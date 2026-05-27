package com.backend.pocketTrack.mappers;

import com.backend.pocketTrack.dto.gastos.CreateGastoDTO;
import com.backend.pocketTrack.dto.gastos.GastoDTO;
import com.backend.pocketTrack.dto.gastos.GastoDetalleDTO;
import com.backend.pocketTrack.entity.Gastos;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GastosMapper {
    GastoDTO toDTO(Gastos gasto);
    Gastos toEntity(GastoDTO gastoDTO);
    Gastos toEntity(CreateGastoDTO createGastoDTO);

    List<GastoDTO> toDTOList(List<Gastos> gastos);
    List<Gastos> toEntityList(List<GastoDTO> gastoDTO);

}
