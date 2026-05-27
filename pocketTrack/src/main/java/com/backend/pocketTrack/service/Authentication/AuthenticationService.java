package com.backend.pocketTrack.service.Authentication;

import com.backend.pocketTrack.config.security.JwtTokenProvider;
import com.backend.pocketTrack.entity.Usuario;
import com.backend.pocketTrack.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Usuario signup(Usuario newUser) {
        if(usuarioRepository.existsByUsername(newUser.getUsername()))
        {
            throw new IllegalArgumentException("Username is already in use");
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setCreationDate(LocalDateTime.now());

        return usuarioRepository.save(newUser);
    }

    @Override
    public String authenticate(Usuario user) {
        Authentication authResult = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authResult);

        return jwtTokenProvider.generateToken(authResult);
    }
}

