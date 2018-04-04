package com.hk.commons.fastjson.support.joda;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.serializer.SerializeWriter;
import com.hk.commons.util.date.DatePattern;

/**
 * LocalDateTime
 * 
 * @author huangkai
 *
 */
public class JodaLocalDateTimeDeserializer extends AbstractJodaDeserializer<LocalDateTime> {

	public static final JodaLocalDateTimeDeserializer INSTANCE = new JodaLocalDateTimeDeserializer();

	public JodaLocalDateTimeDeserializer() {
		super(DatePattern.YYYY_MM_DD_HH_MM_SS);
	}

	public JodaLocalDateTimeDeserializer(DatePattern datePattern) {
		super(datePattern);
	}

	@Override	
	protected Class<LocalDateTime> getType() {
		return LocalDateTime.class;
	}

	@Override
	protected LocalDateTime doDeserialze(String text, DateTimeFormatter formatter) {
		return LocalDateTime.parse(text, formatter);
	}

	@Override
	protected void doWrite(SerializeWriter writer, LocalDateTime dateTime, DateTimeFormatter formatter) {
		writer.writeString(dateTime.toString(formatter));
	}

}
