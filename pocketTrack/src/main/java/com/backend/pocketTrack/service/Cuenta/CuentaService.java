package com.backend.pocketTrack.service.Cuenta;

import com.backend.pocketTrack.dto.cuenta.CreateCuentaDTO;
import com.backend.pocketTrack.dto.cuenta.CuentaDetalleDTO;
import com.backend.pocketTrack.dto.cuenta.CuentaDto;
import com.backend.pocketTrack.entity.Cuenta;
import com.backend.pocketTrack.entity.Usuario;
import com.backend.pocketTrack.enums.Moneda;
import com.backend.pocketTrack.exceptions.ErrorGenericoException;
import com.backend.pocketTrack.exceptions.NotFoundEntityException;
import com.backend.pocketTrack.mappers.CuentaMapper;
import com.backend.pocketTrack.repository.ICuentaRepository;
import com.backend.pocketTrack.repository.IGastosRepository;
import com.backend.pocketTrack.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CuentaService implements ICuentaService {

    private final IUsuarioRepository usuarioRepository;
    private final CuentaMapper cuentaMapper;
    private final ICuentaRepository cuentaRepository;
    private final IGastosRepository gastosRepository;

    @Override
    public void crearCuenta(CreateCuentaDTO createCuentaDTO) {
        Cuenta cuenta = cuentaMapper.toEntity(createCuentaDTO);
        Usuario usuario = usuarioRepository.findById(createCuentaDTO.getUsuarioId()).orElse(null);

        cuenta.setUsuario(usuario);

        cuentaRepository.save(cuenta);
    }

    @Override
    public CuentaDto crearCuenta2(Moneda moneda, Double cantidad, String nombre, Long usuarioId) {

        Cuenta cuenta = new Cuenta();

        cuenta.setDivisa(moneda);
        cuenta.setCantidad(cantidad);
        cuenta.setNombre(nombre);
        cuenta.setUsuario(usuarioRepository.findById(usuarioId).orElse(null));

        cuentaRepository.save(cuenta);

        return cuentaMapper.toDTO(cuenta);
    }

    @Override
    public void actualizarCantidadCuenta(Long id, Double cantidad) {

        try {
            double modificar = cuentaRepository.actualizarCantidadCuenta(id, cantidad);

            if (modificar == 0) {
                throw new EntityActionVetoException("Cuenta no encontrada", null);
            }

        }catch (Exception e){
            throw new ErrorGenericoException("Error actualizar cantidad en actualizarCantidadCuenta", e);
        }
    }

    @Transactional
    public void eliminarCuentasYGastos(Long usuarioId) {
        gastosRepository.eliminarporUsuarioId(usuarioId);
        cuentaRepository.eliminarPorUsuarioId(usuarioId);
    }

    @Override
    public Long findIdByNombre(String nombre) {
        Long id = cuentaRepository.findIdByNombre(nombre);

        if (id == null) {
            throw new NotFoundEntityException("Cuenta con nombre: " + nombre + " no encontrado");
        }
        return id;
    }

    @Override
    public List<CuentaDetalleDTO> findByUsuarioId(Long usuarioId) {
        List<Cuenta> cuentas = cuentaRepository.findByUsuarioId(usuarioId);

        return cuentaMapper.toDetalleDTOList(cuentas);
    }
}
