package io.github.dayanearnaud.manager_service_javer_bank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Manager Service Javer Bank",
				description = "Serviço backend do Javer Bank, responsável por gerenciar a lógica de negócios e o acesso ao banco de dados. Oferece CRUD para 'customer', calcula score de crédito e fornece APIRest para integração com o Client Service Javer Bank.",
				version = "1"
		)
)
public class ManagerServiceJaverBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerServiceJaverBankApplication.class, args);
	}

}
