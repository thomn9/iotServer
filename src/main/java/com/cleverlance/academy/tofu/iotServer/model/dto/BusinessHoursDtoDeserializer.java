package com.cleverlance.academy.tofu.iotServer.model.dto;

import com.cleverlance.academy.tofu.iotServer.model.RangeOfLocalTimeFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.Range;

import java.io.IOException;
import java.time.LocalTime;

public class BusinessHoursDtoDeserializer extends JsonDeserializer<Range<LocalTime>> {

    @Override
    public Range<LocalTime> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String rangeStartTime = node.get(0).asText();
        String rangeEndTime = node.get(1).asText();
        return RangeOfLocalTimeFactory.getNewRangeOfLocalTime(LocalTime.parse(rangeStartTime), LocalTime.parse(rangeEndTime));
    }
}
