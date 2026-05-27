package com.backend.pocketTrack.service.Authentication;

import com.backend.pocketTrack.entity.Usuario;

public interface IAuthenticationService {

    public Usuario signup(Usuario newUser);
    public String authenticate(Usuario user);
}
