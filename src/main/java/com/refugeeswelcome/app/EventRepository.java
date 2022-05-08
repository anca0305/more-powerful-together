package com.refugeeswelcome.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EventRepository extends JpaRepository<Event, Integer> {
	
	List<Event> findByType(String type);

}
