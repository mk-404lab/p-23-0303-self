package com.back.domain.wiseSaying;


import com.back.domain.wiseSaying.entity.WiseSaying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
주소 체계는 내가 원하는대로 설정할 수 있다.
다만 한 가지 방식으로 정했으면 일관되게 사용할 것

Ex) /wiseSayings/{id}/delete
    /wiseSayings/modify/{id}
    이렇게 일관성 없이 쓰면 안됨
 */

@Controller
@RequestMapping("/wiseSayings")
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

    @GetMapping
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

    @GetMapping("/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable int id) {
        WiseSaying wiseSaying = findById(id);
        wiseSayings.remove(wiseSaying);

        return "%d번 명언이 삭제되었습니다.".formatted(id);
    }

    @GetMapping("/{id}/modify")
    @ResponseBody
    public String modify(
            @PathVariable int id,
            @RequestParam(defaultValue = "기본값") String content,
            @RequestParam(defaultValue = "기본값") String author
    ) {
        WiseSaying wiseSaying = findById(id);

        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);

        return "%d번 명언이 수정되었습니다.".formatted(id);
    }

    private WiseSaying findById(int id) {
        Optional<WiseSaying> wiseSaying = wiseSayings.stream()
                .filter(w -> w.getId() == id)
                .findFirst();

        if (wiseSaying.isEmpty()) {
            throw new RuntimeException("%d번 명언은 존재하지 않습니다.".formatted(id));
        }

        return wiseSaying.get();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String detail(@PathVariable int id) {
        WiseSaying wiseSaying = findById(id);

        return """
                <h1>번호 : %s</h1>
                <div>명언 : %s</div>
                <div>작가 : %s</div>
                """.formatted(wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());
    }
}