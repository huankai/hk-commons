package com.hk.commons.fastjson.support.joda;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.serializer.SerializeWriter;
import com.hk.commons.util.date.DatePattern;

/**
 * DateTime
 * 
 * @author huangkai
 * 
 */
public class JodaLocalDateDeserializer extends AbstractJodaDeserializer<LocalDate> {

	public static final JodaLocalDateDeserializer INSTANCE = new JodaLocalDateDeserializer();

	public JodaLocalDateDeserializer() {
		super(DatePattern.YYYY_MM_DD_HH_MM_SS);
	}

	public JodaLocalDateDeserializer(DatePattern datePattern) {
		super(datePattern);
	}

	@Override
	protected Class<LocalDate> getType() {
		return LocalDate.class;
	}

	@Override
	protected LocalDate doDeserialze(String text, DateTimeFormatter formatter) {
		return LocalDate.parse(text, formatter);
	}

	@Override
	protected void doWrite(SerializeWriter writer, LocalDate dateTime, DateTimeFormatter formatter) {
		writer.writeString(dateTime.toString(formatter));
	}

}
