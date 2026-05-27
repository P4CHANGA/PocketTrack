package com.backend.pocketTrack.service.Role;

import com.backend.pocketTrack.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> obtenerRolesByNombre(List<Integer> nombre);
}
