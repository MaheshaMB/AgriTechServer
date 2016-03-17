package com.shavika.agritech.api.exception;

public class Test {

	public static void main(String[] args) throws AgriTechAppException {
		Test t = new Test();
		try {
			t.add1();
		} catch (Exception e) {
			System.out.println("Exception............./1");
			// e.printStackTrace();
			throw new AgriTechAppException("I am calling...", e);
			// throw e;
		}

	}

	public void add1() throws AgriTechAppException {
		this.add2();
	}

	public void add2() throws AgriTechAppException {
		new test1().sub();
	}

	public void add() throws AgriTechAppException {
		String str = null;
		// try {
		throw new AgriTechAppException("i can not pridicted...");

		// str = str.substring(0, 4);
		// if (str.contains("ss")) {
		// System.out.println("condition...");
		// }
		// str = str.substring(0, 1);
		// System.out.println(str);
		// } catch (Exception ex) {
		// System.out.println("Exception............./2");
		// // throw new AgriTechAppException(ex.getLocalizedMessage());
		// throw new AgriTechAppException("I am calling...", ex);
		// }

	}

}
