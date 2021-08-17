package com.seaeagle.pbmicroservice.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HandlingResult {
    @Getter
    private int code;
    @Getter
    private String message;
}
