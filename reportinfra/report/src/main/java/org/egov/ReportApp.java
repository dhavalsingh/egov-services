package org.egov;
import java.io.File;

import org.egov.domain.model.ReportDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;


@SpringBootApplication
public class ReportApp{

	public static final Logger LOGGER = LoggerFactory.getLogger(ReportApp.class);

    @Autowired
    public static ResourceLoader resourceLoader;
   
    @Autowired
    private Environment env;
    
    public ReportApp(ResourceLoader resourceLoader) {
    	this.resourceLoader = resourceLoader;
    }
    
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ReportApp.class, args);
		
	}
	

}