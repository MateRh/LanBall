package com.unicornstudio.lanball.rest;

import lombok.Data;

@Data
public class NickNameResponse {

    private boolean success;

    private String nickname;

    private Float time;

    private Error error;

    private static class Error {

        private String code;

        private String message;

    }
}
