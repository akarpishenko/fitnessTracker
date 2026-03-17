package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.user.AuthResponseDto;
import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserLoginDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.etities.Role;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.mappers.UserMapper;
import fitenessTrackerApp.repository.RoleRepo;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.security.JWTGenerator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final ApplicationContext context;
    private JWTGenerator jwtGenerator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
        return new User(user.getUsername(), user.getPassword(), mapRolesTOAuthorities(user.getRoles()));
    }


    public UserResponseDTO createUser(UserCreateDto userCreateDto) {
        if (userRepo.existsByUsername(userCreateDto.getUsername())) {
            throw new RuntimeException("Username is taken");
        }
        UserEntity user = userMapper.fromUserCreateDto(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        Role roles = roleRepo.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        return userMapper.toUserResponse(userRepo.save(user));
    }

    public AuthResponseDto login(UserLoginDto userLoginDto) {
        if (!userRepo.existsByUsername(userLoginDto.getUsername())) {
            throw new RuntimeException("Username is not exists");
        }
        AuthenticationManager authManager = context.getBean(AuthenticationManager.class);
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername()
                        , userLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new AuthResponseDto(token);
    }

    private Collection<GrantedAuthority> mapRolesTOAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
