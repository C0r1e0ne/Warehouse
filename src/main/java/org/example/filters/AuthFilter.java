package org.example.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*") // Применяется ко всем URL
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        boolean isLoginPage = path.endsWith("index.jsp") || path.endsWith("login");

        boolean isLoggedIn = (session != null && session.getAttribute("username") != null);

        if (isLoggedIn || isLoginPage) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect("index.jsp");
        }
    }
}
