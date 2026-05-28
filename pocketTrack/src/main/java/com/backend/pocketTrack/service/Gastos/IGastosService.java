package com.backend.pocketTrack.service.Gastos;

import com.backend.pocketTrack.dto.gastos.GastoDTO;
import com.backend.pocketTrack.entity.Gastos;

import java.util.List;

public interface IGastosService {
    GastoDTO crearGastos(String nombre, Double cantidad, Long cuentaId);
    void deleteById(Long id);
    List<GastoDTO> obtenerGastosPorCuenta(Long cuentaId);
}
