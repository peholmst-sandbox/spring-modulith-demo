package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@SpringBootApplication
@Theme("default")
@EnableScheduling
@EnableAsync
public class Application implements AppShellConfigurator {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone(); // You can also use Clock.systemUTC()
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
