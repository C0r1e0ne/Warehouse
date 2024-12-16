package org.example.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.example.di.ApplicationContext;
import org.example.service.UserServiceImpl;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new ApplicationContext();
        context.getUserService();

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("appContext", context);
    }
}
