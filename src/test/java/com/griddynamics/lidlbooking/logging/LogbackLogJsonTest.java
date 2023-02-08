package com.griddynamics.lidlbooking.logging;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "log.format=json")
@DirtiesContext
public class LogbackLogJsonTest extends BaseLoggingTest {


    @Test
    public void loggingJson() throws JSONException, ParseException {
        long timeMillis = System.currentTimeMillis();
        final String poruka = "poruka";
        final String log = getLog(poruka);
        JSONObject jsonObject = new JSONObject(log);
        assertTrue(jsonObject.has("timestamp"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date parsedDate = dateFormat.parse(jsonObject.getString("timestamp"));
        assertTrue(timeMillis - parsedDate.getTime() < 500);
        assertAll(
                () -> assertTrue(jsonObject.has("level")),
                () -> assertEquals("INFO", jsonObject.getString("level")),
                () -> assertTrue(jsonObject.has("thread")),
                () -> assertEquals(Thread.currentThread().getName(), jsonObject.getString("thread")),
                () -> assertTrue(jsonObject.has("logger")),
                () -> assertEquals("com.griddynamics.lidlbooking.logging.BaseLoggingTest",
                        jsonObject.getString("logger")),
                () -> assertTrue(jsonObject.has("message")),
                () -> assertEquals(poruka, jsonObject.getString("message"))
        );
    }
}
