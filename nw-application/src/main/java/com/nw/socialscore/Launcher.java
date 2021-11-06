package com.nw.socialscore;

import com.nw.socialscore.repl.application.service.SocialScoreOperationService;
import com.nw.socialscore.repl.application.service.SocialScoreReaderService;
import com.nw.socialscore.repl.infrastructure.service.SocialScoreValidator;

public class Launcher {

    // Note: We could use text blocks here if >= JDK v15.
    private static final String STARTUP_MESSAGE =
                    "+--------------------------------------------------------------------------+\n" +
                    "| -------            URL Storage System Started                  -------   |\n" +
                    "| Commands Available: ADD, REMOVE, EXPORT                                  |\n" +
                    "| Description: Domain's can be stored and can be compared with other URLs  |\n" +
                    "|              from their score metric.                                    |\n" +
                    "|              Try add, remove and export some valid URLs to see           |\n" +
                    "|              how it works!                                               |\n" +
                    "+--------------------------------------------------------------------------+\n";

    public static void main(String[] args) {
        System.out.println(STARTUP_MESSAGE);
        SocialScoreValidator socialScoreValidator = new SocialScoreValidator();
        SocialScoreOperationService socialScoreOperationService = new SocialScoreOperationService();
        SocialScoreReaderService socialScoreReaderService = new SocialScoreReaderService(socialScoreValidator, socialScoreOperationService);
        socialScoreReaderService.startRepl();
    }
}
