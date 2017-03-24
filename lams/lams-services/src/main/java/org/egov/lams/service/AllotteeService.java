package org.egov.lams.service;

import java.util.ArrayList;
import java.util.List;
import org.egov.lams.config.PropertiesManager;
import org.egov.lams.contract.AllotteeResponse;
import org.egov.lams.contract.CreateUserRequest;
import org.egov.lams.contract.UserResponse;
import org.egov.lams.contract.UserSearchRequest;
import org.egov.lams.exception.LamsException;
import org.egov.lams.model.Allottee;
import org.egov.lams.model.RequestInfo;
import org.egov.lams.model.enums.Gender;
import org.egov.lams.model.enums.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class AllotteeService {
	
	public static final Logger logger = LoggerFactory.getLogger(Allottee.class);

	@Autowired
	private PropertiesManager propertiesManager;

	public AllotteeResponse getAllottees(Allottee allottee,RequestInfo requestInfo) {

		RestTemplate restTemplate = new RestTemplate();
		String url = propertiesManager.getAllotteeServiceHostName()
				+ propertiesManager.getAllotteeServiceBasePAth()
				+ propertiesManager.getAllotteeServiceSearchPath();
		
		UserSearchRequest userSearchRequest = new UserSearchRequest();
		// FIXME just setting id field, change when they user search with
		// modelattribute rather than request body
		userSearchRequest.setRequestInfo(requestInfo);
		List<Long> allotteeId = new ArrayList<>();
		allotteeId.add(allottee.getId());
		userSearchRequest.setId(allotteeId);
		
		AllotteeResponse allotteeResponse = null;
		try{
			allotteeResponse = restTemplate.postForObject(url, userSearchRequest, AllotteeResponse.class);
		}catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new RuntimeException(e.getMessage()+e.getCause());
		}
		return allotteeResponse;
	}
	
	public void createAllottee(Allottee allottee,RequestInfo requestInfo) {
		
		RestTemplate restTemplate = new RestTemplate();
		 String url = propertiesManager.getAllotteeServiceHostName()
			    	+ propertiesManager.getAllotteeServiceBasePAth()
			    	+ propertiesManager.getAllotteeServiceCreatePAth();
		/* allottee.setUserName(allottee.getName()+allottee.getMobileNumber());
		 allottee.setPassword(allottee.getMobileNumber().toString());
		 allottee.setGender(Gender.MALE);
		 allottee.setType(UserType.CITIZEN);
		 allottee.setActive(true);*/
		 //TODO will change in future gender will not be mandatory
		 //FIXME set user name and password using any gen service
		 CreateUserRequest userRequest = new CreateUserRequest(requestInfo,allottee);
		 System.err.println("ALLOTTEE INPUT GIVEN : "+userRequest);
		 ObjectMapper mapper=new ObjectMapper();
		 try {
			String s=mapper.writeValueAsString(userRequest);
			System.out.println("user object:"+s);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 UserResponse response = null;
		 try{
			 response = restTemplate.postForObject(url, userRequest, UserResponse.class);
		 }catch (RestClientException restClientException) {
			 restClientException.printStackTrace();
			 throw new LamsException(restClientException);
			 // TODO: handle exception
		}
		 allottee.setId(response.getUser().get(0).getId());
	}
}
