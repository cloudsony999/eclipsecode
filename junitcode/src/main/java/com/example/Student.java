package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

	private int id;
	private String name;

	public Student(String name) {
		super();
		this.name = name;
	}

}
