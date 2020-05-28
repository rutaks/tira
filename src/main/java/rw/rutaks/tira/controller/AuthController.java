package rw.rutaks.tira.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.rutaks.tira.dto.auth.AuthRequestDTO;
import rw.rutaks.tira.dto.auth.AuthResponseDTO;
import rw.rutaks.tira.dto.auth.ForgotPasswordDTO;
import rw.rutaks.tira.dto.auth.RegisterRequestDTO;
import rw.rutaks.tira.dto.auth.RegisterResponseDTO;
import rw.rutaks.tira.dto.auth.ResetPasswordDTO;
import rw.rutaks.tira.exception.CustomAuthenticationException;
import rw.rutaks.tira.model.ApiResponse;
import rw.rutaks.tira.model.Auth;
import rw.rutaks.tira.model.Person;
import rw.rutaks.tira.service.AuthService;
import rw.rutaks.tira.util.ErrorUtil;
import rw.rutaks.tira.util.JwtUtil;

//TODO: CREATE TIRA PROJECT & ADD AUTH W/ FINANCIAL ENDPOINTS
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private AuthService userDetailsService;
  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult)
      throws Exception {
    try {
      ErrorUtil.checkForError(bindingResult);
      authManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authRequestDTO.getUsername(), authRequestDTO.getPassword()));
      final Auth userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUsername());
      final String jwt = jwtUtil.generateToken(userDetails);
      AuthResponseDTO dto = new AuthResponseDTO(jwt);
      ApiResponse response = new ApiResponse(HttpStatus.OK, "Login Successful", dto);
      return ResponseEntity.ok(response);
    } catch (BadCredentialsException e) {
      throw new CustomAuthenticationException("Incorrect username or password");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO,
      BindingResult bindingResult)
      throws Exception {
    ErrorUtil.checkForError(bindingResult);
    Person person = userDetailsService.register(registerRequestDTO);
    final Auth userDetails =
        userDetailsService.loadUserByUsername(registerRequestDTO.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
    final RegisterResponseDTO responseDTO = new RegisterResponseDTO(jwt, person);
    ApiResponse response = new ApiResponse(HttpStatus.OK, "Registration Successful", responseDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(HttpServletRequest request,
      @Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO,
      BindingResult bindingResult)
      throws IOException {
    ErrorUtil.checkForError(bindingResult);
    userDetailsService.sendForgotPasswordRequest(forgotPasswordDTO.getEmail(), request);
    ApiResponse response = new ApiResponse(HttpStatus.OK,
        "Email was successfully sent to " + forgotPasswordDTO.getEmail(), null);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<?> resetPassword(@PathVariable String token,
      @Valid @RequestBody ResetPasswordDTO resetPasswordDTO,
      BindingResult bindingResult) {
    ErrorUtil.checkForError(bindingResult);
    userDetailsService.resetPassword(token, resetPasswordDTO);
    ApiResponse response = new ApiResponse(HttpStatus.OK,
        "Password was reset successfully", null);
    return ResponseEntity.ok(response);
  }
}
