package com.seaeagle.pbmicroservice.handlers;

import com.seaeagle.pbmicroservice.dto.events.DeletePBEvent;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.GetPBEvent;
import org.springframework.stereotype.Service;

@Service
public class GetHandler implements Handler{
    @Override
    public HandlingResult handle(Event event) {
        if (!(event instanceof GetPBEvent)) return new HandlingResult(500, "GetHandler cannot parse " + event.getEventName());
        return new HandlingResult(200, "ID");
    }
}
