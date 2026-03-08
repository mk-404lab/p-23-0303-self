package com.back;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonController {

    private final PersonService personService;

    public PersonController(@Qualifier("personServiceV3") PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/people")
    @ResponseBody
    public String people() {
        return "사람 수 : %d".formatted(personService.count());
    }
}
