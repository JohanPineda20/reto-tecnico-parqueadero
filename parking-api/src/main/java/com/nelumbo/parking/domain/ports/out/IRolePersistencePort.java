package com.nelumbo.parking.domain.ports.out;

import com.nelumbo.parking.domain.model.RoleModel;

public interface IRolePersistencePort {
    RoleModel findById(Long id);
}
