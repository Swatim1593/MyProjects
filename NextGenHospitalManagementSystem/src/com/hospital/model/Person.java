package com.hospital.model;

import java.util.Objects;

public abstract class Person {
	protected String id;
	protected String name;
	protected int age;
	protected String phone;
	protected Gender gender;
	protected Address address;
	public Person(String id,String name,int age,String phone,Gender gender,Address address) {

	this.id = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phone = phone;
    this.address = address;
}

public String getId() { return id; }
public String getName() { return name; }
public int getAge() { return age; }
public Gender getGender() { return gender; }
public String getPhone() { return phone; }
public Address getAddress() { return address; }

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(id, person.id);
}

@Override
public int hashCode() {
    return Objects.hash(id);
}
}


