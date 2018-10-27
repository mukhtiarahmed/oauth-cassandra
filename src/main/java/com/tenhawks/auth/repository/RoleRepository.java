package com.tenhawks.auth.repository;


import com.tenhawks.auth.domain.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mukhtiar
 */
@Repository
public interface RoleRepository extends CassandraRepository<Role> {

  /**
   *
   * @param roleName
   * @return
   */
  Role findByRoleName(String roleName);

}
