package com.seaeagle.pbmicroservice;

import com.seaeagle.pbmicroservice.controllers.MainController;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class GrpcServer {

    MainController mainController;

    @Autowired
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @PostConstruct
    void main(){
        Server server = ServerBuilder
                .forPort(8001)
                .addService(mainController).build();

        try {
            server.start();
            Logger.getLogger("GRPCServer").info("Server successfully started");
            server.awaitTermination();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
