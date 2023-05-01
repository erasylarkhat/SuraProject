package diplom.sura.rassai.dto;

import diplom.sura.rassai.models.Answer;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDto {
    private Long id;
    private String title;
    private List<Answer> answers;

    public QuestionDto(Long id, String title, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.answers = answers.stream()
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
