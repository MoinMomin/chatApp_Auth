package com.chatApp.authentication.config;

import com.chatApp.authentication.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Slf4j
public class JwtFilterForReq extends OncePerRequestFilter {
    @Autowired
    UserDetailedService userDetailedService;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
  log.error("----------------------------dofilter--------------------------------------");
        String requestTokenHeader=request.getHeader("Authorization");
  String username=null;
  String jwttoken;
  if (requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer")){
      jwttoken=requestTokenHeader.substring(7);
      try {
          username = jwtUtil.extractUsername(jwttoken);
      }catch(Exception e){
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED,"Invalid or expired token");
          response.getWriter().write("Invalid or expired token");
          return;
      }
      UserDetails userDetails=userDetailedService.loadUserByUsername(username);
      if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
          usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }else  {
          // Handle invalid or expired token
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Invalid or expired token");
          return;
      }
      //filterChain.doFilter(request,response);
  }else{
      // Handle null  token
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(" Token not available");
      return;
  }
        filterChain.doFilter(request,response);
    }

       /* @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            return new AntPathMatcher().match("/login", request.getServletPath());
        }*/
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] publicApiPaths = { "/user/registeruser","/user/login","/ws-chat"};

        // Use AntPathMatcher to check if the request path matches any of the public API paths
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String path : publicApiPaths) {
            if (pathMatcher.match(path, request.getServletPath())) {
                return true; // Do not filter for public APIs
            }
        }
        return false; // Filter all other requests
    }


}
