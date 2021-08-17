package com.seaeagle.pbmicroservice.handlers;

import com.seaeagle.pbmicroservice.dto.events.AddPBEvent;
import com.seaeagle.pbmicroservice.dto.events.ChangePBEvent;
import com.seaeagle.pbmicroservice.dto.events.Event;
import org.springframework.stereotype.Service;

@Service
public class ChangeHandler implements Handler{
    @Override
    public HandlingResult handle(Event event) {
        if (!(event instanceof ChangePBEvent)) return new HandlingResult(500, "ChangeHandler cannot parse " + event.getEventName());
        return new HandlingResult(200, "ID");
    }


}
