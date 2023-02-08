package com.griddynamics.lidlbooking.logging;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"log.format=plaintext"})
@DirtiesContext
public class LogbackLogPlaintextTest extends BaseLoggingTest {


    @Test
    public void loggingPlainText() {

        final String poruka = "poruka";
        final String[] log = getLog(poruka).split(" ");

        String timeExpected = log[0];
        String threadExpected = log[2];
        String levelExpected = log[5];
        String classExpected = log[8];
        String messageExpected = log[10];
        String time = log[1].substring(5, 17);
        String level = log[6].substring(5);
        String thread = log[3].substring(5, 9);
        String currentClassExpected = "c.griddynamics.lidlbooking.logging.BaseLoggingTest";
        String currentClass = log[9].substring(5, 55);
        String message = log[11].trim();
        assertAll(() -> assertEquals("Time:", timeExpected),
                () -> assertTrue(time.matches("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}")),
                () -> assertEquals("Thread:", threadExpected),
                () -> assertEquals(Thread.currentThread().getName(), thread),
                () -> assertEquals("Level:", levelExpected),
                () -> assertEquals("INFO", level),
                () -> assertEquals("Class:", classExpected),
                () -> assertEquals(currentClassExpected, currentClass),
                () -> assertEquals("Message:", messageExpected),
                () -> assertEquals(poruka, message)
        );
    }
}
