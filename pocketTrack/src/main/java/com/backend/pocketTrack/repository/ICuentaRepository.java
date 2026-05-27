package com.backend.pocketTrack.repository;

import com.backend.pocketTrack.entity.Cuenta;
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
public interface ICuentaRepository extends CrudRepository<Cuenta,Long> {

    @Modifying
    @Transactional
    @Query("update Cuenta cue " +
            "set cue.cantidad = cue.cantidad + :mov " +
            "where cue.id = :cuentaId")
    Double actualizarCantidadCuenta(@Param("cuentaId") Long cuentaId, @Param("mov") Double mov);

    @Modifying
    @Transactional
    @Query("delete from Cuenta c " +
            "where c.usuario.id =:usuarioId")
    void eliminarPorUsuarioId(@Param("usuarioId") Long usuarioId);
}
