package com.seaeagle.pbmicroservice.handlers.crud;

import com.seaeagle.pbmicroservice.dto.events.crud.ChangePBEvent;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.handlers.Handler;
import com.seaeagle.pbmicroservice.handlers.HandlingResult;
import org.springframework.stereotype.Service;

@Service
public class ChangeHandler implements Handler {
    @Override
    public HandlingResult handle(Event event) {
        if (!(event instanceof ChangePBEvent)) return new HandlingResult(500, "ChangeHandler cannot parse " + event.getEventName());
        return new HandlingResult(200, "ID");
    }


}
