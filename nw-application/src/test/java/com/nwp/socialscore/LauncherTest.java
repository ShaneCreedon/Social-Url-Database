package com.nwp.socialscore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Test;

public class LauncherTest {

    @Test
    public void callMain_testAllOperations_verifySystemOutput() {
        System.out.println("main");
        final InputStream fileInputStream = this.getClass().getClassLoader().getResourceAsStream("launcher.input.test.data");
        System.setIn(fileInputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Launcher.main(null);

        String expectedOutput = "+--------------------------------------------------------------------------+\n" +
                "| -------            URL Storage System Started                    ------- |\n" +
                "| Commands Available: ADD, REMOVE, EXPORT                                  |\n" +
                "| Description: Domain's can be stored and can be compared with other URLs  |\n" +
                "|              from their score metric.                                    |\n" +
                "|              Try add, remove and export some valid URLs to see           |\n" +
                "|              how it works!                                               |\n" +
                "+--------------------------------------------------------------------------+\n" +
                "\r\n" +
                "domain;urls;social_score\r\n" +
                "test.com;4;71.74\r\n" +
                "nw.com;2;180.00\r\n" +
                "domain;urls;social_score\r\n" +
                "test.com;3;41.74\r\n";

        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

}