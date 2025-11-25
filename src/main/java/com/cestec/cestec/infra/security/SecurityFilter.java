package com.cestec.cestec.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cestec.cestec.repository.userRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    tokenService tokenservice;

    @Autowired
    userRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {        
        String path = request.getServletPath();
        String method = request.getMethod();

        if (path.equals("/webhook") && method.equals("POST") ||
            path.equals("/auth/login") ||
            path.equals("/login") ||
            path.equals("/impressao") ||
            path.startsWith("/css/**") ||
            path.startsWith("/js/**") ||
            path.startsWith("/icons/**") ||
            path.equals("/")) {
            
            filterChain.doFilter(request, response);
            return; 
        }

        var token = this.recoverToken(request);

        if(token != null){
            var login = tokenservice.validateToken(token);
            if(login != null){
                UserDetails user = userRepository.findByLogin(login);
                if(user != null){
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    
    private String recoverToken(HttpServletRequest request){
        //var authHeader = request.getHeader("Authorization");
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies){
                if("authToken".equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
