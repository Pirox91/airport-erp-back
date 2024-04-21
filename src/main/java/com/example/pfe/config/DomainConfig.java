package com.example.pfe.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.example.pfe")
@EnableJpaRepositories("com.example.pfe")
@EnableTransactionManagement
public class DomainConfig {
}
