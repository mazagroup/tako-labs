package org.amdatu.tutorial.todo.app;


import java.net.URL;
import java.util.Set;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;


@Component(service = ServletContextHelper.class, 
		scope=ServiceScope.PROTOTYPE
		,
		property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME+"="+ToDoApp.CONTEXT_NAME,
		HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH+"=/todo"})
public class ToDoServletContextHelper extends ServletContextHelper {

    private ServletContextHelper delegatee;
    
    @Activate
    private void activate(final ComponentContext ctx) {
        delegatee = new ServletContextHelper(ctx.getUsingBundle()) {};
    }
 
    @Override
    public URL getResource(String name) {
        return delegatee.getResource(name);
    }
 
    @Override
    public String getMimeType(String name) {
        return delegatee.getMimeType(name);
    }
 
    @Override
    public Set<String> getResourcePaths(String path) {
        return delegatee.getResourcePaths(path);
    }
 
    @Override
    public String getRealPath(String path) {
        return delegatee.getRealPath(path);
    }
}
