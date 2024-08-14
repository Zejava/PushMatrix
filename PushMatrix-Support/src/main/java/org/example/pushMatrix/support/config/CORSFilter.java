package org.example.pushMatrix.support.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author 泽
 * @Date 2024/8/14 5:48
 */
//@Component
//public class CORSFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletResponse res = (HttpServletResponse) response;
//        res.addHeader("Access-Control-Allow-Credentials", "true");
//        res.addHeader("Access-Control-Allow-Origin", "http://localhost:3000\n");
//        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//        res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
//        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
//            response.getWriter().println("ok");
//            return;
//        }
//        chain.doFilter(request, response);
//    }
//    @Override
//    public void destroy() {
//    }
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//}