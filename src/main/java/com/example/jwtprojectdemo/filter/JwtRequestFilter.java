package com.example.jwtprojectdemo.filter;

import com.example.jwtprojectdemo.service.JwtService;
import com.example.jwtprojectdemo.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            final var header = request.getHeader("Authorization");
            String jwtToken = null;
            String username = null;
            //Header က Bearer နဲ့ စ ရင် token ယူမယ်
            if (header != null && header.startsWith("Bearer ") ){
             jwtToken = header.substring(7);
                try {
                  username = jwtUtil.getUserNameFromToke(jwtToken);
                }catch (IllegalArgumentException e){
                    System.out.println("unable jwt token");
                }catch (ExpiredJwtException e){
                    System.out.println("Jwt Toke is Expired");
                }

            }
            else{
                System.out.println("Jwt token does not start with Bearer");
            }
                //autheication ma shi yin
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

                var userDetails = jwtService.loadUserByUsername(username);
                    //Expired phit nay lar token ka
                    if (jwtUtil.validationToken(jwtToken,userDetails)) {

                      var usernamepasswordauthenticationToken =
                              new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                      userDetails.getAuthorities());
                        usernamepasswordauthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamepasswordauthenticationToken);
                    }
            }
            filterChain.doFilter(request,response);

    }
}
