package com.example;

public class Logic {

	static String check(Student s) throws MyOwnException {
		if (s.getName().length() > 2)
			return s.getName().toUpperCase();
		else

			throw new MyOwnException("Student name must be >2 characters....");

	}
}
