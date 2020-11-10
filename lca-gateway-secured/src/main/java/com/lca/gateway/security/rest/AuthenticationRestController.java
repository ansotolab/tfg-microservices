package com.lca.gateway.security.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lca.gateway.security.repository.AuthorityRepository;
import com.lca.gateway.security.repository.UserRepository;
import com.lca.gateway.security.rest.dto.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.lca.gateway.security.model.Authority;
import com.lca.gateway.security.model.User;
import com.lca.gateway.security.rest.dto.LoginDto;
import com.lca.gateway.security.jwt.JWTFilter;
import com.lca.gateway.security.jwt.TokenProvider;
import com.lca.gateway.security.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

   private final TokenProvider tokenProvider;

   private final AuthenticationManagerBuilder authenticationManagerBuilder;

   private final UserService userService;

   private final AuthorityRepository authorityRepository;

   private final UserRepository userRepository;

   public AuthenticationRestController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService, AuthorityRepository authorityRepository, UserRepository userRepository) {
      this.tokenProvider = tokenProvider;
      this.authenticationManagerBuilder = authenticationManagerBuilder;
      this.userService = userService;
      this.authorityRepository = authorityRepository;
      this.userRepository = userRepository;
   }

   @PostMapping("/login")
   public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDto loginDto) {

      UsernamePasswordAuthenticationToken authenticationToken =
         new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();
      String jwt = tokenProvider.createToken(authentication, rememberMe);

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
   }

   @PostMapping("/register")
   public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
      userService.registerUser(userDTO);
      return new ResponseEntity<>("The user has been created.", HttpStatus.OK);
   }

   @PostMapping("/update")
   public ResponseEntity<String> update(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws Exception {

      if (bindingResult.hasErrors()) {
         if (bindingResult.getErrorCount() > 1) {
            if (((FieldError) bindingResult.getAllErrors().get(0)).getField() != "password") {
               throw new BindException(bindingResult);
            }
         }
      }
      userService.updateUser(userDTO);
      return new ResponseEntity<>("The user has been updated.", HttpStatus.OK);
   }

   @GetMapping("/authorities")
   public ResponseEntity<List<Authority>> listAuthorities() {
      List<Authority> list = authorityRepository.findAll();
      return new ResponseEntity<>(list, HttpStatus.OK);
   }

   @GetMapping("/users")
   public ResponseEntity<List<UserDTO>> listUsers() {
      List<UserDTO> list = new ArrayList<>();
      userRepository.findAll().forEach((user) -> {
         UserDTO newUser = new UserDTO();
         newUser.setByUser(user);
         list.add(newUser);
      });
      return new ResponseEntity<>(list, HttpStatus.OK);
   }

   @GetMapping("/users/{id}")
   public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
      Optional<User> user = userRepository.findById(id);
      if (!user.isPresent()) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found.");
      }
      UserDTO userDTO = new UserDTO();
      userDTO.setByUser(user.get());
      return new ResponseEntity<>(userDTO, HttpStatus.OK);
   }

   /**
    * Object to return as body in JWT Authentication.
    */
   static class JWTToken {

      private String idToken;

      JWTToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("id_token")
      String getIdToken() {
         return idToken;
      }

      void setIdToken(String idToken) {
         this.idToken = idToken;
      }
   }
}
