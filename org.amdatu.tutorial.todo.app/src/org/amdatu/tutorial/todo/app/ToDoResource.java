package org.amdatu.tutorial.todo.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.amdatu.tutorial.todo.api.TodoDTO;
import org.amdatu.tutorial.todo.api.TodoService;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

@Component( service = Object.class,property = {
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE+":Boolean="+true,
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT+"="+ToDoApp.JAX_RS_APPLICATION_NAME})
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ToDoResource {

	@Reference
    private volatile TodoService todoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{user}")
    public List<TodoDTO> list(@PathParam("user") String user) {
      return todoService.list(user);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveTodo(TodoDTO todo) {
      todoService.store(todo);
    }

}
