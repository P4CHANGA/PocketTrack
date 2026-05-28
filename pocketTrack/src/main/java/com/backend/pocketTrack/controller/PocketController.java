package com.backend.pocketTrack.controller;

import com.backend.pocketTrack.dto.cuenta.CuentaDetalleDTO;
import com.backend.pocketTrack.dto.cuenta.CuentaDto;
import com.backend.pocketTrack.dto.gastos.GastoDTO;
import com.backend.pocketTrack.entity.Gastos;
import com.backend.pocketTrack.enums.Moneda;
import com.backend.pocketTrack.repository.IGastosRepository;
import com.backend.pocketTrack.service.Cuenta.ICuentaService;
import com.backend.pocketTrack.service.Gastos.IGastosService;
import com.backend.pocketTrack.service.Usuario.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "PocketTrack Controlador", description = "Gestión de la app")
public class PocketController {
    private final Logger logger = LoggerFactory.getLogger(PocketController.class);
    private final ICuentaService cuentaService;
    private final IGastosService gastosService;
    private final IUsuarioService usuarioService;
    private final IGastosRepository igastosRepository;

    @Operation(summary = "Crear una Cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta creada",
            content = @Content(schema = @Schema(implementation = CuentaDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Error al crear la cuenta",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/pocket/crearCuenta/{moneda}/{cantidad}/{nombre}/{usuarioId}")
    public ResponseEntity<CuentaDto> crearCuenta(@PathVariable Moneda moneda,@PathVariable Double cantidad,@PathVariable String nombre, @PathVariable Long usuarioId){
        CuentaDto cuentaDto = cuentaService.crearCuenta2(moneda,cantidad,nombre,usuarioId);
        logger.info("Cuenta creada");
        return ResponseEntity.ok(cuentaDto);
    }

    @PostMapping("/pocket/crearGasto/{nombre}/{cantidad}/{tipoGasto}/{cuentaId}")
    public ResponseEntity<GastoDTO> crearGasto(@PathVariable String nombre, @PathVariable Double cantidad, @PathVariable Long cuentaId) {
        GastoDTO gastoDTO = null;
            gastoDTO = gastosService.crearGastos(nombre, cantidad, cuentaId);
            logger.info("Gasto creada");
            return ResponseEntity.ok(gastoDTO);
    }

    @PatchMapping("/pocket/cantidadCuenta/{cuentaId}/{cantidad}")
    public ResponseEntity<?> modificarCantidadCuenta(@PathVariable Long cuentaId,@PathVariable Double cantidad){
        cuentaService.actualizarCantidadCuenta(cuentaId, cantidad);
        return ResponseEntity.ok(cuentaId);
    }

    @Operation(summary = "Eliminar Cuentas y Gastos del Usuario")
    @DeleteMapping("/pocket/eliminarCuentasGastos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> eliminarCuentasGastos(@PathVariable Long usuarioId){
        Map<String,Object> respuesta = new HashMap<>();

        try{
            cuentaService.eliminarCuentasYGastos(usuarioId);
            respuesta.put("mensaje", "Cuentas y Gastos eliminados correctamente");
        }catch (Exception e){
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/pocket/eliminarGasto/{id}")
    public ResponseEntity<Map<String,Object>> eliminarGasto(@PathVariable Long id){
        Map<String,Object> respuesta = new HashMap<>();

        try {
            gastosService.deleteById(id);
            respuesta.put("mensaje", "Gasto eliminado correctamente");
        }catch (Exception e){
            respuesta.put("mensaje", "Error al eliminar el gasto con id " + id);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/pocket/obtenerGastos/{idCuenta}")
    public ResponseEntity<List<GastoDTO>> obtenerGastos(@PathVariable Long idCuenta){

        List<GastoDTO> gastos = gastosService.obtenerGastosPorCuenta(idCuenta);

        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/pocket/obtenerIdUsuarioPorUsername/{username}")
    public ResponseEntity<Long> obtenerIdUsuarioPorUsername(@PathVariable String username){
        Long idUsuario = usuarioService.findIdByUsername(username);
        logger.info("Usuario encontrado con id " + idUsuario);
        return ResponseEntity.ok(idUsuario);
    }

    @GetMapping("/pocket/obtenerIdCuentaPorNombre/{nombre}")
    public ResponseEntity<Long> obtenerIdCuentaPorNombre(@PathVariable String nombre){
        Long idCuenta = cuentaService.findIdByNombre(nombre);
        logger.info("Cuenta encontrada con id " + idCuenta);
        return ResponseEntity.ok(idCuenta);
    }

    @GetMapping("/pocket/obtenerCuentas/{idUsuario}")
    public ResponseEntity<List<CuentaDetalleDTO>> obtenerCuentas(@PathVariable Long idUsuario){
        List<CuentaDetalleDTO> cuentass = cuentaService.findByUsuarioId(idUsuario);

        return ResponseEntity.ok(cuentass);
    }
}

