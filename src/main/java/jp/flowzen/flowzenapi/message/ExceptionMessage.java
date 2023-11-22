package jp.flowzen.flowzenapi.message;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * ExceptionMessage
 */
public class ExceptionMessage {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String message;
  private String path;

  public ExceptionMessage() {
    timestamp = LocalDateTime.now();
  }

  public ExceptionMessage(String message, String path) {
    this();
    this.message = message;
    this.path = path;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }


}