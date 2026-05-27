package com.backend.pocketTrack.service.Cuenta;

import com.backend.pocketTrack.dto.cuenta.CreateCuentaDTO;
import com.backend.pocketTrack.dto.cuenta.CuentaDto;
import com.backend.pocketTrack.entity.Cuenta;
import com.backend.pocketTrack.enums.Moneda;

import java.util.List;

public interface ICuentaService {
    void crearCuenta(CreateCuentaDTO createCuentaDTO);
    CuentaDto crearCuenta2(Moneda moneda, Double cantidad, String nombre, Long usuarioId);
    void actualizarCantidadCuenta(Long id, Double cantidad);
    void eliminarCuentasYGastos(Long usuarioId);
}
