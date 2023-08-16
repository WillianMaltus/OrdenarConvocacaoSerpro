package br.com.lertxt;

import br.com.lertxt.services.LerTxtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LerTxtApplication implements CommandLineRunner {

    @Autowired
    private LerTxtService lerTxtService;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(LerTxtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        lerTxtService.processarListas();

        SpringApplication.exit(applicationContext, () -> 0);
    }
}
