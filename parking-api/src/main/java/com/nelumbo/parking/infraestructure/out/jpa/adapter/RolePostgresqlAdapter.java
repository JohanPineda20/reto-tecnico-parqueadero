package com.nelumbo.parking.infraestructure.out.jpa.adapter;

import com.nelumbo.parking.domain.model.RoleModel;
import com.nelumbo.parking.domain.ports.out.IRolePersistencePort;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.RoleEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RolePostgresqlAdapter implements IRolePersistencePort {
    private final RoleRepository rolRepository;
    private final RoleEntityMapper rolEntityMapper;
    @Override
    public RoleModel findById(Long id) {
        return rolEntityMapper.toRolModel(rolRepository.findById(id).orElse(null));
    }
}
