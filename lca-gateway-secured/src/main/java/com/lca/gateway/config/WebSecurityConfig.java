package com.lca.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import com.lca.gateway.security.JwtAccessDeniedHandler;
import com.lca.gateway.security.JwtAuthenticationEntryPoint;
import com.lca.gateway.security.jwt.JWTConfigurer;
import com.lca.gateway.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }

   // Configure BCrypt password encoder =====================================================================

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   // Configure paths and requests that should be ignored by Spring Security ================================

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")

         // allow anonymous resource requests
         .antMatchers(
            "/",
            "/login",
            "/api/images/**"
         );
   }

   // Configure security settings ===========================================================================

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
         // we don't need CSRF because our token is invulnerable
         .csrf().disable()

         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

         .exceptionHandling()
         .authenticationEntryPoint(authenticationErrorHandler)
         .accessDeniedHandler(jwtAccessDeniedHandler)

         // enable h2-console
         .and()
         .headers()
         .frameOptions()
         .sameOrigin()

         // create no session
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

         .and()
         .authorizeRequests()

         // LOGIN
         .antMatchers("/auth/login").permitAll()

         // GET CREDENTIALS
         .antMatchers("/auth/user").authenticated()

         // USERS
         .antMatchers(HttpMethod.POST,"/auth/register").hasAuthority("EDIT_USER")
         .antMatchers(HttpMethod.POST,"/auth/update").hasAuthority("EDIT_USER")
         .antMatchers("/auth/authorities").hasAuthority("EDIT_USER")
         .antMatchers("/auth/users").hasAuthority("EDIT_USER")
         .antMatchers("/auth/users/**").hasAuthority("EDIT_USER")

         // CUSTOMER MICROSERVICE
         .antMatchers(HttpMethod.GET, "/api/customers").hasAuthority("VIEW_CUSTOMER")
         .antMatchers(HttpMethod.GET, "/api/customers/**").hasAuthority("VIEW_CUSTOMER")
         .antMatchers(HttpMethod.POST, "/api/customers").hasAuthority("EDIT_CUSTOMER")
         .antMatchers(HttpMethod.PUT, "/api/customers/**").hasAuthority("EDIT_CUSTOMER")
         .antMatchers(HttpMethod.DELETE, "/api/customers/**").hasAuthority("EDIT_CUSTOMER")

         .antMatchers(HttpMethod.GET, "/api/details/**").hasAuthority("VIEW_CUSTOMER")
         .antMatchers(HttpMethod.PUT, "/api/details/**").hasAuthority("EDIT_CUSTOMER")

         .antMatchers(HttpMethod.POST, "/api/images").hasAuthority("EDIT_CUSTOMER")


         // THE REST OF REQUESTS
         .anyRequest().authenticated()

         .and()
         .apply(securityConfigurerAdapter());
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }
}
