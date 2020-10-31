package com.mobile.search.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.search.constants.ErrorConstants;
import com.mobile.search.domain.AuthTokenRequest;
import com.mobile.search.domain.AuthTokenResponse;
import com.mobile.search.exception.dto.MobileBusinessError;
import com.mobile.search.exception.security.MobileBusinessException;
import com.mobile.search.model.User;
import com.mobile.search.repo.UserRepository;
import com.mobile.search.security.JwtTokenProvider;

import io.swagger.annotations.Api;

/**
 * This is authentication provider class.
 * 
 * @author Suresh Thakare
 *
 */
@RestController
@RequestMapping("/mobile")
@Api(tags = {"Authentication API's"})
@CrossOrigin("*")
public class AuthController {
  @Autowired
  UserRepository userRepository;

  @Autowired
  JwtTokenProvider tokenProvider;

  @PostMapping("/auth")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthTokenRequest loginRequest) {

    Optional<User> userOptional = this.userRepository.findByUsername(loginRequest.getUsername());
    String token = null;
    if (!userOptional.isPresent()) {
      throw new MobileBusinessException(
          new MobileBusinessError(HttpStatus.BAD_REQUEST, ErrorConstants.USER_NOT_FOUND));
    } else {
      User user = userOptional.get();
      if (CollectionUtils.isEmpty(user.getRoles())) {
        throw new MobileBusinessException(
            new MobileBusinessError(HttpStatus.BAD_REQUEST, ErrorConstants.ROLES_NOT_FOUND));
      }
      List<String> roleList = user.getRoles().stream().map(role -> role.getCode())
          .collect(Collectors.toList());
      token = tokenProvider.generateAuthenticationToken(loginRequest.getUsername(), roleList);
    }
    return ResponseEntity.ok(new AuthTokenResponse(token));
  }

}
