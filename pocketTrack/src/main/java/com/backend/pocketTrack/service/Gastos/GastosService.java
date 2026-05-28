package com.backend.pocketTrack.service.Gastos;

import com.backend.pocketTrack.dto.gastos.GastoDTO;
import com.backend.pocketTrack.entity.Gastos;
import com.backend.pocketTrack.exceptions.CreateEntityException;
import com.backend.pocketTrack.exceptions.ErrorGenericoException;
import com.backend.pocketTrack.mappers.GastosMapper;
import com.backend.pocketTrack.repository.ICuentaRepository;
import com.backend.pocketTrack.repository.IGastosRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GastosService implements IGastosService {
    private final ICuentaRepository cuentaRepository;
    private final IGastosRepository gastosRepository;
    private final GastosMapper gastosMapper;
    private final Logger logger = LoggerFactory.getLogger(GastosService.class);

    @Override
    public GastoDTO crearGastos(String nombre, Double cantidad, Long cuentaId) {

        Gastos gasto = null;
        try {

            try {

                gasto = new Gastos();

                gasto.setNombre(nombre);
                gasto.setCantidad(cantidad);
                gasto.setCuenta(cuentaRepository.findById(cuentaId).orElseThrow(() -> new RuntimeException("No existe la cuenta con el id: " + cuentaId)));

                gastosRepository.save(gasto);

            }catch (Exception e){
                throw new CreateEntityException(Gastos.class.getSimpleName(),gasto,e.getCause());
            }


        } catch (Exception e) {
            logger.error("Se ha producido un error en el método crearGastos de GastosService");
            throw new ErrorGenericoException(e.getMessage(),e);
        }
        return gastosMapper.toDTO(gasto);
    }

    @Override
    public void deleteById(Long id) {
        gastosRepository.deleteById(id);
    }

    @Override
    public List<GastoDTO> obtenerGastosPorCuenta(Long cuentaId) {

        List<Gastos> gastos = gastosRepository.findByCuentaId(cuentaId);

        return gastosMapper.toDTOList(gastos);
    }

}
