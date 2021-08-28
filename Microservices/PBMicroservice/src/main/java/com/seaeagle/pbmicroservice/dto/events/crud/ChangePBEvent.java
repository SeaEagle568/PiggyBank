package com.seaeagle.pbmicroservice.dto.events.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.EventBuilder;
import com.seaeagle.pbmicroservice.dto.events.utils.AuthDataDTO;

public class ChangePBEvent extends Event {

    private ChangePBEvent(String name, ChangeEventData data) {
        super(name, data);
    }

    public static final class Builder implements EventBuilder {
        public ChangePBEvent buildFromJson(String json) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            ChangeEventData data = objectMapper.readValue(json, ChangeEventData.class);
            return new ChangePBEvent("ChangePBEvent", data);
        }
    }

    public static class ChangeEventData extends Event.EventData {
        public String id;
        public String name;
        public String description;
        public AuthDataDTO author;
        public String destination;

    }
}
