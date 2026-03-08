package com.back;

import org.springframework.stereotype.Component;

/*
- Configuration 클래스 + Bean 메서드를 이용하면 빈 등록을 할 수 있다.
- 그런데 Bean은 메서드에서 만든 객체를 반환하여 빈으로 등록 시키는데, 대부분 복잡한 로직 없이 객체 하나 등록하기 위해 메서드를 만들고 반환하는데 이게 너무 비효율적이다.
- 따라서 클래스 레벨에 Component를 붙여서 복잡한 과정 없이 바로 빈으로 등록 시켜버리자
 */

@Component
public class PersonService {

    private int version = 1;

    public PersonService(int version) {
        this.version = version;
    }

    public int count() {
        System.out.println("v%d 버전의 count() 호출".formatted(version));
        return 3;
    }
}
