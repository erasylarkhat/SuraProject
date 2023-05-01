package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.config.JwtUtils;
import diplom.sura.rassai.models.Answer;
import diplom.sura.rassai.models.Question;
import diplom.sura.rassai.models.User;
import diplom.sura.rassai.repository.AnswerRepository;
import diplom.sura.rassai.repository.QuestionRepository;
import diplom.sura.rassai.repository.UserRepository;
import diplom.sura.rassai.service.LikeService;
import diplom.sura.rassai.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    private final AnswerRepository answerRepository;


    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }
    @Override
    public List<Question> getUserQuestions(HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        return questionRepository.findByUser(user);
    }

    @Override
    public List<Question> getUserAnsweredQuestions(HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        List<Answer> answers = answerRepository.findByUser(user);
        
        Set<Question> questions = new HashSet<>(); 

        for (Answer answer : answers) {
            questions.add(answer.getQuestion());
        }
        List<Question> newList = new ArrayList<>(questions);

        return newList;
    }

    

    @Override
    public Question getOneQuestion(Long id) {
        Question question = questionRepository.findById(id).orElse(null);
        return question;
    }

    @Override
    public Question addNewQuestion(Question question, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        question.setUser(user);

        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

//    @Override
//    public Question likeQuestion(Long id, HttpServletRequest request) {
//        String token = jwtUtils.getTokenFromRequest(request);
//        String username = jwtUtils.getUsernameFromToken(token);
//
//        Question question = questionRepository.findById(id).orElse(null);
//        User user = userRepository.findByEmail(username);
//
//        if(user == null)
//            return null;
//
//        question = likeService.likeQuestion(question, user);
//
//        question = questionRepository.save(question);
//        return question;
//    }
}
