package com.nwp.socialscore.repl.infrastructure.service;

import com.nwp.socialscore.repl.application.domain.model.OperationType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SocialScoreUrlValidatorTest {

    private SocialScoreUrlValidator socialScoreUrlValidator;

    @Before
    public void setUp() {
        this.socialScoreUrlValidator = new SocialScoreUrlValidator();
    }

    @Test
    public void isInvalidOperationCommand_validatePossibleInput() {
        String command = "ADD";
        boolean res = socialScoreUrlValidator.isInvalidOperationCommand(command);
        Assert.assertFalse(res);

        command = "REMOVE";
        res = socialScoreUrlValidator.isInvalidOperationCommand(command);
        Assert.assertFalse(res);

        command = "EXPORT";
        res = socialScoreUrlValidator.isInvalidOperationCommand(command);
        Assert.assertFalse(res);

        command = "WRONG INPUT";
        res = socialScoreUrlValidator.isInvalidOperationCommand(command);
        Assert.assertTrue(res);
    }

    @Test
    public void validateOperationTypeArguments_verifyValidCases_noExceptionShouldThrow() {
        List<String> input = Arrays.asList("ADD", "https://www.test.com", "50.04");
        socialScoreUrlValidator.validateOperationTypeArguments(input, OperationType.ADD);

        input = Arrays.asList("REMOVE", "https://www.test.com");
        socialScoreUrlValidator.validateOperationTypeArguments(input, OperationType.REMOVE);
    }

    @Test
    public void validateOperationTypeArguments_verifyInvalidCases() {
        String expectedErrorForAddOperation = "Invalid input - The 'ADD' operation requires 2 subsequent arguments.";
        String expectedErrorForRemoveOperation = "Invalid input - The 'REMOVE' operation requires 1 subsequent argument.";

        final List<String> addInputA = Arrays.asList("ADD", "https://www.test.com", "50.04", "10.53");
        IllegalStateException e = Assert.assertThrows(IllegalStateException.class,
                () -> socialScoreUrlValidator.validateOperationTypeArguments(addInputA, OperationType.ADD)
        );
        Assert.assertEquals(e.getMessage(), expectedErrorForAddOperation);

        final List<String> addInputB = Arrays.asList("ADD", "https://www.test.com");
        e = Assert.assertThrows(IllegalStateException.class,
                () -> socialScoreUrlValidator.validateOperationTypeArguments(addInputB, OperationType.ADD)
        );
        Assert.assertEquals(e.getMessage(), expectedErrorForAddOperation);

        final List<String> removeInputA = Arrays.asList("REMOVE", "https://www.test.com", "50.04");
        e = Assert.assertThrows(IllegalStateException.class,
                () -> socialScoreUrlValidator.validateOperationTypeArguments(removeInputA, OperationType.REMOVE)
        );
        Assert.assertEquals(e.getMessage(), expectedErrorForRemoveOperation);

        final List<String> removeInputB = Collections.singletonList("REMOVE");
        e = Assert.assertThrows(IllegalStateException.class,
                () -> socialScoreUrlValidator.validateOperationTypeArguments(removeInputB, OperationType.REMOVE)
        );
        Assert.assertEquals(e.getMessage(), expectedErrorForRemoveOperation);
    }

}