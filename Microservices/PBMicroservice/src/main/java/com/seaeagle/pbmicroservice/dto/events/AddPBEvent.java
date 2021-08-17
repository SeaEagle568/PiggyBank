package com.seaeagle.pbmicroservice.dto.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaeagle.pbmicroservice.dto.events.utils.AuthDataDTO;
import lombok.Getter;

public class AddPBEvent implements Event{
    @JsonProperty(value = "eventType")
    @Getter
    private final String eventName;

    @JsonProperty(value = "data")
    @Getter
    private final EventData data;

    private AddPBEvent(String name, EventData data) {
        this.data = data;
        this.eventName = name;
    }

    public static final class Builder implements EventBuilder{
        public AddPBEvent buildFromJson(String json) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            EventData data = objectMapper.readValue(json, AddPBEvent.EventData.class);
            return new AddPBEvent("AddPBEvent", data);
        }
    }

    private static class EventData {
        public String name;
        public String description;
        public AuthDataDTO author;
        public String destination;
    }
}
