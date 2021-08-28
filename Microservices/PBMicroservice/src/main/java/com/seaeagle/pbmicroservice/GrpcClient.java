package com.seaeagle.pbmicroservice;

import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.replies.Reply;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import services.HandlerGrpc;
import services.RPCEventResult;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;

@Controller
public class GrpcClient {

    @Value("${microservice.eventdriver}")
    private String EventDriverAddress;
    private Channel channel;

    @PostConstruct
    private void main() {
        this.channel = ManagedChannelBuilder.forTarget(EventDriverAddress).usePlaintext().build();
    }

    public Reply handle(Event event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HandlerGrpc.HandlerBlockingStub stub = HandlerGrpc.newBlockingStub(this.channel);
        RPCEventResult result = stub.handle(event.toRPC());
        var replyBuilderClass = Reply.getCorrespondingReply(event.getClass());
        return (Reply) replyBuilderClass.getDeclaredMethod("buildFromRPC").invoke(result);
    }
}
