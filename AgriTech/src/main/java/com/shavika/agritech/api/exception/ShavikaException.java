package com.shavika.agritech.api.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class ShavikaException extends Exception {

	public ShavikaException() {
		super();
		System.out.println("inside the constractor ShavikaException...");
	}

	public ShavikaException(String message) {
		super(message);
		System.out.println("inside the constractor-1 ShavikaException...");
	}

	public ShavikaException(Throwable cause) {
		 super(ShavikaException.getStackTrace(cause));
		//super(cause);
		System.out.println("inside the constractor-2 ShavikaException...");
	}

	public ShavikaException(String message, Throwable cause) {
		//super(ShavikaException.getStackTrace(cause), cause);
		//super(message, cause);
		super(message, cause);
		System.out.println("inside the constractor-3 ShavikaException...");
	}

	public String getStackTraceString() {
		return ShavikaException.getStackTrace(this);
	}

	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));

		return sw.toString();
	} // end of getStackTrace()

}
