package com.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AppConfig {

//    @Bean
//    public PersonService personServiceV2() {
//        System.out.println("personService v2 빈이 생성되었습니다.");
//        return new PersonService(2);
//    }
//
//    @Bean
//    public PersonService personServiceV3() {
//        System.out.println("personService v3 빈이 생성되었습니다.");
//        return new PersonService(3);
//    }

    @Bean
    public int personVersion() {
        return 100;
    }


    /*
    - 앱 시작시 실행되는 ApplicationRunner는 여러 개 존재할 수 있다.
    - 이때 실행 순서를 보장하기 위해 @Order를 사용한다.
    - @Order()에 사용되는 숫자가 작을 수록 우선순위가 높다.(먼저 실행됨)
     */
    @Bean
    @Order(1)
    public ApplicationRunner myApplicationRunner1() {
        return args -> {
            System.out.println("MyApplicationRunner1 is runnig");
        };
   }

    @Bean
    @Order(2)
    public ApplicationRunner myApplicationRunner2() {
        return args -> {
            System.out.println("MyApplicationRunner2 is runnig");
        };
    }

    @Autowired
    @Lazy
    private AppConfig self;     // AppConfigProxy의 리모컨

    @Bean
    public ApplicationRunner myApplicationRunner3() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {

    }

    @Transactional
    public void work2() {
        System.out.println("work2 is runnig");
    }
}
