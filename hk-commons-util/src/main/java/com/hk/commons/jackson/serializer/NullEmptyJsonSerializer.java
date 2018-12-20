package com.hk.commons.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hk.commons.util.StringUtils;

import java.io.IOException;

/**
 * @author kevin
 * @date 2018-08-20 13:09
 */
public class NullEmptyJsonSerializer extends JsonSerializer<Object> {

    public static final NullEmptyJsonSerializer INSTANCE = new NullEmptyJsonSerializer();


    private NullEmptyJsonSerializer() {

    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(StringUtils.EMPTY);//如果是字符串，可以设置为 "" ，但如果是对象就不行，设置为 "" 再反序列化就会有问题
    }
}
