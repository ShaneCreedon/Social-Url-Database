package com.nwp.socialscore.repl.infrastructure.service;

import com.nwp.socialscore.repl.application.domain.model.OperationType;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SocialScoreValidator {

    private static final String ADD_ARGS_LENGTH_ERROR_MESSAGE = "Invalid input - The 'ADD' operation requires 2 subsequent arguments.";
    private static final String REMOVE_ARGS_LENGTH_ERROR_MESSAGE = "Invalid input - The 'REMOVE' operation requires 1 subsequent argument.";

    private static final Set<String> operationTypes = EnumSet.allOf(OperationType.class).stream()
            .map(OperationType::name)
            .collect(Collectors.toSet());

    public boolean isInvalidOperationCommand(String commandInputItem) {
        return !operationTypes.contains(commandInputItem);
    }

    public void validateOperationTypeArguments(List<String> inputList, OperationType operationType) {
        if (operationType == OperationType.ADD) {
            if (inputList.size() != 3) {
                throw new IllegalStateException(ADD_ARGS_LENGTH_ERROR_MESSAGE);
            }
        } else if (operationType == OperationType.REMOVE) {
            if (inputList.size() != 2) {
                throw new IllegalStateException(REMOVE_ARGS_LENGTH_ERROR_MESSAGE);
            }
        }
    }
}
