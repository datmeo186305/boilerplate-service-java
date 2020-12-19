package com.benit.team.base.jwt;

import com.benit.team.services.JwtService;
import com.benit.team.services.UserDetailsServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private final static String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String accessToken = httpRequest.getHeader(AUTHORIZATION);

        if (jwtService.validateTokenLogin(accessToken)) {
            String username = jwtService.getUsernameFromToken(accessToken);
            UserDetails userDetail = userDetailsService.loadUserByUsername(username);
            if (userDetail != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
                        null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
