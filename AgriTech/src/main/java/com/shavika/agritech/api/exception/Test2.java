package com.shavika.agritech.api.exception;

public class Test2 {

	public Test2() {

	}

	public void mul() throws AgriTechAppException {
		String str = "a";
		str = str.substring(0, 4);
		if (str.contains("ss")) {
			System.out.println("condition...");
		}
		str = str.substring(0, 1);
		System.out.println(str);

	}

}
