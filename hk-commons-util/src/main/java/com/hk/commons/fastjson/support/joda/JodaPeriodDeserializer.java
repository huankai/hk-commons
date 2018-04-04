package com.hk.commons.fastjson.support.joda;

import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;

import com.alibaba.fastjson.serializer.SerializeWriter;
import com.hk.commons.util.date.DatePattern;

/**
 * Period
 * 
 * @author huangkai
 *
 */
public class JodaPeriodDeserializer extends AbstractJodaDeserializer<Period> {

	public static final JodaPeriodDeserializer INSTANCE = new JodaPeriodDeserializer();

	public JodaPeriodDeserializer() {
		super(DatePattern.YYYY_MM_DD_HH_MM_SS);
	}

	public JodaPeriodDeserializer(DatePattern datePattern) {
		super(datePattern);
	}

	@Override
	protected Class<Period> getType() {
		return Period.class;
	}

	@Override
	protected Period doDeserialze(String text, DateTimeFormatter formatter) {
		return Period.parse(text);
	}

	@Override
	protected void doWrite(SerializeWriter writer, Period period, DateTimeFormatter formatter) {
		writer.writeString(period.toString());
	}

}
