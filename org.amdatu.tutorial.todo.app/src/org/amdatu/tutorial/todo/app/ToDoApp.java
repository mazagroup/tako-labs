package org.amdatu.tutorial.todo.app;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.amdatu.web.rest.jaxrs.AmdatuWebRestConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Component(service = Application.class, property = {
		Constants.SERVICE_RANKING+":Integer=-1",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN+"=/*",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX+"=/web",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT+"="+ToDoApp.CONTEXT_SELECT,
		JaxrsWhiteboardConstants.JAX_RS_NAME+"="+ToDoApp.JAX_RS_APPLICATION_NAME,
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE+"=/rest",
		AmdatuWebRestConstants.JAX_RS_APPLICATION_CONTEXT+"="+ToDoApp.CONTEXT_NAME})
public class ToDoApp extends Application {

    public static final String JAX_RS_APPLICATION_NAME = "org.amdatu.tutorial.todo.app";

    public static final String CONTEXT_NAME = "org.amdatu.tutorial.todo.app";

    public static final String CONTEXT_SELECT =
                    "(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + CONTEXT_NAME + ")";

    private Set<Object> singletons;

    public ToDoApp() {
        singletons = Collections.singleton(new JacksonJsonProvider());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
