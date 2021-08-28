package com.seaeagle.pbmicroservice.dto.replies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import services.RPCEventResult;

public class GenerateWalletReply extends Reply{


    protected GenerateWalletReply(Message message) {
        super(message);
    }

    public static class Builder implements ReplyBuilder{
        @Override
        public Reply buildFromRPC(RPCEventResult data) throws JsonProcessingException {
            ObjectMapper om = new ObjectMapper();
            Message msg = om.readValue(data.getMessage(), GenerateWalletMessage.class);
            return new GenerateWalletReply(msg);
        }
    }

    public static class GenerateWalletMessage extends Message {
        @Getter
        String wallet;
    }
}
