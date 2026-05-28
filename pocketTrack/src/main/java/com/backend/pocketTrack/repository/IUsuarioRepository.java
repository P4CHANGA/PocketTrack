package com.backend.pocketTrack.repository;

import com.backend.pocketTrack.entity.Usuario;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario,Long> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);


    @Query("select u.id from Usuario u where u.username =:nombre")
    Long findIdByUsername(@Param("nombre") String nombre);
}
