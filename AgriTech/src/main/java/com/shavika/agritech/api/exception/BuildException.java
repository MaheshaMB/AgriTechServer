package com.shavika.agritech.api.exception;

public class BuildException extends ShavikaException {
	public BuildException() {
		super();

	}

	public BuildException(String message) {
		super(message);
	}

	public BuildException(Throwable exc) {
		super(BuildException.getStackTrace(exc));
	}

}
