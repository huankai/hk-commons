package com.hk.commons.fastjson.support.joda;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.serializer.SerializeWriter;
import com.hk.commons.util.date.DatePattern;

/**
 * DateTime
 * 
 * @author: kevin
 *
 */
public class JodaLocalTimeDeserializer extends AbstractJodaDeserializer<LocalTime> {

	public static final JodaLocalTimeDeserializer INSTANCE = new JodaLocalTimeDeserializer();

	public JodaLocalTimeDeserializer() {
		super(DatePattern.HH_MM_SS);	
	}

	public JodaLocalTimeDeserializer(DatePattern datePattern) {
		super(datePattern);
	}

	@Override
	protected Class<LocalTime> getType() {
		return LocalTime.class;
	}

	@Override
	protected LocalTime doDeserialze(String text, DateTimeFormatter formatter) {
		return LocalTime.parse(text, formatter);
	}

	@Override
	protected void doWrite(SerializeWriter writer, LocalTime dateTime, DateTimeFormatter formatter) {
		writer.writeString(dateTime.toString(formatter));
	}

}
