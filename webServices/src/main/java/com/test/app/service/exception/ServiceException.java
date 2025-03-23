package com.test.app.service.exception;

public class ServiceException extends RuntimeException{

  public ServiceException(String message, Exception ex) {
    super(message, ex);
  }
}
