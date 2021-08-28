package com.seaeagle.pbmicroservice.dto.events.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaeagle.pbmicroservice.dto.events.Event;
import services.RPCEvent;

import java.io.Serializable;

public class GenerateWalletEvent extends Event {

    public GenerateWalletEvent() {
        super("GenerateWalletEvent", new GenerateEventData());
    }

    private static class GenerateEventData extends EventData {
        @Override
        public String toString(){
            return "";
        }
    }
}
