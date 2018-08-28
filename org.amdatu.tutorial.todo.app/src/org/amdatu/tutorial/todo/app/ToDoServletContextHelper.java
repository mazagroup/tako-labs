package org.amdatu.tutorial.todo.app;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.Property;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;


@Component(provides = ServletContextHelper.class)
@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, value = ToDoApp.CONTEXT_NAME)
@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, value = "/todo")
public class ToDoServletContextHelper implements ServiceFactory<ServletContextHelper> {

    @Override
    public ServletContextHelper getService(Bundle bundle, ServiceRegistration<ServletContextHelper> registration) {
        return new ServletContextHelper(bundle) {};
    }

    @Override
    public void ungetService(Bundle bundle, ServiceRegistration<ServletContextHelper> registration,
        ServletContextHelper service) {
        // nothing here
    }

}
