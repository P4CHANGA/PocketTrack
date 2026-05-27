package com.backend.pocketTrack.controller;

import com.backend.pocketTrack.dto.usuario.CrearUsuarioDTO;
import com.backend.pocketTrack.dto.usuario.LoginUsuarioDTO;
import com.backend.pocketTrack.entity.Role;
import com.backend.pocketTrack.entity.Usuario;
import com.backend.pocketTrack.exceptions.Response;
import com.backend.pocketTrack.mappers.UsuarioMapper;
import com.backend.pocketTrack.repository.IRoleRepository;
import com.backend.pocketTrack.service.Authentication.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final IAuthenticationService authenticationService;
    private final UsuarioMapper mapper;
    private final IRoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody CrearUsuarioDTO registerUsuarioDto)
    {
        Usuario user = mapper.toUsuarioFromCreateUsuarioDTO(registerUsuarioDto);

        List<Role> roles = roleRepository.findByNombreIn(registerUsuarioDto.getRoles());

        user.setRoles(roles);

        authenticationService.signup(user);

        String message = "Usuario registrado correctamente";

        return new ResponseEntity<Response>(Response.ok(message), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginUsuarioDTO loginUserDto)
    {
        Usuario loginUser = mapper.toUsuarioFromLoginDTO(loginUserDto);

        String jwtToken = authenticationService.authenticate(loginUser);

        return ResponseEntity.ok(jwtToken);
    }
}
