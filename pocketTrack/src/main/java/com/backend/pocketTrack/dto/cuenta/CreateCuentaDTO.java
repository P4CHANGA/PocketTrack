package com.backend.pocketTrack.dto.cuenta;

import com.backend.pocketTrack.dto.gastos.GastoDetalleDTO;
import com.backend.pocketTrack.enums.Moneda;
import com.backend.pocketTrack.mappers.CuentaMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCuentaDTO implements Serializable {
    private Long id;
    private Moneda divisa;
    private Double cantidad;
    private String nombre;
    private Long usuarioId;
    private Set<GastoDetalleDTO> gastos = new LinkedHashSet<>();
}
