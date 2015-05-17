package org.personalized.dashboard.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sudan on 5/4/15.
 */
public class ApplicationBootstrapper extends GuiceServletContextListener{

    private final Logger LOGGER = LoggerFactory.getLogger(ApplicationBootstrapper.class);
    @Override
    protected Injector getInjector() {

        try {
            ConfigManager.init();
            MongoBootstrap.init();
            MongoBootstrap.getMongoDatabase().runCommand(new Document("ping", 1));
            LOGGER.info("Application initialization has been successful");
        }
        catch (Exception e){
            LOGGER.error("Application initialization failed with exception  ", e);
            System.exit(1);
        }
        return Guice.createInjector(new ServletModule(){

            @Override
            protected void configureServlets(){
                super.configureServlets();

                ResourceConfig resourceConfig = new PackagesResourceConfig("org.personalized.dashboard.controller");
                for (Class<?> resource : resourceConfig.getClasses()) {
                    bind(resource);
                }
                serve("/*").with(GuiceContainer.class);
            }
        }, new DIModule());
    }
}
