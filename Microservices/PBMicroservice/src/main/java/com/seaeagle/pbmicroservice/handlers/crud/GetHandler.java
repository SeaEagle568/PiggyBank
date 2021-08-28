package com.seaeagle.pbmicroservice.handlers.crud;

import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.crud.GetPBEvent;
import com.seaeagle.pbmicroservice.handlers.Handler;
import com.seaeagle.pbmicroservice.handlers.HandlingResult;
import org.springframework.stereotype.Service;

@Service
public class GetHandler implements Handler {
    @Override
    public HandlingResult handle(Event event) {
        if (!(event instanceof GetPBEvent)) return new HandlingResult(500, "GetHandler cannot parse " + event.getEventName());
        return new HandlingResult(200, "ID");
    }
}
