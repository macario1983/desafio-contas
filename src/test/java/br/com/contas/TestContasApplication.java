package br.com.contas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContasApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
	}

	public static void main(String[] args) {
		SpringApplication.from(ContasApplication::main).with(TestContasApplication.class).run(args);
	}

//	@Bean
//	ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository) {
//
//		return args -> {
//
//			IntStream.range(0, 1_000).forEach(val -> {
//
//						Faker faker = new Faker();
//
//						Author author = getAuthor(faker);
//						authorRepository.save(author);
//					}
//			);
//		};
//	}
//
//	private static @NotNull Author getAuthor(Faker faker) {
//		Author author = new Author();
//		author.setName(faker.name().fullName());
//		return author;
//	}
//
//}

}
