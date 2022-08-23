package com.souqalmal.providers;

//import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.souqalmal.common.CommonServices;
import com.souqalmal.common.Message;


@Provider
public class Exception implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		Message message = new Message();
		message.setMessage(ex.getMessage());
		message.setMessageType("INFO");
		String genericMessage = CommonServices.convertJavaObjectToJSONstring(message);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(genericMessage).build();
	}
}
