package com.backend.pocketTrack.dto.gastos;

import com.backend.pocketTrack.enums.TipoGasto;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateGastoDTO implements Serializable {

    private Long id;
    private String nombre;
    private Double cantidad;
    private TipoGasto tipo;
    private Long cuentaId;
}
