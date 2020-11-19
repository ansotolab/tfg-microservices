package com.lca.gateway.security.service;

import com.lca.gateway.security.model.User;
import com.lca.gateway.security.repository.UserRepository;
import com.lca.gateway.security.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.lca.gateway.security.SecurityUtils;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   @Autowired
   private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

   public User registerUser(UserDTO userDTO) {

      if (!userRepository.findOneByUsername(userDTO.getUsername()).isPresent()) {
         User user = new User();
         user.setUsername(userDTO.getUsername());
         String password = userDTO.getPassword();
         String encodedPassword = passwordEncoder.encode(password);
         user.setPassword(encodedPassword);
         user.setEmail(userDTO.getEmail());
         user.setFirstname(userDTO.getFirstname());
         user.setLastname(userDTO.getLastname());
         user.setAuthorities(userDTO.getAuthorities());
         user.setActivated(true);
         userRepository.save(user);
         return user;
      } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been created.");
      }
   }

   public User updateUser(UserDTO userDTO) {
      Optional<User> userSearch = userRepository.findOneById(userDTO.getId());
      if (userSearch.isPresent()) {
         User user = userSearch.get();
         user.setUsername(userDTO.getUsername());

         if (!userDTO.getPassword().isEmpty()) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
         }

         user.setEmail(userDTO.getEmail());
         user.setFirstname(userDTO.getFirstname());
         user.setLastname(userDTO.getLastname());
         user.setAuthorities(userDTO.getAuthorities());
         user.setActivated(userDTO.isActivated());
         userRepository.save(user);
         return user;
      } else {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has not been updated.");
      }
   }
}
