package com.backend.pocketTrack.repository;

import com.backend.pocketTrack.dto.gastos.GastoDTO;
import com.backend.pocketTrack.entity.Gastos;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Hidden
@Repository
public interface IGastosRepository extends CrudRepository<Gastos,Long> {

    @Modifying
    @Transactional
    @Query("delete from Gastos g " +
            "where g.cuenta.id in(" +
            "select c.id from Cuenta c where c.usuario.id = :usuarioId)")
    void eliminarporUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("select g from Gastos g where g.cuenta.id =: cuentaId")
    List<GastoDTO> findByCuentaId(@Param("cuentaId") Long cuentaId);
}
