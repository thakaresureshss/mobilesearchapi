package com.mobile.search.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.search.model.User;
import com.mobile.search.repo.UserRepository;

@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user
        .getPassword(),
        getAuthorities(user));
  }

  public Collection<? extends GrantedAuthority> getAuthorities(User user) {
    return getGrantedAuthorities(
        user.getRoles().stream().map(role -> role.getCode()).collect(Collectors.toList()));
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }
}
