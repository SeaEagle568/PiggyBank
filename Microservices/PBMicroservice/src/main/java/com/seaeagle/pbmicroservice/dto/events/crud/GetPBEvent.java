package com.seaeagle.pbmicroservice.dto.events.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.EventBuilder;

public class GetPBEvent extends Event {

    private GetPBEvent(String name, GetEventData data) {
        super(name, data);
    }

    public static final class Builder implements EventBuilder {
        public GetPBEvent buildFromJson(String json) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            GetEventData data = objectMapper.readValue(json, GetEventData.class);
            return new GetPBEvent("GetPBEvent", data);
        }
    }

    public static class GetEventData extends Event.EventData {
        public String id;
    }
}
