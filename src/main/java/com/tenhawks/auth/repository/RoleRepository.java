package com.tenhawks.auth.repository;


import com.tenhawks.auth.domain.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Mukhtiar
 */
@Repository
public interface RoleRepository extends CassandraRepository<Role, UUID> {

  /**
   *
   * @param roleName
   * @return
   */
  Role findByRoleName(String roleName);

}
