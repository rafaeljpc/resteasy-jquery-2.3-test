package com.docyousign.resteasytest;


public class PessoaInexistenteException extends Exception {

	private static final long serialVersionUID = -4933312386935526381L;

	public PessoaInexistenteException() {
	}

	public PessoaInexistenteException(String message) {
		super(message);
	}

	public PessoaInexistenteException(Throwable cause) {
		super(cause);
	}

	public PessoaInexistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public PessoaInexistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
