package com.hk.commons.fastjson.support.joda;

import java.io.IOException;
import java.lang.reflect.Type;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hk.commons.util.StringUtils;
import com.hk.commons.util.date.DatePattern;

/**
 * Joda 日期与 Fast json 整合 DateTime
 * 
 * @author: kevin
 *
 */
@SuppressWarnings("unchecked")
abstract class AbstractJodaDeserializer<T> implements ObjectSerializer, ObjectDeserializer {

	private final DatePattern datePattern;

	protected abstract Class<T> getType();

	protected abstract T doDeserialze(String text, DateTimeFormatter formatter);

	protected abstract void doWrite(SerializeWriter writer, T t, DateTimeFormatter formatter);

	public AbstractJodaDeserializer(DatePattern datePattern) {
		this.datePattern = datePattern;
	}

	@Override
	public T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		if (type != getType()) {
			throw new UnsupportedOperationException();
		}
		JSONLexer lexer = parser.getLexer();
		if (lexer.token() == JSONToken.LITERAL_STRING) {
			String text = lexer.stringVal();
			lexer.nextToken();
			DateTimeFormatter forPattern = DateTimeFormat
					.forPattern(StringUtils.isEmpty(parser.getDateFomartPattern()) ? datePattern.getPattern()
							: parser.getDateFomartPattern());
			return doDeserialze(text, forPattern);
			// if (type == LocalDateTime.class) {
			// return (T) LocalDateTime.parse(text);
			// } else if (type == LocalDate.class) {
			// return (T) LocalDate.parse(text);
			// } else if (type == LocalTime.class) {
			// return (T) LocalTime.parse(text);
			// } else if (type == Period.class) {
			// return (T) Period.parse(text);
			// } else if (type == Duration.class) {
			// return (T) Duration.parse(text);
			// } else if (type == Instant.class) {
			// return (T) Instant.parse(text);
			// } else if(type == DateTime.class) {
			// return (T) DateTime.parse(text);
			// }
		}
		return null;
	}

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_STRING;
	}

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter writer = serializer.out;
		if (object == null) {
			writer.writeNull(SerializerFeature.WriteNullStringAsEmpty);
			return;
		}
		DateTimeFormatter forPattern = DateTimeFormat
				.forPattern(StringUtils.isEmpty(serializer.getDateFormatPattern()) ? datePattern.getPattern()
						: serializer.getDateFormatPattern());
		doWrite(writer, (T) object, forPattern);
	}

}
