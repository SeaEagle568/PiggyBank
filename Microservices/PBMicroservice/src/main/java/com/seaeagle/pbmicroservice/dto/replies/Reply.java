package com.seaeagle.pbmicroservice.dto.replies;

import com.google.protobuf.Message;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.out.GenerateWalletEvent;
import lombok.Getter;

import java.util.HashMap;

public abstract class Reply {

    @Getter
    protected Message message;

    protected Reply(Message message) {
        this.message = message;
    }

    public abstract static class Message {

    }

    private static HashMap<Class<? extends Event>, Class<? extends ReplyBuilder>> map = new HashMap<>();
    static {
        map.put(GenerateWalletEvent.class, GenerateWalletReply.Builder.class);
    }
    public static Class<? extends ReplyBuilder> getCorrespondingReply(Class<? extends Event> event) {
        return map.get(event);
    }
}
