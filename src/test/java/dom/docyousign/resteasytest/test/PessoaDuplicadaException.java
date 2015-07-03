package dom.docyousign.resteasytest.test;


public class PessoaDuplicadaException extends RuntimeException {

	private static final long serialVersionUID = -4933312386935526381L;

	public PessoaDuplicadaException() {
	}

	public PessoaDuplicadaException(String message) {
		super(message);
	}

	public PessoaDuplicadaException(Throwable cause) {
		super(cause);
	}

	public PessoaDuplicadaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PessoaDuplicadaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
