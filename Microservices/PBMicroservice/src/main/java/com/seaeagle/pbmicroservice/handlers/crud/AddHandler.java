package com.seaeagle.pbmicroservice.handlers.crud;

import com.seaeagle.pbmicroservice.GrpcClient;
import com.seaeagle.pbmicroservice.dto.events.crud.AddPBEvent;
import com.seaeagle.pbmicroservice.dto.events.Event;
import com.seaeagle.pbmicroservice.dto.events.out.GenerateWalletEvent;
import com.seaeagle.pbmicroservice.dto.replies.GenerateWalletReply;
import com.seaeagle.pbmicroservice.entites.PiggyBank;
import com.seaeagle.pbmicroservice.entites.User;
import com.seaeagle.pbmicroservice.handlers.Handler;
import com.seaeagle.pbmicroservice.handlers.HandlingResult;
import com.seaeagle.pbmicroservice.persistence.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.HandlerGrpc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class AddHandler implements Handler {

    private DBManager dbManager;
    private GrpcClient grpcClient;
    @Autowired
    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Autowired
    public void setGrpcClient(GrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    @Override
    public HandlingResult handle(Event event) {
        if (!(event instanceof AddPBEvent)) {
            return new HandlingResult(500, "AddHandler cannot parse " + event.getEventName());
        }
        if (!(event.getData() instanceof AddPBEvent.AddEventData data)) {
            return new HandlingResult(500, "Internal error while trying to extract event data");
        }
        User author = getUser(data);
        String address;
        try {
            address = getNewAddress();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            return new HandlingResult(500, "Internal error while trying get new address");
        }

        PiggyBank newBank = new PiggyBank(
                UUID.randomUUID().toString(),
                data.name,
                data.description,
                author.getId(),
                address,
                data.destination,
                new ArrayList<>(),
                "OPEN"
        );
        dbManager.savePB(newBank);

        return new HandlingResult(200, "Created");
    }


    private User getUser(AddPBEvent.AddEventData data) {
        if (!dbManager.userExists(data.author.username)) {
            User user = new User(
                    data.author.name,
                    data.author.username,
                    data.author.wallet
            );
            dbManager.saveUser(user);
            return user;
        }
        return dbManager.getUser(data.author.username);
    }

    private String getNewAddress() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        var reply = (GenerateWalletReply.GenerateWalletMessage) grpcClient.handle(new GenerateWalletEvent()).getMessage();
        return reply.getWallet();
    }
}
