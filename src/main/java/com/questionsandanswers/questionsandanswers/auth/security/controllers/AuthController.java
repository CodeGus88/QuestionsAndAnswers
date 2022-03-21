package com.questionsandanswers.questionsandanswers.auth.security.controllers;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.questionsandanswers.questionsandanswers.controllers.MainClassController;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.Role;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.models.enums.ERole;
import com.questionsandanswers.questionsandanswers.repository.RoleRepository;
import com.questionsandanswers.questionsandanswers.auth.payload.request.LoginRequest;
import com.questionsandanswers.questionsandanswers.auth.payload.request.SignupRequest;
import com.questionsandanswers.questionsandanswers.auth.payload.response.MessageResponse;
import com.questionsandanswers.questionsandanswers.auth.payload.response.UserInfoResponse;
import com.questionsandanswers.questionsandanswers.auth.security.jwt.JwtUtils;
import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsImpl;
import com.questionsandanswers.questionsandanswers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController extends MainClassController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        Validation.generalValidations(result);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult result) {
        Validation.generalValidations(result);
        signUpRequest.getRole().clear();
        signUpRequest.getRole().add("user");
        userService.saveUser(buidUser(signUpRequest));
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> newUser(@Valid @RequestBody SignupRequest signupRequest) {
        userService.saveUser(buidUser(signupRequest));
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody SignupRequest signUpRequest, BindingResult result) {
        Validation.generalValidations(result);
        User user = buidUser(signUpRequest);
        user.setId(id);
        check(id);
        userService.updateUser(user, Validation.existRole(getUserDetailsImp(), ERole.ROLE_ADMIN));
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param signUpRequest
     * @return user
     */
    private User buidUser(SignupRequest signUpRequest){
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return user;
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param userId
     */
    private void check(long userId){
        Validation.validateAuthor(
                getUserDetailsImp(),
                userId
        );
    }
}
