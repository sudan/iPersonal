package org.personalized.dashboard.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Created by sudan on 5/4/15.
 */
public class ApplicationBootstrapper extends GuiceServletContextListener{

    @Override
    protected Injector getInjector() {

        try {
            ConfigManager.init();
            MongoBootstrap.init();
        }
        catch (Exception e){
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
