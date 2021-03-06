package com.seaeagle.pbmicroservice.handlers;

import com.seaeagle.pbmicroservice.dto.events.AddPBEvent;
import com.seaeagle.pbmicroservice.dto.events.DeletePBEvent;
import com.seaeagle.pbmicroservice.dto.events.Event;
import org.springframework.stereotype.Service;

@Service
public class DeleteHandler implements Handler{
    @Override
    public HandlingResult handle(Event event) {
        if (!(event instanceof DeletePBEvent)) return new HandlingResult(500, "DeleteHandler cannot parse " + event.getEventName());
        return new HandlingResult(200, "ID");
    }
}
