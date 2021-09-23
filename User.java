package com.java.test;

public class User {

	String name;
	String age;
	String grade;
	Long id;
	
	public String toString() {
		return "Long: "+id+" Name : "+name+" Age : "+age+" Grade"+grade;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode()+age.hashCode()+grade.hashCode()+id.intValue();
	}
	
	@Override
	public boolean equals(Object user) {
		return id.equals(((User)user).id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
