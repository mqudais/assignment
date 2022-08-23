package com.souqalmal.tracker;

//import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.souqalmal.common.CommonServices;
import com.souqalmal.common.Message;


@Path("track")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class TrackerResources {
	
	@GET
	@Path("trackValue")
	public Response trackValue(@QueryParam("input") String input) {
		GenericEntity<String> genericResponseObject = null;
		Message errorMessage = null;
		boolean errorOccurred = false;
		Output result = null;
		
		if(input==null || input.length()<=0) {
			errorMessage = new Message();
			errorMessage.setMessage("Please enter a value");
			errorMessage.setMessageType("ERROR");
			errorOccurred = true;
		}
		
		if(!errorOccurred) {
			result = TrackerServices.inputData(input);
			
			if (result == null){
				errorMessage = new Message();
				errorMessage.setMessage("Insufficient Data");
				errorMessage.setMessageType("ERROR");
				errorOccurred = true;
			}
		}
		
		if (errorOccurred) {
			genericResponseObject = new GenericEntity<String>(CommonServices.convertJavaObjectToJSONstring(errorMessage)) {};
			return Response.status(Status.BAD_REQUEST).entity(genericResponseObject).build();
		}else{
			genericResponseObject = new GenericEntity<String>(CommonServices.convertJavaObjectToJSONstring(result)) {};
			return Response.status(Status.OK).entity(genericResponseObject).build();
		}
	}
	
	@GET
	@Path("getHistory")
	public Response getHistory() {
		GenericEntity<String> genericResponseObject = new GenericEntity<String>(CommonServices.convertJavaObjectToJSONstring(TrackerServices.trackInputHistory())) {};
		return Response.status(Status.OK).entity(genericResponseObject).build();
	}
}
