package com.tenhawks.auth.repository;


import com.tenhawks.auth.domain.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Mukhtiar Ahmed
 */
@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {

    @Query("select * from user where userName =?0 ALLOW FILTERING")
    User findByUserName(String userName);

    @Query("select * from user where emailAddress =?0 ALLOW FILTERING")
    User findByEmailAddress(String email);
}
