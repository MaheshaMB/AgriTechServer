package com.shavika.agritech.api.exception;

public class AgriTechAppException extends ShavikaException {

	private static final long serialVersionUID = 7733244097382836120L;

	public AgriTechAppException(String str) {
		super(str);
		System.out.println("inside the constractor-1 AgriTechAppException...");
	}

	public AgriTechAppException(Throwable cause) {
		super(cause);
		System.out.println("inside the constractor-2 AgriTechAppException...");
	}

	public AgriTechAppException(String str, Throwable cause) {
		super(str, cause);
		System.out.println("inside the constractor-3 AgriTechAppException...");
	}

}
