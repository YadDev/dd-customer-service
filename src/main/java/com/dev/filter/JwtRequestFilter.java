package com.dev.filter;

import com.dev.constants.CommonConstants;
import com.dev.model.ResponseHandler;
import com.dev.services.impl.DBDetailService;
import com.dev.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    private final DBDetailService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(DBDetailService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(CommonConstants.AUTHORIZATION_HEADER);

        String username = CommonConstants.EMPTY_STRING;
        String jwt = CommonConstants.EMPTY_STRING;

        if (authorizationHeader !=null && authorizationHeader.startsWith(CommonConstants.AUTHORIZATION_BEARER)) {
            jwt = authorizationHeader.substring(CommonConstants.AUTHORIZATION_BEARER.length());
            try {
                username = jwtUtil.extractUsername(jwt);
            }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
                response.setContentType("application/json");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(ResponseHandler.generateResponse(
                        e.getMessage(),
                        HttpStatus.FORBIDDEN,
                        null
                ).toString());
            }
        }


        if (!username.equals(CommonConstants.EMPTY_STRING) && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}

