package com.unicornstudio.lanball.server.enumeration;

public enum Arguments {
    PORT("--port", "-p", "server port"),
    LOG("--log", "-l", "log file path");

    private String argument;
    private String alternativeArgument;
    private String description;

    Arguments(String argument, String alternativeArgument, String description) {
        this.argument = argument;
        this.alternativeArgument = alternativeArgument;
        this.description = description;
    }

    public String getArgument() {
        return argument;
    }

    public String getAlternativeArgument() {
        return alternativeArgument;
    }

    public String getDescription() {
        return description;
    }
}
