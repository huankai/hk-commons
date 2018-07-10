package com.hk.commons.fastjson.support.joda;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.serializer.SerializeWriter;
import com.hk.commons.util.date.DatePattern;

/**
 * DateTime
 * 
 * @author: kevin
 *
 */
public class JodaDateTimeDeserializer extends AbstractJodaDeserializer<DateTime> {

	public static final JodaDateTimeDeserializer INSTANCE = new JodaDateTimeDeserializer();

	public JodaDateTimeDeserializer() {
		super(DatePattern.YYYY_MM_DD_HH_MM_SS);
	}

	public JodaDateTimeDeserializer(DatePattern datePattern) {
		super(datePattern);
	}

	@Override
	protected Class<DateTime> getType() {
		return DateTime.class;
	}

	@Override
	protected DateTime doDeserialze(String text, DateTimeFormatter formatter) {
		return DateTime.parse(text, formatter);
	}

	@Override
	protected void doWrite(SerializeWriter writer, DateTime dateTime, DateTimeFormatter formatter) {
		writer.writeString(dateTime.toString(formatter));
	}

}
