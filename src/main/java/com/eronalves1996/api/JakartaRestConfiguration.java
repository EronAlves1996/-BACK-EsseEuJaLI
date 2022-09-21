package com.eronalves1996.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("/")
public class JakartaRestConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(Login.class);
        classes.add(Validate.class);
        classes.add(FilterCORS.class);
        classes.add(Logout.class);
        classes.add(Readed.class);
        return classes;
    }
}
