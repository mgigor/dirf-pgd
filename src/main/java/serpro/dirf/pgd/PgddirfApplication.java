package serpro.dirf.pgd;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import serpro.dirf.pgd.util.Utils;

@SpringBootApplication
public class PgddirfApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgddirfApplication.class, args);
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("/script_inicial.sql"));
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		return dataSourceInitializer;
	}

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void runOnSpringInit() throws Exception {

		carregarFontes();

		configurarLogger();

	}

	@SneakyThrows
	private void configurarLogger() {
		Utils.configurarLogger();
	}

	@SneakyThrows
	private void carregarFontes() {
		Dirf dirf = new Dirf();
		Method declaredMethod = dirf.getClass().getDeclaredMethod("carregarFontes");
		declaredMethod.setAccessible(true);
		declaredMethod.invoke(dirf);
	}

}
