package cz.reservation.app.model.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import cz.reservation.app.model.RangeOfLocalTimeFactory;
import org.apache.commons.lang3.Range;

import java.io.IOException;
import java.time.LocalTime;

public class RangeOfLocalTimeDeserializer extends JsonDeserializer<Range<LocalTime>> {

    @Override
    public Range<LocalTime> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String rangeStartTime = node.get(0).asText();
        String rangeEndTime = node.get(1).asText();
        return RangeOfLocalTimeFactory.getNewRangeOfLocalTime(LocalTime.parse(rangeStartTime), LocalTime.parse(rangeEndTime));
    }
}
