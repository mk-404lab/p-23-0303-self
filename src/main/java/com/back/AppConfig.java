package com.back;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public PersonService personServiceV2() {
        System.out.println("personService v2 빈이 생성되었습니다.");
        return new PersonService(2);
    }

    @Bean
    public PersonService personServiceV3() {
        System.out.println("personService v3 빈이 생성되었습니다.");
        return new PersonService(3);
    }
}
