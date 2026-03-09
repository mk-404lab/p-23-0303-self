package com.back.domain.wiseSaying;


import com.back.domain.wiseSaying.entity.WiseSaying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WiseSayingController {

    private int lastId = 5;
    private final List<WiseSaying> wiseSayings = new ArrayList<>() {{
        add(new WiseSaying(1, "삶이 있는 한 희망은 있다.", "키케로"));
        add(new WiseSaying(2, "하루에 3시간을 걸으면 7년 후에 지구를 한 바퀴 돌 수 있다.", "사무엘 존슨"));
        add(new WiseSaying(3, "언제나 현재에 집중할수 있다면 행복할 것이다.", "파울로 코엘료"));
        add(new WiseSaying(4, "신은 용기있는 자를 결코 버리지 않는다.", "켄러"));
        add(new WiseSaying(5, "피할 수 없으면 즐겨라.", "로버트 엘리엇"));
    }};

    @GetMapping("/write")
    @ResponseBody
    public String write(@RequestParam String content, @RequestParam String author) {
        if(content == null || content.trim().length() == 0) {
            throw new RuntimeException("명언을 입력해주세요.");
        }

        if(author == null || author.trim().length() == 0) {
            throw new RuntimeException("작가를 입력해주세요.");
        }

        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
        wiseSayings.add(wiseSaying);

        return "%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId());
    }

    @GetMapping("/list")
    @ResponseBody
    public String list() {

        String wiseSayingsList = wiseSayings.stream()
                .map(w -> "<li>%s / %s / %s</li>".formatted(w.getId(), w.getContent(), w.getAuthor()))
                .collect(Collectors.joining("\n"));

        return """
                <ul>
                %s
                </ul>
                """.formatted(wiseSayingsList);
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        Optional<WiseSaying> wiseSaying = wiseSayings.stream()
                .filter(w -> w.getId() == id)
                .findFirst();

        if(wiseSaying.isEmpty()) {
            throw new RuntimeException("%d번 명언은 존재하지 않습니다.".formatted(id));
        }

        wiseSayings.remove(wiseSaying.get());

        return "%d번 명언이 삭제되었습니다.".formatted(id);
    }
}