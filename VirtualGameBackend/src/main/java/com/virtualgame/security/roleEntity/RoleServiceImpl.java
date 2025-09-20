package com.virtualgame.security.roleEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Set<RoleEntity> findRoleEntitiesByRoleEntityEnumIn(List<String> roleRequest) {
        return roleRepository.findRoleEntitiesByRoleEntityEnumIn(roleRequest).stream().collect(Collectors.toSet());
    }
    // TODO: Falta por hacer el crud y la gestion de los roles
}
