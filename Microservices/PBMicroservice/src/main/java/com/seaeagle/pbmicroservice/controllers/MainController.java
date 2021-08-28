package com.seaeagle.pbmicroservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seaeagle.pbmicroservice.dto.events.*;
import com.seaeagle.pbmicroservice.dto.events.crud.AddPBEvent;
import com.seaeagle.pbmicroservice.dto.events.crud.ChangePBEvent;
import com.seaeagle.pbmicroservice.dto.events.crud.DeletePBEvent;
import com.seaeagle.pbmicroservice.dto.events.crud.GetPBEvent;
import com.seaeagle.pbmicroservice.handlers.*;
import com.seaeagle.pbmicroservice.handlers.crud.AddHandler;
import com.seaeagle.pbmicroservice.handlers.crud.ChangeHandler;
import com.seaeagle.pbmicroservice.handlers.crud.DeleteHandler;
import com.seaeagle.pbmicroservice.handlers.crud.GetHandler;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import services.HandlerGrpc;
import services.RPCEvent;
import services.RPCEventResult;

@Controller
public class MainController extends HandlerGrpc.HandlerImplBase{
    private final AddHandler addHandler;
    private final GetHandler getHandler;
    private final DeleteHandler deleteHandler;
    private final ChangeHandler changeHandler;

    @Autowired
    MainController(AddHandler addHandler, GetHandler getHandler, DeleteHandler deleteHandler, ChangeHandler changeHandler) {
        this.addHandler = addHandler;
        this.changeHandler = changeHandler;
        this.getHandler = getHandler;
        this.deleteHandler = deleteHandler;
    }

    @Override
    public void handle(RPCEvent request, StreamObserver<RPCEventResult> responseObserver){
        RPCEventResult result;
        switch (request.getEventType())
        {
            case "AddPBEvent" -> result = handleEvent(request.getData(), new AddPBEvent.Builder(), addHandler);
            case "GetPBEvent" -> result = handleEvent(request.getData(), new GetPBEvent.Builder(), getHandler);
            case "DeletePBEvent" -> result = handleEvent(request.getData(), new DeletePBEvent.Builder(), deleteHandler);
            case "ChangePBEvent" -> result = handleEvent(request.getData(), new ChangePBEvent.Builder(), changeHandler);
            default -> result = invalidRequest();
        }
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    private RPCEventResult invalidRequest() {
        return RPCEventResult.newBuilder().setCode(400).setMessage("I don't know how to handle this request").build();
    }

    private RPCEventResult handleEvent(String data, EventBuilder eventBuilder, Handler handler) {
        try {
            Event event = eventBuilder.buildFromJson(data);
            HandlingResult result = handler.handle(event);
            return RPCEventResult.newBuilder()
                    .setCode(result.getCode())
                    .setMessage(result.getMessage())
                    .build();
        }
        catch (JsonProcessingException e) {
            System.err.println("Error occurred while parsing json: ");
            e.printStackTrace();
            return RPCEventResult.newBuilder().setCode(400).setMessage("Wrong JSON format").build();
        }


    }
}
