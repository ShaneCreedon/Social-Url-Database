package com.nwp.socialscore.repl.application.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SocialScoreOperationServiceTest {

    private SocialScoreOperationService socialScoreOperationService;

    @Before
    public void setUp() {
        this.socialScoreOperationService = new SocialScoreOperationService();
    }

    @Test
    public void addUrlWithSocialScore_shouldAddSocialScore() {
        String url = "https://www.test.com/junit";
        String score = "50.40";

        socialScoreOperationService.addUrlWithSocialScore(url, score);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        socialScoreOperationService.exportUrlSocialScores();

        String expectedOutput = "domain;urls;social_score\r\n" +
                                "test.com;1;50.40\r\n";

        Assert.assertNotNull(outputStream.toString());
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void addUrlWithSocialScore_shouldThrowException_nonNumericScorePassed() {
        String url = "https://www.test.com/junit";
        String score = "NON_NUMERIC_VALUE";
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class,
                () -> socialScoreOperationService.addUrlWithSocialScore(url, score)
        );
        Assert.assertEquals("The value passed is not a valid numeric type [integer/decimal].", e.getMessage());
    }

    @Test
    public void removeUrlWithSocialScore_shouldRemoveUrlFromDomainGroup() {
        String url = "https://www.test.com/junit";
        String score = "50.40";

        socialScoreOperationService.addUrlWithSocialScore(url, score);
        socialScoreOperationService.findUrlInStoreAndRemove(url);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        socialScoreOperationService.exportUrlSocialScores();

        String expectedOutput = "domain;urls;social_score\r\n";

        Assert.assertNotNull(outputStream.toString());
        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

}