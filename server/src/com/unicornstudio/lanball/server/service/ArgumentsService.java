package com.unicornstudio.lanball.server.service;

import com.unicornstudio.lanball.server.enumeration.Arguments;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ArgumentsService {

    private ArgumentParser parser;

    private Namespace namespace;

    public ArgumentsService() {
        parser = ArgumentParsers.newFor("server").build()
                .description("LanBall dedicated server");
        for (Arguments argument: Arguments.values()) {
            parser.addArgument(argument.getArgument()).help(argument.getDescription());
        }
    }

    public void parse(String[] arguments) {
        try {
            namespace = parser.parseArgs(arguments);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }
    }

}
