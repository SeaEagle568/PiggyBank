package com.seaeagle.pbmicroservice.dto.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import services.RPCEvent;

import java.util.Optional;

public abstract class Event {
    @JsonProperty(value = "eventType")
    @Getter
    private final String eventName;

    @JsonProperty(value = "data")
    @Getter
    private final EventData data;

    protected Event(String name, EventData data) {
        this.data = data;
        this.eventName = name;
    }

    public RPCEvent toRPC() {
        return RPCEvent.newBuilder()
                .setEventType(this.getEventName())
                .setData(this.getData().toString())
                .build();
    }

    protected static class EventData {
        @SneakyThrows
        public String toString() {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }
    }
}
