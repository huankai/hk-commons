package com.hk.commons.poi.excel.model;

import java.util.List;
import java.util.Map;

import com.hk.commons.poi.excel.read.interceptor.ValidationInterceptor;
import com.hk.commons.poi.excel.read.validation.Validationable;

import lombok.Builder;
import lombok.Data;

/**
 * 读取 Excel参数
 *
 * @author kevin
 * @date 2018年1月10日下午5:17:49
 */
@Data
@Builder
public class ReadParam<T> {

	/**
	 * 標題行
	 */
	private int titleRow;

	/**
	 * 数据开始行
	 */
	@Builder.Default
	private int dataStartRow = 1;

	/**
	 * <pre>
	 * 是否解析所有工作表
	 * 如果设置为 true,自定义的 sheetStartIndex 与 sheetMaxIndex 将被覆盖
	 * </pre>
	 */
	private boolean parseAll;

	/**
	 * 开始读取的sheet位置
	 */
	private int sheetStartIndex;

	/**
	 * 上传表格需要读取的sheet最大的索引
	 */
	private int sheetMaxIndex;

	/**
	 * 是否输出公式结果值
	 */
	private boolean outputFormulaValues;

	/**
	 * 是否过滤单元格首尾空字符串
	 */
	@Builder.Default
	private boolean trim = true;

	/**
	 * <pre>
	 * 是否忽略换行符号
	 * </pre>
	 */
	private boolean ignoreLineBreak;

	/**
	 * 列与属性映射
	 */
	private Map<Integer, String> columnProperties;

	/**
	 * BeanClazz
	 */
	private Class<T> beanClazz;

	/**
	 * 验证拦截器
	 */
	@Builder.Default
	private ValidationInterceptor<T> interceptor = ValidationInterceptor.INSTANCE;

	/**
	 * 验证器
	 */
	private List<Validationable<T>> validationList;

}
