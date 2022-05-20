package com.refugeesmpt.app.repositories;

import java.util.List;

import com.refugeesmpt.app.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
	
	List<Event> findByType(String type);

}
