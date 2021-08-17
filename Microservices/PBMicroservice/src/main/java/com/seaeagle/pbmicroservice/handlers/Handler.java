package com.seaeagle.pbmicroservice.handlers;

import com.seaeagle.pbmicroservice.dto.events.Event;

public interface Handler {
    HandlingResult handle(Event event);
}
