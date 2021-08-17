package com.seaeagle.pbmicroservice;

import com.seaeagle.pbmicroservice.dto.events.AddPBEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PbMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PbMicroserviceApplication.class, args);
    }

}
