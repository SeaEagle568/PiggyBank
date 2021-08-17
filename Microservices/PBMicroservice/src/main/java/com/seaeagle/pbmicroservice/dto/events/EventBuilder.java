package com.seaeagle.pbmicroservice.dto.events;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EventBuilder {
    Event buildFromJson(String data) throws JsonProcessingException;
}
