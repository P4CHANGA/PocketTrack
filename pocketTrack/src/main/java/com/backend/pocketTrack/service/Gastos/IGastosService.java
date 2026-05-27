package com.backend.pocketTrack.service.Gastos;

import com.backend.pocketTrack.dto.gastos.CreateGastoDTO;
import com.backend.pocketTrack.dto.gastos.GastoDTO;
import com.backend.pocketTrack.enums.TipoGasto;

import java.util.List;

public interface IGastosService {
    GastoDTO crearGastos(String nombre, Double cantidad, TipoGasto tipoGasto, Long cuentaId);
    void deleteById(Long id);
    List<GastoDTO> obtenerGastosPorCenta(Long cuentaId);
}
