package com.souqalmal.providers;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;


@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class Authentication implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequest) {
		containerRequest.setProperty("requestTime", System.currentTimeMillis());
	}
}