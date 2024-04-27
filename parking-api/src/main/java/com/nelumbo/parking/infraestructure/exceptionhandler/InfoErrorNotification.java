package com.nelumbo.parking.infraestructure.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfoErrorNotification {

    private String exceptionName;
    private String message;
    private Integer statusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;
    private String controllerError;

    @Override
    public String toString() {
        return "ControllerOriginError: " + controllerError + '\'' + "\n" +
                "Exception: " + exceptionName + '\'' + "\n" +
                "Message: " + message + '\'' + "\n" +
                "StatusCode: " + statusCode + '\'' + "\n" +
                "TimeStamp: " + timestamp + '\'' + "\n";
    }
}
