package com.backend.pocketTrack.service.Role;

import com.backend.pocketTrack.entity.Role;
import com.backend.pocketTrack.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    IRoleRepository roleRepository;

    @Override
    public List<Role> obtenerRolesByNombre(List<Integer> nombre) {
        return roleRepository.findByNombreIn(nombre);
    }
}
