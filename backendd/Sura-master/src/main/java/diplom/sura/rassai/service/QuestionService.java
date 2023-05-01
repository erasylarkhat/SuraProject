package diplom.sura.rassai.service;

import diplom.sura.rassai.models.Question;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestion();
    List<Question> getUserQuestions(HttpServletRequest request);
    List<Question> getUserAnsweredQuestions(HttpServletRequest request);
    
    Question getOneQuestion(Long id);
    Question addNewQuestion(Question question, HttpServletRequest request);
    void deleteQuestion(Long id);

//    Question likeQuestion(Long id, HttpServletRequest request);

}
