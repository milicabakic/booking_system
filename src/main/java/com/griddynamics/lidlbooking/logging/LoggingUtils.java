package com.griddynamics.lidlbooking.logging;

public class LoggingUtils {
    public static final int SIZE = 1024;

    public static int parseBodySize(final String bodySize) {

        int size;

        String bodySizeNoSpaces = bodySize.toLowerCase().replaceAll("\\s", "");

        if (bodySizeNoSpaces.toLowerCase().matches("\\d+[a-z]+")) {
            size = Integer.parseInt(bodySize.replaceAll("\\D", ""));
            if (bodySizeNoSpaces.toLowerCase().contains("kb")) {
                size *= SIZE;
            } else if (bodySizeNoSpaces.toLowerCase().contains("mb")) {
                size *= SIZE * SIZE;
            }
        } else {
            size = Integer.parseInt(bodySize);
        }

        return size;
    }
}
