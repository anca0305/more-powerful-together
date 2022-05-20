package com.refugeesmpt.app.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	private String name;
	private String startdate;
	private String city;
	private String enddate;
	private String type;
	private String organization;
	private String contact;
	private String image;
	private String description;
	private String volunteers;
	private String owner;
	
	public Event() {
		super();
	}

	public Event(int id, String name, String startdate, String city, String enddate, String type, String organization,
			String contact, String image, String description, String volunteers, String owner) {
		super();
		this.id = id;
		this.name = name;
		this.startdate = startdate;
		this.city = city;
		this.enddate = enddate;
		this.type = type;
		this.organization = organization;
		this.contact = contact;
		this.image = image;
		this.description = description;
		this.volunteers = volunteers;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVolunteers() {
		return volunteers;
	}

	public void setVolunteers(String volunteers) {
		this.volunteers = volunteers;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", startdate=" + startdate + ", city=" + city + ", enddate="
				+ enddate + ", type=" + type + ", organization=" + organization + ", contact=" + contact + ", image="
				+ image + ", description=" + description + ", volunteers=" + volunteers + ", owner=" + owner + "]";
	}
	
}
