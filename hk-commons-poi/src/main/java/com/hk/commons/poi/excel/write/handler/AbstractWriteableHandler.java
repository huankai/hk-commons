/**
 * 
 */
package com.hk.commons.poi.excel.write.handler;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hk.commons.poi.excel.exceptions.WriteableExcelException;
import com.hk.commons.poi.excel.model.DataFormat;
import com.hk.commons.poi.excel.model.ExcelColumnInfo;
import com.hk.commons.poi.excel.model.StyleTitle;
import com.hk.commons.poi.excel.model.WriteParam;
import com.hk.commons.poi.excel.style.CustomCellStyle;
import com.hk.commons.poi.excel.util.CellStyleBuilder;
import com.hk.commons.poi.excel.util.WriteExcelUtils;
import com.hk.commons.util.BooleanUtils;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.ObjectUtils;
import com.hk.commons.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ClassUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author huangkai
 *
 */
public abstract class AbstractWriteableHandler<T> implements WriteableHandler<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 导出参数
	 */
	protected WriteParam<T> params;

	/**
	 * 工作表
	 */
	protected Workbook workbook;

	/**
	 * 
	 */
	private List<ExcelColumnInfo> columnInfoList;

	/**
	 * <pre>
	 * 缓存属性列样式
	 * 每一个属性列都有相同的样式
	 * </pre>
	 */
	private Map<String, CellStyle> cacheDataCellStyle = Maps.newHashMap();

	/**
	 * 统计样式
	 */
	private CustomCellStyle statiStyle;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.write.handler.WriteableHandler#write(org.apache.poi.
	 * ss.usermodel.Workbook, com.hk.commons.poi.excel.model.WriteParam,
	 * java.io.OutputStream)
	 */
	@Override
	public final void write(Workbook workbook, WriteParam<T> param, OutputStream out) {
		this.params = param;
		this.workbook = workbook;
		try {
			writeWrokbook();
			this.workbook.write(out);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new WriteableExcelException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 写入数据到工作表
	 */
	protected abstract void writeWrokbook();

	@Override
	public Workbook getWorkBook() {
		return workbook;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 * @return
	 */
	protected final int mergingCells(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	/**
	 * <pre>
	 * 	获取批注内容
	 * 	如果需要设置批注，请重写此方法
	 * </pre>
	 * 
	 * @param obj
	 *            对象
	 * @param propertyName
	 *            对象属性
	 * @param propertyType
	 *            对象属性类型
	 * @return 需要设置的批注信息 ，如果返回值为 Null || "" || " " ，不设置批注信息
	 */
	protected String getCommentText(T obj, String propertyName, Class<?> propertyType) {
		return null;
	}

	/**
	 * 创建标题行
	 * 
	 * @param sheet
	 */
	protected void createTitleRow(Sheet sheet) {
		Row titleRow = sheet.createRow(params.getTitleRow());
		titleRow.setHeightInPoints(params.getTitleRowHeight());
		List<ExcelColumnInfo> columnInfoList = getColumnInfoList();
		for (ExcelColumnInfo item : columnInfoList) {
			StyleTitle title = item.getTitle();
			sheet.setColumnWidth(title.getColumn(), title.getColumnWidth());
			Cell cell = titleRow.createCell(title.getColumn(), CellType.STRING);
			CellStyle cellStyle = CellStyleBuilder.buildCellStyle(workbook, title.getStyle(), DataFormat.TEXT_FORMAT);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(title.getValue());
		}
	}

	/**
	 * 根据属性名称获取对应的单元格式
	 * 
	 * @param propertyType
	 * @return
	 */
	protected final CellType getCellType(Class<?> propertyType) {
		CellType cellType = CellType.STRING;
		if (ClassUtils.isAssignable(Number.class, propertyType) || ClassUtils.isAssignable(Date.class, propertyType)
				|| ClassUtils.isAssignable(Temporal.class, propertyType)) {
			cellType = CellType.NUMERIC;
		} else if (propertyType == Boolean.class || propertyType == Boolean.TYPE) {
			cellType = CellType.BOOLEAN;
		}
		return cellType;
	}

	/**
	 * 创建数据行
	 * 
	 * @param sheet
	 * @param dataList
	 */
	protected void createDataRows(Sheet sheet, List<T> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			CreationHelper helper = workbook.getCreationHelper();
			Drawing<?> drawing = sheet.createDrawingPatriarch();
			int rowIndex = params.getDataStartRow();

			Map<Integer, String> statisFormula = Maps.newHashMap();// 记录需要统计的列与公式
			List<ExcelColumnInfo> columnInfoList = getColumnInfoList();

			// 记录nested属性所在每个列、与属性名称的Map
			Map<Integer, String> columnNestedPropertyMap = Maps.newHashMap();

			String nestedPropertyPrefix = null;// Nested属性前缀 ，如 users[].name ，则此值为 users
			for (ExcelColumnInfo excelColumnInfo : columnInfoList) {
				StyleTitle title = excelColumnInfo.getTitle();
				if (StringUtils.contains(title.getPropertyName(), WriteExcelUtils.NESTED_PROPERTY)) {
					nestedPropertyPrefix = StringUtils.substringBefore(title.getPropertyName(),
							WriteExcelUtils.NESTED_PROPERTY);
					columnNestedPropertyMap.put(title.getColumn(),
							StringUtils.substringAfter(title.getPropertyName(), WriteExcelUtils.NESTED_PROPERTY));
				}
			}
			for (T item : dataList) {
				BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(item);
				if(StringUtils.isEmpty(nestedPropertyPrefix)) {
					Row row = createDataRow(sheet, item, rowIndex++);
					for (ExcelColumnInfo excelColumnInfo : columnInfoList) {
						Cell cell = createCell(row,item,excelColumnInfo,excelColumnInfo.getTitle().getPropertyName(),beanWrapper,helper,drawing);
						statisFormula(excelColumnInfo, cell, statisFormula,StringUtils.COLON_SEPARATE);
					}
				} else {
					Collection<?> collection = (Collection<?>)beanWrapper.getPropertyValue(nestedPropertyPrefix);
					if(CollectionUtils.isEmpty(collection)) {
						Row row = createDataRow(sheet, item, rowIndex++);
						for (ExcelColumnInfo excelColumnInfo : columnInfoList) {
							StyleTitle title = excelColumnInfo.getTitle();
							if(!StringUtils.contains(title.getPropertyName(), WriteExcelUtils.NESTED_PROPERTY)) {
								Cell cell = createCell(row, item, excelColumnInfo,title.getPropertyName(), beanWrapper, helper, drawing);
								statisFormula(excelColumnInfo, cell, statisFormula,StringUtils.COLON_SEPARATE);
							}
						}
					} else {
						final int firstRow = rowIndex;
						Set<Integer> margeCellColumnList = Sets.newHashSet();
						for (int index = 0;index < collection.size();index++) {
							Row row = createDataRow(sheet, item, rowIndex++);
							for (Entry<Integer, String> entry : columnNestedPropertyMap.entrySet()) {
								ExcelColumnInfo info = columnInfoList.stream()
										.filter(infoItem -> infoItem.getTitle().getColumn() == entry.getKey())
										.findFirst().get();
								final String nestedPropertyName = String.format("%s"+ WriteExcelUtils.NESTED_PROPERTY + "%s", nestedPropertyPrefix,index,entry.getValue());
								Cell cell = createCell(row, item, info, nestedPropertyName, beanWrapper,helper, drawing);
								statisFormula(info, cell, statisFormula,StringUtils.COLON_SEPARATE);
							}
							for (ExcelColumnInfo excelColumnInfo : columnInfoList) {
								StyleTitle title = excelColumnInfo.getTitle();
								if(!StringUtils.contains(title.getPropertyName(), WriteExcelUtils.NESTED_PROPERTY)) {
									margeCellColumnList.add(title.getColumn());
									Cell cell = createCell(row, item, excelColumnInfo,title.getPropertyName(), beanWrapper,helper, drawing);
									if(index == 0) {//只需要统计第一条
										statisFormula(excelColumnInfo,cell,statisFormula,StringUtils.COMMA_SEPARATE);
									}
								}
							}
						}
						if(params.isMergeCell() && !margeCellColumnList.isEmpty()) {
							for (Integer columnItem : margeCellColumnList) {
								mergingCells(sheet, firstRow, rowIndex-1, columnItem, columnItem);
							}
						}
					}
				}
			}
			if(!statisFormula.isEmpty()) {
				buildStatisRow(statisFormula, sheet.createRow(rowIndex), statiStyle);
			}
		}
	}

	private void statisFormula(ExcelColumnInfo excelColumnInfo, Cell cell, Map<Integer, String> statisFormula,String separate) {
		if(excelColumnInfo.isStatistics() && cell.getCellTypeEnum() == CellType.NUMERIC && !DateUtil.isCellDateFormatted(cell)) {
			int columnIndex = cell.getColumnIndex();
			String formula = statisFormula.get(columnIndex);
			String cellAddress = cell.getAddress().formatAsString();
			statisFormula.put(columnIndex, StringUtils.isEmpty(formula) ? cellAddress + separate : formula + separate + cellAddress);
		}
		
	}

	/**
	 * 
	 * @param row
	 * @param data
	 * @param excelColumnInfo
	 * @param beanWrapper
	 * @param helper
	 * @param drawing
	 */
	private Cell createCell(Row row,T data, ExcelColumnInfo excelColumnInfo,String nestedPropertyName, BeanWrapper beanWrapper,
			CreationHelper helper, Drawing<?> drawing) {
		StyleTitle title = excelColumnInfo.getTitle();
		Class<?> propertyType = beanWrapper.getPropertyType(nestedPropertyName);
		Object value = beanWrapper.getPropertyValue(nestedPropertyName);
		Cell cell = row.createCell(title.getColumn(), getCellType(propertyType));
		setCellComment(drawing, helper, cell, getCommentText(data, nestedPropertyName, propertyType),
				excelColumnInfo.getCommentAuthor(), excelColumnInfo.isCommentVisible());
		if(StringUtils.contains(nestedPropertyName, WriteExcelUtils.NESTED_PROPERTY)) {
			nestedPropertyName = StringUtils.substringAfterLast(nestedPropertyName, WriteExcelUtils.NESTED_PROPERTY);
		}
		setCellStyle(cell, excelColumnInfo.getDataStyle(), nestedPropertyName, propertyType);
		setCellValue(cell, nestedPropertyName, value);
		return cell;
	}

	/**
	 * 创建数据行并设置行样式
	 * 
	 * @param sheet
	 *            工作表
	 * @param item
	 *            行数据
	 * @param rowIndex
	 *            行号
	 * @return 有样式的行，如果样式存在
	 */
	private Row createDataRow(Sheet sheet, T item, int rowIndex) {
		Row row = sheet.createRow(rowIndex);
		row.setHeightInPoints(params.getDataRowHeight());
		CustomCellStyle rowStyle = getRowStyle(item, row.getRowNum());
		if (null != rowStyle) {
			row.setRowStyle(rowStyle.toCellStyle(workbook, DataFormat.TEXT_FORMAT));
		}
		return row;
	}

	/**
	 * 
	 * @param statisFormula
	 * @param row
	 * @param statiStyle
	 */
	protected void buildStatisRow(Map<Integer, String> statisFormula, Row row, CustomCellStyle statiStyle) {
		CellStyle style = CellStyleBuilder.buildCellStyle(workbook, statiStyle, DataFormat.TEXT_FORMAT);
		for (ExcelColumnInfo info : getColumnInfoList()) {
			StyleTitle title = info.getTitle();
			if (statisFormula.containsKey(title.getColumn())) {
				Cell cell = row.createCell(title.getColumn(), CellType.NUMERIC);
				cell.setCellFormula(String.format("SUM(%s)", statisFormula.get(title.getColumn())));
				cell.setCellStyle(style);
			}
		}
	}

	/**
	 * 获取数据行样式
	 * 
	 * @param data
	 *            要填充的对象
	 * @param rowNum
	 *            当前行数
	 */
	protected CustomCellStyle getRowStyle(T data, int rowNum) {
		return null;
	}

	/**
	 * 设置单元格样式
	 * 
	 * @param cell
	 * @param style
	 * @param propertyName
	 * @param propertyType
	 */
	protected void setCellStyle(Cell cell, CustomCellStyle style, String propertyName, Class<?> propertyType) {
		CellStyle cellStyle = cacheDataCellStyle.get(propertyName);
		if (null == cellStyle && null != style) {
			cellStyle = style.toCellStyle(workbook, params.getValueFormat().getFormat(propertyName, propertyType));
			cacheDataCellStyle.put(propertyName, cellStyle);
		}
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 构建数据单元格，设置值与批注信息
	 * 
	 * @param cell
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            要设置的单元格值
	 * @param commentText
	 *            批注内容
	 * @param drawing
	 * @param helper
	 * @param author
	 *            批注作者
	 * @param visible
	 *            批注可见性
	 */
	protected void buildDataCell(Cell cell, String propertyName, Object value, String commentText, Drawing<?> drawing,
			CreationHelper helper, String author, boolean visible) {
		setCellValue(cell, propertyName, value);
		setCellComment(drawing, helper, cell, commentText, author, visible);
	}

	/**
	 * 设置单元格注释
	 * 
	 * @param drawing
	 * @param helper
	 * @param cell
	 * @param commentText
	 */
	private void setCellComment(Drawing<?> drawing, CreationHelper helper, Cell cell, String commentText, String author,
			boolean visible) {
		if (StringUtils.isNotBlank(commentText)) {
			ClientAnchor clientAnchor = helper.createClientAnchor();
			clientAnchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);
			clientAnchor.setCol1(cell.getColumnIndex());
			clientAnchor.setCol2(cell.getColumnIndex() + 1);
			clientAnchor.setRow1(cell.getRowIndex());
			clientAnchor.setRow2(cell.getRowIndex() + 3);
			Comment comment = drawing.createCellComment(clientAnchor);
			comment.setAuthor(author);// 作者
			comment.setVisible(visible); // 是否可见 ，默认隐藏，需要鼠标移动到指定的区域才可见
			comment.setString(helper.createRichTextString(commentText));
			cell.setCellComment(comment);
		}
	}

	/**
	 * 设置单元格值
	 * 
	 * @param cell
	 * @param value
	 */
	protected final void setCellValue(Cell cell, String propertyName, Object value) {
		if (ObjectUtils.isNotEmpty(value)) {
			if (ClassUtils.isAssignableValue(Number.class, value)) {
				BigDecimal decimal = (value instanceof BigDecimal) ? (BigDecimal) value
						: new BigDecimal(value.toString(), MathContext.UNLIMITED);
				cell.setCellValue(decimal.doubleValue());
			} else if (ClassUtils.isAssignableValue(Boolean.class, value)) {
				cell.setCellValue(BooleanUtils.toBooleanChinese(BooleanUtils.toBoolean(value.toString())));
			} else if (ClassUtils.isAssignableValue(Date.class, value)) {
				cell.setCellValue((Date) value);
			} else if (ClassUtils.isAssignableValue(LocalDateTime.class, value)) {
				DataFormat format = params.getValueFormat().getFormat(propertyName, LocalDateTime.class);
				cell.setCellValue(((LocalDateTime) value).format(DateTimeFormatter.ofPattern(format.getPattern())));
			} else if (ClassUtils.isAssignableValue(LocalDate.class, value)) {
				DataFormat format = params.getValueFormat().getFormat(propertyName, LocalDate.class);
				cell.setCellValue(((LocalDate) value).format(DateTimeFormatter.ofPattern(format.getPattern())));
			} else if (ClassUtils.isAssignableValue(LocalTime.class, value)) {
				DataFormat format = params.getValueFormat().getFormat(propertyName, LocalTime.class);
				cell.setCellValue(((LocalTime) value).format(DateTimeFormatter.ofPattern(format.getPattern())));
			} else {
				cell.setCellValue(value.toString());
			}
		}
	}

	public List<ExcelColumnInfo> getColumnInfoList() {
		if (null == columnInfoList) {
			columnInfoList = WriteExcelUtils.parse(params.getBeanClazz(), params.getTitleRow());
		}
		return columnInfoList;
	}

	/**
	 * @return the statiStyle
	 */
	public CustomCellStyle getStatiStyle() {
		return statiStyle;
	}

	/**
	 * @param statiStyle
	 *            the statiStyle to set
	 */
	public void setStatiStyle(CustomCellStyle statiStyle) {
		this.statiStyle = statiStyle;
	}

}
