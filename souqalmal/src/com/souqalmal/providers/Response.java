package com.souqalmal.providers;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.souqalmal.common.CommonServices;

@Provider
public class Response implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext containerRequest,ContainerResponseContext containerResponse) {
		
		Long responseTime = (System.currentTimeMillis()) - ((Long)containerRequest.getProperty("requestTime"));
		containerResponse.getHeaders().add("X-Response-Time-Milliseconds", responseTime);
		containerResponse.getHeaders().add("X-Developed-By", "Mahmoud Qudais");
//		boolean serviceIsOpenWeb = containerRequestPath.startsWith("openWebApi");
		boolean serviceRequestMethodIsGET = containerRequest.getMethod().equalsIgnoreCase("GET");
		boolean serviceResponseIsOK = (containerResponse.getStatus() == 200);
		if (serviceRequestMethodIsGET && serviceResponseIsOK){
			containerResponse.getHeaders().add("Cache-Control", "private, no-cache, max-age=600");
		}
		
		Logger logger = Logger.getLogger(ContainerResponseFilter.class);
		logger.info(CommonServices.convertJavaObjectToJSONstring(containerResponse.getEntity()));
		
	}
}
