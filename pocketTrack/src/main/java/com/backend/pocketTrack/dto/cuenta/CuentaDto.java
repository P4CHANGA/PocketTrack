package com.backend.pocketTrack.dto.cuenta;

import com.backend.pocketTrack.dto.gastos.GastoDetalleDTO;
import com.backend.pocketTrack.enums.Moneda;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link com.backend.pocketTrack.entity.Cuenta}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CuentaDto implements Serializable {
    private long id;
    private Moneda divisa;
    private Double cantidad;
    private String nombre;
    private Long usuarioId;
    private Set<GastoDetalleDTO> gastos = new LinkedHashSet<>();
}