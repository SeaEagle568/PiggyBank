package com.seaeagle.pbmicroservice.dto.events.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.EventBuilder;
import com.seaeagle.pbmicroservice.dto.events.utils.AuthDataDTO;

public class AddPBEvent extends Event {

    private AddPBEvent(String name, AddEventData data) {
        super(name, data);
    }

    public static final class Builder implements EventBuilder {
        public AddPBEvent buildFromJson(String json) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            AddEventData data = objectMapper.readValue(json, AddPBEvent.AddEventData.class);
            return new AddPBEvent("AddPBEvent", data);
        }
    }

    public static class AddEventData extends Event.EventData {
        public String name;
        public String description;
        public AuthDataDTO author;
        public String destination;
    }
}
