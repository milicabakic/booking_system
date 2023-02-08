package com.griddynamics.lidlbooking.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private UUID uuid;

    private String message;

    private String time;

    @Override
    public String toString() {
        return "ErrorDTO{"
                + "uuid=" + uuid
                + ", message='" + message + '\''
                + ", time='" + time + '\'' + '}';
    }
}
