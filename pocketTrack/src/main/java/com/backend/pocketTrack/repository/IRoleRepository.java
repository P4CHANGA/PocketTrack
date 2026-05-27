package com.backend.pocketTrack.repository;

import com.backend.pocketTrack.entity.Role;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Hidden
@Repository
public interface IRoleRepository extends CrudRepository<Role, Long> {
    @Query(value = "SELECT * FROM role WHERE id IN :rolenames", nativeQuery = true)
    List<Role> findByNombreIn(@Param("rolenames") List<Integer> rolenames);
}
