package com.backend.pocketTrack.dto.usuario;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


public class UsuarioDTO {
    private long id;
    private String username;
    private String password;
    private String email;
}
