package com.backend.pocketTrack.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO implements Serializable {

    public String username;
    public String password;
    public String email;
    public List<Integer> roles;


}
