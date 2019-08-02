package com.tenhawks.auth;

import com.datastax.driver.core.utils.UUIDs;
import com.tenhawks.auth.domain.*;
import com.tenhawks.auth.repository.ClientRepository;
import com.tenhawks.auth.repository.RoleRepository;
import com.tenhawks.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.util.StringUtils;

import java.net.Inet4Address;
import java.util.Arrays;
import java.util.UUID;


/**
 * @author Mukhtiar
 */
@SpringBootApplication
@EnableGlobalMethodSecurity (prePostEnabled = true)
@Configuration
@EnableCassandraRepositories(basePackages = "com.tenhawks.auth")

public class AuthApplication {

	private static final Logger log = LoggerFactory.getLogger(AuthApplication.class);

	public static void main(String[] args)  throws Exception {
		String cassandraHost =  System.getProperty("CASSANDRA_HOST");
		log.info("CASSANDRA_HOST :" + cassandraHost + ":");
		if(StringUtils.isEmpty(cassandraHost)) {
			System.setProperty("CASSANDRA_HOST", "0.0.0.0");
		} else {
			String ipAddress = Inet4Address.getByName(cassandraHost).getHostAddress();
			//log.info("ipAddress :" + ipAddress + ":");
			System.setProperty("CASSANDRA_HOST", ipAddress);
		}

		SpringApplication.run(AuthApplication.class, args);
	}

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CassandraTemplate cassandraTemplate;


	@Bean
	public AuthenticationKeyGenerator getAuthenticationKeyGenerator() {
		return new DefaultAuthenticationKeyGenerator();
	}


	@Bean
	InitializingBean sendDatabase() {
		Client client = new Client();
		client.setAccessTokenValiditySeconds(2592000);
		client.setRefreshTokenValiditySeconds(2592000 * 30);
		client.setClientId(UUID.fromString("cffe3990-6f0e-11e8-b750-4d8614c940ff"));
		client.setClientSecret("$2a$12$.5qlSA.5Gjp9.TRlEflnXukLlYUz/eNRhLgFKubk6PIoL8GM7GzLu");
		client.getScope().add("trust");
		client.getAuthorizedGrantTypes().add("password");
		client.getAuthorizedGrantTypes().add("refresh_token");

		return () -> {
			cassandraTemplate.truncate(Client.class);
			cassandraTemplate.truncate(Role.class);
			cassandraTemplate.truncate(User.class);
			cassandraTemplate.truncate(AccessToken.class);
			cassandraTemplate.truncate(Approval.class);
			cassandraTemplate.truncate(ClientToken.class);
			cassandraTemplate.truncate(RefreshToken.class);
			clientRepository.save(client);
			Client client1 = clientRepository.findByClientId(UUID.fromString("cffe3990-6f0e-11e8-b750-4d8614c940ff"));

			Role role = new Role();
			role.setId(UUIDs.timeBased());
			role.setRoleName("ROLE_USER");
		    roleRepository.save(role);

			role = new Role();
			role.setId(UUIDs.timeBased());
			role.setRoleName("ROLE_ADMIN");
		    roleRepository.save(role);

			role = new Role();
			role.setId(UUIDs.timeBased());
			role.setRoleName("ROLE_SUPER_ADMIN");
		    roleRepository.save(role);

			User user = new User();
			user.setUserId(UUIDs.timeBased());
			user.setRoles(Arrays.asList(role.getRoleName()));
			user.setEmailAddress("mukhtiar.ahmed@gmail.com");
			user.setActive(true);
			user.setFullName("Mukhtiar Ahmed");
			user.setUserName("ahmed");
			user.setPhoneNumber("+923100000000");
			// password is  `secert`
			user.setPassword("$2a$12$2kV1gT4c3XM.Gl1jHPoLYO/V7Pg.A1KnlUlZBrf5rnDXjYNLgc6N6");
			userRepository.save(user);
			user = userRepository.findByUserName("ahmed");
			user.getRoles();
		};
	}

}
