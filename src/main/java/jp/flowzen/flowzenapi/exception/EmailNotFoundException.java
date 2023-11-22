package jp.flowzen.flowzenapi.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * EmailNotFoundException
 */
public class EmailNotFoundException extends UsernameNotFoundException {

  public EmailNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}