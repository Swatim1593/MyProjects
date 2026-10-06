package com.hospital.model;


	public class Patient extends Person {
	    private String bloodGroup;
	    private String insurancePolicyNo;
	    private boolean isAdmitted;
	    private PatientPriority priority;

	    private Patient(Builder builder) {
	    	super(builder.id, builder.name, builder.age, builder.phone, builder.gender, builder.address);
	        this.bloodGroup = builder.bloodGroup;
	        this.insurancePolicyNo = builder.insurancePolicyNo;
	        this.isAdmitted = builder.isAdmitted;
	        this.priority = builder.priority;

	    }

	    public String getBloodGroup() { return bloodGroup; }
	    public String getInsurancePolicyNo() { return insurancePolicyNo; }
	    public boolean isAdmitted() { return isAdmitted; }
	    public PatientPriority getPriority() { return priority; }
	    public void setAdmitted(boolean admitted) { isAdmitted = admitted; }

	    public static class Builder {
	        private String id;
	        private String name;
	        private int age;
	        private Gender gender;
	        private String phone;
	        private Address address;
	        private String bloodGroup;
	        private String insurancePolicyNo;
	        private boolean isAdmitted = false;
	        private PatientPriority priority = PatientPriority.LOW;

	        public Builder setId(String id) { this.id = id; return this; }
	        public Builder setName(String name) { this.name = name; return this; }
	        public Builder setAge(int age) { this.age = age; return this; }
	        public Builder setGender(Gender gender) { this.gender = gender; return this; }
	        public Builder setPhone(String phone) { this.phone = phone; return this; }
	        public Builder setAddress(Address address) { this.address = address; return this; }
	        public Builder setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; return this; }
	        public Builder setInsurancePolicyNo(String insurancePolicyNo) { this.insurancePolicyNo = insurancePolicyNo; return this; }
	        public Builder setAdmitted(boolean admitted) { isAdmitted = admitted; return this; }
	        public Builder setPriority(PatientPriority priority) { this.priority = priority; return this; }

	        public Patient build() {
	            if (age <= 0) throw new IllegalArgumentException("Age must be greater than zero.");
	            if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
	            return new Patient(this);
	        }
	    }

	    @Override
	    public String toString() {
	        return String.format("Patient[ID=%s, Name=%s, Age=%d, Gender=%s, Phone=%s, BloodGroup=%s, Admitted=%b, Priority=%s]",
	                id, name, age, gender, phone, bloodGroup, isAdmitted, priority);
	    }
	

}
