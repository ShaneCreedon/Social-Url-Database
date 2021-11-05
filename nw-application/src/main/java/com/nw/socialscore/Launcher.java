package com.nw.socialscore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Launcher {

    private static final String STARTUP_MESSAGE = """
            +--------------------------------------------------------------------------+
            | -------            URL Storage System Started                  -------   |
            | Commands Available: ADD, REMOVE, EXPORT                                  |
            | Description: Domain's can be stored and can be compared with other URLs  |
            |              from their score metric.                                    |
            |              Try add, remove and export some valid URLs to see           |
            |              how it works!                                               |
            +--------------------------------------------------------------------------+
            
            """ ;

    private static final String SINGLE_WHITESPACE = " ";

    private static final String INVALID_COMMAND_ERROR_MESSAGE = "Invalid command entered - Please use [ADD|REMOVE|EXPORT].";
    private static final String ADD_ARGS_LENGTH_ERROR_MESSAGE = "Invalid input - The 'ADD' operation requires 2 subsequent arguments.";
    private static final String REMOVE_ARGS_LENGTH_ERROR_MESSAGE = "Invalid input - The 'Remove' operation requires 1 subsequent argument.";
    private static final String INVALID_URL_ERROR_MESSAGE = "Invalid URL passed when performing ADD/REMOVE operation.";
    private static final String INVALID_NUMERIC_ADD_VALUE_ERROR_MESSAGE = "The value passed is not a valid numeric type [integer/decimal].";
    private static final String ITEM_NOT_FOUND_FOR_REMOVAL_ERROR = "URL cannot be removed from store as it does not exist - please try adding one first.";

    private static final Set<String> operationTypes = EnumSet.allOf(OperationType.class).stream()
            .map(OperationType::name)
            .collect(Collectors.toSet());

    private static Map<String, List<SocialScore>> domainToScoreMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println(STARTUP_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            List<String> inputList = Arrays.asList(scanner.nextLine().split(SINGLE_WHITESPACE));
            if (inputList.isEmpty()) {
                scanner.next();
            }
            String commandInputItem = inputList.remove(0);
            if (isInvalidOperationCommand(commandInputItem)) {
                throw new IllegalStateException(INVALID_COMMAND_ERROR_MESSAGE);
            }
            OperationType operationType = OperationType.valueOf(commandInputItem);
            validateOperationTypeArguments(inputList, operationType);

            switch (operationType) {
                case ADD -> addUrlWithSocialScore(inputList.get(0), inputList.get(1));
                case REMOVE -> removeUrlFromSocialScoreContainer(inputList.get(0));
                case EXPORT -> exportUrlSocialScores();
                default -> throw new IllegalStateException("Operation Type has not been implemented");
            }
        }
    }

    private static void addUrlWithSocialScore(String inputUrl, String inputScore) {
        URL url = createUrlFromArgument(inputUrl);
        BigDecimal score = createScoreFromArgument(inputScore);
        String domainName = url.getHost();
        domainToScoreMap.computeIfAbsent(domainName, (domain -> new ArrayList<>())).add(new SocialScore(url, score));
    }

    private static void removeUrlFromSocialScoreContainer(String inputUrl) {
        URL url = createUrlFromArgument(inputUrl);
        String domainName = url.getHost();
        if (!domainToScoreMap.containsKey(domainName)) {
            throw new IllegalStateException(ITEM_NOT_FOUND_FOR_REMOVAL_ERROR);
        }
    }

    private static void exportUrlSocialScores() {
        System.out.println("domain;urls;social_score");
        domainToScoreMap.forEach((domainName, urlScoreList) -> {
            BigDecimal socialScoreForDomain = urlScoreList.stream()
                    .map(SocialScore::getScore)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            String domainResult = String.format("%s;%d;%f", domainName, urlScoreList.size(), socialScoreForDomain.setScale(2, RoundingMode.HALF_EVEN));
            System.out.println(domainResult);
        });
    }

    private static boolean isInvalidOperationCommand(String commandInputItem) {
        return !operationTypes.contains(commandInputItem);
    }

    private static void validateOperationTypeArguments(List<String> inputList, OperationType operationType) {
        if (operationType == OperationType.ADD) {
            if (inputList.size() != 2) {
                throw new IllegalStateException(ADD_ARGS_LENGTH_ERROR_MESSAGE);
            }
        } else if (operationType == OperationType.REMOVE) {
            if (inputList.size() != 1) {
                throw new IllegalStateException(REMOVE_ARGS_LENGTH_ERROR_MESSAGE);
            }
        }
    }

    private static BigDecimal createScoreFromArgument(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(INVALID_NUMERIC_ADD_VALUE_ERROR_MESSAGE, e);
        }
    }

    private static URL createUrlFromArgument(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(INVALID_URL_ERROR_MESSAGE, e);
        }
    }

}
