package org.amdatu.tutorial.todo.app;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.Property;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(provides = ServletContextHelper.class)
@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, value = ToDoApp.CONTEXT_NAME)
@Property(name = HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, value = "/todo")
public class ToDoServletContextHelper implements ServiceFactory<ServletContextHelper> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoServletContextHelper.class);

    private final class ServletContextHelperImpl extends ServletContextHelper {

        private final Bundle bundle;

        private ServletContextHelperImpl(Bundle bundle) {
            super(bundle);
            this.bundle = bundle;
        }

        @Override
        public URL getResource(final String name) {
            String resourceName;
            if (name.endsWith("/")) {
                // Append index.html when the name ends with '/' this makes index.html the default resource for a directory
                resourceName = name + "index.html";
            }
            else {
                Set<String> resourcePaths = getResourcePaths(name.concat("/"));
                if (resourcePaths != null && !resourcePaths.isEmpty()) {
                    // Don't return a URL for dir's as this results in a white page instead of a 404.
                    LOGGER.debug("Requested resource '{}' is a dir, return null", name);
                    return null;
                }
                resourceName = name;
            }

            URL url = super.getResource(resourceName);

            if (Boolean.parseBoolean(bundle.getBundleContext().getProperty("allow-dev-files"))) {
                File file = new File("..", bundle.getSymbolicName() + resourceName);

                if (file.exists()) {
                    if (file.isFile()) {
                        try {
                            LOGGER.debug("Return to development file url for resource '{}' file '{}'", name, file);
                            url = file.toURI().toURL();
                        }
                        catch (MalformedURLException e) {
                            LOGGER.error("Failed to get development file url for resource '{}' file '{}'", name, file, e);
                        }
                    }
                    else if (file.isDirectory()) {
                        LOGGER.debug("Return 'null' for resource '{}' is dir", name);
                        url = null;
                    }
                }
            }

            return url;
        }

        @Override
        public String getMimeType(String name) {
            if (name.endsWith("/")) {
                return "text/html";
            }
            return super.getMimeType(name);
        }
    }

    @Override
    public ServletContextHelper getService(Bundle bundle, ServiceRegistration<ServletContextHelper> registration) {
        return new ServletContextHelperImpl(bundle);
    }

    @Override
    public void ungetService(Bundle bundle, ServiceRegistration<ServletContextHelper> registration,
        ServletContextHelper service) {
        // nothing here
    }

}