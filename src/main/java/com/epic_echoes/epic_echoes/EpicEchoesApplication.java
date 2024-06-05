package com.epic_echoes.epic_echoes;

import com.epic_echoes.epic_echoes.helpers.RefreshableCRUDRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableCRUDRepositoryImpl.class)
public class EpicEchoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpicEchoesApplication.class, args);
	}

}
