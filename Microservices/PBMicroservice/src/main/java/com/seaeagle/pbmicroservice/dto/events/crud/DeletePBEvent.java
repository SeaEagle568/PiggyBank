package com.seaeagle.pbmicroservice.dto.events.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.EventBuilder;
import com.seaeagle.pbmicroservice.dto.events.utils.AuthDataDTO;

public class DeletePBEvent extends Event {

    private DeletePBEvent(String name, DeleteEventData data){
        super(name, data);
    }

    public static final class Builder implements EventBuilder {
        public DeletePBEvent buildFromJson(String json) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            DeleteEventData data = objectMapper.readValue(json, DeleteEventData.class);
            return new DeletePBEvent("DeletePBEvent", data);
        }
    }

    public static class DeleteEventData extends Event.EventData {
        public String id;
        public AuthDataDTO author;
    }
}
