package com.aca.rest.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee 
{
	private Integer id;
    private String lastName;
    private String firstName;
    private Integer titleId;
    private Integer titleOfCourtesyId;
    private Integer hobbyId;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String address;
    private String city;
    private String stateId;
    private String zipcode;
    private String personalPhone;
    private String extension;
    private String picture;
    private String notes;
    private Integer reportsToEmployeeId;
    private BigDecimal salary;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public Integer getTitleId() {
        return titleId;
    }
    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }
    public Integer getTitleOfCourtesyId() {
        return titleOfCourtesyId;
    }
    public void setTitleOfCourtesyId(Integer titleOfCourtesyId) {
        this.titleOfCourtesyId = titleOfCourtesyId;
    }
    public Integer getHobbyId() {
        return hobbyId;
    }
    public void setHobbyId(Integer hobbyId) {
        this.hobbyId = hobbyId;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStateId() {
        return stateId;
    }
    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getPersonalPhone() {
        return personalPhone;
    }
    public void setPersonalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Integer getReportsToEmployeeId() {
        return reportsToEmployeeId;
    }
    public void setReportsToEmployeeId(Integer reportssToEmployeeId) {
        this.reportsToEmployeeId = reportssToEmployeeId;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    
}
