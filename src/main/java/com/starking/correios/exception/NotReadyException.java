package com.starking.correios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.SERVICE_UNAVAILABLE, reason="Serviço está em instalação. Favor aguarde de 3 a 5 minutos")
public class NotReadyException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

}
