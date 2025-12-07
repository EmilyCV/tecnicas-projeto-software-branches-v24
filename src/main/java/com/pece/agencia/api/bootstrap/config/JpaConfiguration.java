package com.pece.agencia.api.bootstrap.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.pece.agencia.api")
@EntityScan("com.pece.agencia.api")
public class JpaConfiguration {
}
