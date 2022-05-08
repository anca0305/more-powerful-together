package com.refugeeswelcome.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Eventenrollment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	private String id_event;
	private String user_id;
	
	public Eventenrollment() {
		super();
	}

	public Eventenrollment(int id, String id_event, String user_id) {
		super();
		this.id = id;
		this.id_event = id_event;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getId_event() {
		return id_event;
	}

	public void setId_event(String id_event) {
		this.id_event = id_event;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Eventenrollment [id=" + id + ", id_event=" + id_event + ", user_id=" + user_id + "]";
	}
	
	
	
}
