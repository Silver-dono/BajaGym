package com.bajagym.controllers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoSuchElementExceptionControllerAdvice{
		@ResponseStatus(code=HttpStatus.NOT_FOUND, reason= "Objeto no encontrado")
		@ExceptionHandler(NoSuchElementException.class)
		public void handleNoTFound() {
		}
}
