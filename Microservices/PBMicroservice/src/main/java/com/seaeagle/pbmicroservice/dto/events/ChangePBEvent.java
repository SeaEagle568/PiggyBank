package com.seaeagle.pbmicroservice.dto.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChangePBEvent implements Event{
    @Override
    public String getEventName() {
        return null;
    }
    public static final class Builder implements EventBuilder{
        public ChangePBEvent buildFromJson(String json) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ChangePBEvent.class);
        }
    }
}
