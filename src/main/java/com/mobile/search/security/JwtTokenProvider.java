package com.mobile.search.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JwtTokenProvider {

  @Autowired
  JwtProperties jwtProperties;

  private String secretKey;

  @Autowired
  private UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
  }

  public String generateAuthenticationToken(String username, List<String> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", roles);

    Date now = new Date();
    Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

    return Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setExpiration(validity)//
        .signWith(SignatureAlgorithm.HS256, secretKey)//
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

      if (claims.getBody().getExpiration().before(new Date())) {
        return false;
      }
      return true;
    } catch (SignatureException | MalformedJwtException e) {
      log.error("Unsupported JWT token trace: {}", e);
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token.");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token.");
    } catch (IllegalArgumentException e) {
      log.error("JWT token compact of handler are invalid.");
    } catch (JwtException e) {
      log.error("JWK exception.");
    }
    return false;
  }


  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(getAllClaimsFromToken(token));
  }

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().parseClaimsJws(token).getBody();
  }

  public long getExpirationDateFromToken(String token) {
    return (getClaimFromToken(token, Claims::getExpiration).getTime() - new Date().getTime())
        / 1000;
  }

}
