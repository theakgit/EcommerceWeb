package com.ECommerceWeb.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
           if(jwt!=null){
               jwt=jwt.substring(7);
               try {
                  // SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                   final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                   Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                   String email = String.valueOf(claims.get("email"));

                   String  authorities = String.valueOf(claims.get("authorities"));

                   List<GrantedAuthority>auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                   Authentication authentication = new UsernamePasswordAuthenticationToken(email,null,auths);
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }catch (Exception exception){
                   throw new BadCredentialsException("invalid token...from jwt validator");
               }
           }
           filterChain.doFilter(request,response);
    }
}
