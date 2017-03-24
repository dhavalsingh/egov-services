package org.egov.lams.contract;

import org.egov.lams.model.Allottee;
import org.egov.lams.model.RequestInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CreateUserRequest {
	
	@JsonProperty("RequestInfo")
    private RequestInfo requestInfo;
	
	@JsonProperty("User")
    private Allottee user;
}
