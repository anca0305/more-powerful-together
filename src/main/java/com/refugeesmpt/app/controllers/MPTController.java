package com.refugeesmpt.app.controllers;

import com.refugeesmpt.app.dao.User;
import com.refugeesmpt.app.repositories.EventRepository;
import com.refugeesmpt.app.dao.Event;
import com.refugeesmpt.app.dao.Eventenrollment;
import com.refugeesmpt.app.repositories.EventenrollmentRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MPTController {
	@Autowired
	EventRepository eventsRespository;
	@Autowired
	EventenrollmentRepository eventsenrollmentRespository;

	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleEmail(String email, String body, String subject)
	{
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("pwebtest99@gmail.com");
		message.setTo(email);
		message.setText(body);
		message.setSubject(subject);

		mailSender.send(message);
		System.out.println("Email send to : "+email);

	}
	
	public String getManagementApiToken() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    JSONObject requestBody = new JSONObject();
	    requestBody.put("client_id", "EemtfAzcoNY6AnxTH1XlSkl9HmFdkMDE");
	    requestBody.put("client_secret", "3ai8K1JxRoW3ysEMOkuALpMM-RKrX4GB6ueuT8D9-l6GVeDTOVwKZ3uhxfWKnx94");
	    requestBody.put("audience", "https://dev-rxpyu1l6.us.auth0.com/api/v2/");
	    requestBody.put("grant_type", "client_credentials"); 

	    HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

	    RestTemplate restTemplate = new RestTemplate();
	    HashMap<String, String> result = restTemplate
	      .postForObject("https://dev-rxpyu1l6.us.auth0.com/oauth/token", request, HashMap.class);

	    return result.get("access_token");
	}
	
	@GetMapping(value="/users")
    @ResponseBody
    public ResponseEntity<String> users(HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getManagementApiToken());
        
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate
          .exchange("https://dev-rxpyu1l6.us.auth0.com/api/v2/users", HttpMethod.GET, entity, String.class);
        return result;
    }
	
	@GetMapping(value="/users/{id}")
    @ResponseBody
    public ResponseEntity<String> usersId(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getManagementApiToken());
        
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate
          .exchange("https://dev-rxpyu1l6.us.auth0.com/api/v2/users/auth0|"+id, HttpMethod.GET, entity, String.class);
        
        return result;
    }
	
	@PostMapping(value = "/createUser")
	@ResponseBody
	public ResponseEntity<String> createUser(@RequestBody User u) {
	    
	    String url = "https://dev-rxpyu1l6.us.auth0.com/api/v2/users";

	    // create headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Bearer " + getManagementApiToken());
	    
	    Map<String, Object> metadata = new HashMap<>();
	    metadata.put("phone", u.getPhone());
	    metadata.put("username", u.getUsername());
	    metadata.put("country", u.getCountry());
	    metadata.put("city", u.getCity());
	    metadata.put("occupation", u.getOccupation());
	    metadata.put("admin", "false");
	    
	    // create a map for post parameters
	    Map<String, Object> map = new HashMap<>();
	    map.put("email", u.getEmail());
	    map.put("given_name", u.getFname());
	    map.put("family_name", u.getLname());
	    map.put("connection", "Username-Password-Authentication");
	    map.put("password", u.getPassword());
	    map.put("user_metadata", metadata);

	    // build the request
	    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

	    RestTemplate restTemplate = new RestTemplate();
	    
	    ResponseEntity<String> result = restTemplate
	      .postForEntity("https://dev-rxpyu1l6.us.auth0.com/api/v2/users", entity, String.class);
	    return result;
	    
	}
	
	@PatchMapping("/changePassword/{id}")
    public String changePassword(@PathVariable String id, @RequestBody String password) throws UnirestException{
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    JSONObject jsonObject= new JSONObject(password);
	    
	    String pass = jsonObject.get("pass").toString();

	    JSONObject requestBody = new JSONObject();
	    requestBody.put("password", pass);
	    requestBody.put("connection", "Username-Password-Authentication");
	    
	    HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate
	      .patchForObject("https://dev-rxpyu1l6.us.auth0.com/api/v2/users/auth0|"+id, request, String.class);
	    
	    return result;
		
    }
	
	@PostMapping("/login/{email}")
    public String login(@PathVariable String email, @RequestBody String body) throws UnirestException{
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    JSONObject jsonObject= new JSONObject(body);
	    
	    String pass = jsonObject.get("pass").toString();

	    JSONObject requestBody = new JSONObject();
	    requestBody.put("grant_type", "password");
	    requestBody.put("username", email);
	    requestBody.put("password", pass);
	    requestBody.put("audience", "https://dev-rxpyu1l6.us.auth0.com/api/v2/");
	    requestBody.put("scope", "read:current_user");
	    requestBody.put("client_id", "EemtfAzcoNY6AnxTH1XlSkl9HmFdkMDE");
	    requestBody.put("client_secret", "3ai8K1JxRoW3ysEMOkuALpMM-RKrX4GB6ueuT8D9-l6GVeDTOVwKZ3uhxfWKnx94");
	    
	    try {
		    HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
	
		    RestTemplate restTemplate = new RestTemplate();
		    ResponseEntity<String> result = restTemplate
		      .postForEntity("https://dev-rxpyu1l6.us.auth0.com/oauth/token", request, String.class);
		   
	    }catch(Exception e)
	    {
	    	return null;
	    }
	    
	    HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        h.set("Authorization", "Bearer " + getManagementApiToken());
        
        HttpEntity<String> entity = new HttpEntity<String>(h);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate
          .exchange("https://dev-rxpyu1l6.us.auth0.com/api/v2/users-by-email?email="+email, HttpMethod.GET, entity, String.class);
        
        return result.getBody().toString();
		
    }
	
	@GetMapping("/welcome")
	public String welcome()
	{
		return getManagementApiToken();
	}
	
	@PostMapping("/event/add")
    public Event createEvent(@RequestBody Event body){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + getManagementApiToken());

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate
				.exchange("https://dev-rxpyu1l6.us.auth0.com/api/v2/users", HttpMethod.GET, entity, String.class);

		JSONArray jsonObject = new JSONArray(result.getBody());

		for (int i = 0; i < jsonObject.length(); i++) {
			JSONObject explrObject = jsonObject.getJSONObject(i);
			sendSimpleEmail(explrObject.getString("email"), "New event on platform More Powerful Together", "MorePowerfulTogether Notification");
		}

		return eventsRespository.save(body);
    }
	
	@GetMapping("/events")
    public List<Event> getEvents(){
        return eventsRespository.findAll();
    }
	
	@GetMapping("/eventsenrollment")
    public List<Eventenrollment> getEventsEnrollment(){
        return eventsenrollmentRespository.findAll();
    }

	@GetMapping("/topvolunteer")
	public List<String> getTopVolunteer(){
		List<Eventenrollment> enroll =  eventsenrollmentRespository.findAll();
		ArrayList<String> users = new ArrayList<String>();
		for(int i = 0; i<enroll.size(); i++)
		{
			users.add(enroll.get(i).getUser_id());
		}

		List<String> top = new ArrayList<String> ();

		Map<String, Long> group = users.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		group.entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEach(entry -> top.add(entry.getKey() + " = " + entry.getValue()));

		return top;

	}

	@GetMapping("/events/{id}")
	public Optional<Event> getEventById(@PathVariable String id){
		int i = Integer.parseInt(id);
		return eventsRespository.findById(i);
	}

	@DeleteMapping("/eventsenrollment/delete/{id}")
	public boolean delete(@PathVariable String id){
		int blogId = Integer.parseInt(id);
		eventsenrollmentRespository.deleteById(blogId);
		return true;
	}

	@PostMapping("/eventsenrollment/add")
	public Eventenrollment createEvent(@RequestBody Eventenrollment body){
		return eventsenrollmentRespository.save(body);
	}
}
