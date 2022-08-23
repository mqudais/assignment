package com.souqalmal.providers;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.souqalmal.common.CommonServices;

@Provider
@PreMatching
@Priority(Priorities.AUTHORIZATION)
public class Authorization implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequest) {
		
		String serviceMethod = containerRequest.getMethod().toString();
		String serviceUri = containerRequest.getUriInfo().getPath();
		
		if (serviceUri.endsWith("/")) {
			serviceUri = serviceUri.substring(0, serviceUri.length() - 1);
		}

		if (serviceMethod.equalsIgnoreCase("GET")) {
			boolean startIsNotNull = containerRequest.getUriInfo().getQueryParameters().containsKey("start");
			String startIndex = null;
			if (!startIsNotNull) {
				startIndex = "1";
			} else {
				startIndex = containerRequest.getUriInfo().getQueryParameters().get("start").get(0);
				boolean startIndexIsInteger = CommonServices.isInteger(startIndex);
				if (!startIndexIsInteger) {
					throw new WebApplicationException(Status.REQUESTED_RANGE_NOT_SATISFIABLE);
				} else {
					if (Integer.valueOf(startIndex) < 1) {
						throw new WebApplicationException(Status.REQUESTED_RANGE_NOT_SATISFIABLE);
					}
				}
			}
			
			String maxReadRows = "20";
			boolean offsetIsNull = !containerRequest.getUriInfo().getQueryParameters().containsKey("offset");
			String offset = null;
			if (offsetIsNull) {
				offset = maxReadRows;
			} else {
				offset = containerRequest.getUriInfo().getQueryParameters().get("offset").get(0);
				boolean offsetIsInteger = CommonServices.isInteger(offset);
				if (!offsetIsInteger) {
					throw new WebApplicationException(Status.REQUESTED_RANGE_NOT_SATISFIABLE);
				} else {
					int valueOfOffset = Integer.valueOf(offset);
					int privilegedMaxRows = Integer.valueOf(maxReadRows);;
					if (valueOfOffset <= 0) {
						throw new WebApplicationException(Status.REQUESTED_RANGE_NOT_SATISFIABLE);
					}
					if (privilegedMaxRows < valueOfOffset) {
						throw new WebApplicationException(Status.REQUESTED_RANGE_NOT_SATISFIABLE);
					}
				}
			}
			
			String queryParams = "";
			String queryParameters = containerRequest.getUriInfo().getRequestUri().getRawQuery();
			String[] queryParamsArray = null;
			if (queryParameters != null) {
				queryParamsArray = queryParameters.split("&");
			}
			if (queryParamsArray == null) {
				containerRequest.setRequestUri(
						UriBuilder.fromUri(containerRequest.getUriInfo().getAbsolutePath())
							.queryParam("start",(Integer.valueOf(startIndex) - 1))
							.queryParam("offset", offset).build()
				);
			} else {
				for (int i = 0; i < queryParamsArray.length; i++) {
					if (!queryParamsArray[i].startsWith("start") && !queryParamsArray[i].startsWith("offset")) {
						queryParams = queryParams + "&" + queryParamsArray[i];
					}
				}
				queryParams = queryParams + "&start=" + (Integer.valueOf(startIndex) - 1) + "&offset=" + offset;
				if (queryParams.startsWith("&")) {
					queryParams = queryParams.replaceFirst("&", "");
				}
				containerRequest.setRequestUri(
						UriBuilder.fromUri(containerRequest.getUriInfo().getAbsolutePath())
							.replaceQuery(queryParams).build()
				);
			}
		}
	}
}
