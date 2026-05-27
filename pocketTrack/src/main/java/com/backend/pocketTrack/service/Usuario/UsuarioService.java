package com.backend.pocketTrack.service.Usuario;

import com.backend.pocketTrack.entity.Cuenta;
import com.backend.pocketTrack.entity.Usuario;
import com.backend.pocketTrack.exceptions.NotFoundEntityException;
import com.backend.pocketTrack.repository.ICuentaRepository;
import com.backend.pocketTrack.repository.IGastosRepository;
import com.backend.pocketTrack.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository userRepository;
    private  final IGastosRepository gastosRepository;
    private final ICuentaRepository cuentaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundEntityException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }


}
