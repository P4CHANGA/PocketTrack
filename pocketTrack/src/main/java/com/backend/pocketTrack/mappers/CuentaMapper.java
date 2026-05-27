package com.backend.pocketTrack.mappers;

import com.backend.pocketTrack.dto.cuenta.CreateCuentaDTO;
import com.backend.pocketTrack.dto.cuenta.CuentaDetalleDTO;
import com.backend.pocketTrack.dto.cuenta.CuentaDto;
import com.backend.pocketTrack.entity.Cuenta;
import com.backend.pocketTrack.entity.Usuario;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UsuarioMapper.class})
public interface CuentaMapper {
    CuentaDto toDTO(Cuenta cuenta);
    Cuenta toEntity(CuentaDto cuentaDto);
    Cuenta toEntity(CreateCuentaDTO cuentaDto);

    List<CuentaDto> toDTOList(List<Cuenta> cuentas);
    List<CuentaDto> toEntityList(List<CuentaDto> cuentasDTO);

    CuentaDetalleDTO toDetalleDTO(Cuenta cuenta);
    List<CuentaDetalleDTO> toDetalleDTOList(List<Cuenta> cuentas);
}
