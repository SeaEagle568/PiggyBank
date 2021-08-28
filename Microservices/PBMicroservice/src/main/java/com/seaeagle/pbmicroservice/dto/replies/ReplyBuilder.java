package com.seaeagle.pbmicroservice.dto.replies;

import com.fasterxml.jackson.core.JsonProcessingException;
import services.RPCEventResult;

public interface ReplyBuilder {
    Reply buildFromRPC(RPCEventResult data) throws JsonProcessingException;
}
