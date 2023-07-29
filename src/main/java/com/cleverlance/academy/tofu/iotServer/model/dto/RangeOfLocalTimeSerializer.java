package com.cleverlance.academy.tofu.iotServer.model.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.Range;

import java.io.IOException;
import java.time.LocalTime;

public class RangeOfLocalTimeSerializer extends JsonSerializer<Range<LocalTime>> {

    @Override
    public void serialize(Range<LocalTime> localTimeRange, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String[] localTimeRangeAsStringArray ={localTimeRange.getMinimum().toString(), localTimeRange.getMaximum().toString()};
        jsonGenerator.writeArray(localTimeRangeAsStringArray,0,2);
    }
}
