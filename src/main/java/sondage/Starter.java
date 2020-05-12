package sondage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sondage.entity.model.Pollster;
import sondage.entity.services.IPollsterDAO;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = IPollsterDAO.class)
@EntityScan(basePackageClasses = Pollster.class)
public class Starter extends SpringBootServletInitializer {

	@Bean("messageSource")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource r = new ResourceBundleMessageSource();
		r.setBasenames("/springapp/web/product");
		return r;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Starter.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}
}
