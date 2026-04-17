package com.rcasani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
public class ApiMastercadServiceV1Application {

    public static void main(String[] args) {
        SpringApplication.run(ApiMastercadServiceV1Application.class, args);
    }

}
