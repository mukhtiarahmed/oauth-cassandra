package com.tenhawks.auth.repository;

import com.tenhawks.auth.domain.ClientToken;
import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * Created by Mukhtiar on 5/23/2018.
 */
public interface ClientTokenRepository extends CassandraRepository<ClientToken> {
}
