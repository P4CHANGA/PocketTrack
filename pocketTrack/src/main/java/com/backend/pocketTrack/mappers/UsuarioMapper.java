package com.backend.pocketTrack.mappers;

import com.backend.pocketTrack.dto.usuario.CrearUsuarioDTO;
import com.backend.pocketTrack.dto.usuario.LoginUsuarioDTO;
import com.backend.pocketTrack.dto.usuario.UsuarioDTO;
import com.backend.pocketTrack.entity.Usuario;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UsuarioMapper {
    @Mapping(target = "roles", ignore = true)
    Usuario toUsuarioFromCreateUsuarioDTO(CrearUsuarioDTO usuario);

    Usuario toUsuarioFromLoginDTO(LoginUsuarioDTO usuario);
}
