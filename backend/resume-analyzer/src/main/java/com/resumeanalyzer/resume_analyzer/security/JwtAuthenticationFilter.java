package com.resumeanalyzer.resume_analyzer.security;

import com.resumeanalyzer.resume_analyzer.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

//        if there is no authorization header continue to chain without further moving jwt task
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            filterChain.doFilter(..) -> Not my job, move ahead to next chain
            filterChain.doFilter(request, response);
            return;
        }

/* If there is authorization header, extract jwt from it -> extract user's email -> load user details -> validate user
create authentication token -> set authentication to Security context holder for further task in current chain
* */
        String jwt = authHeader.substring(7);

        String userEmail = jwtService.extractUserName(jwt);

        if(userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                                userDetails,
                                                                null,
                                                                userDetails.getAuthorities()
                                                                );

//              adds extra request metadata to that authentication
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //filterChain.doFilter(..) -> I'm done move ahead to next chain
        filterChain.doFilter(request, response);

    }
}
