package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.config.JwtUtils;
import diplom.sura.rassai.models.Answer;
import diplom.sura.rassai.models.Question;
import diplom.sura.rassai.models.User;
import diplom.sura.rassai.repository.AnswerRepository;
import diplom.sura.rassai.repository.UserRepository;
import diplom.sura.rassai.service.AnswerService;
import diplom.sura.rassai.service.DislikeService;
import diplom.sura.rassai.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final LikeService likeService;
    private final DislikeService dislikeService;

    @Override
    public List<Answer> getAllAnswer() {
        return answerRepository.findAll();
    }

    @Override
    public Answer getOneAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow();
    }

    @Override
    public Answer addAnswer(Answer answer, Question question, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);

        answer.setQuestion(question);
        answer.setUser(user);
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public Answer likeAnswer(Long id, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);

        Answer answer = answerRepository.findById(id).orElseThrow();
        User user = userRepository.findByEmail(username);

        if (user == null) {
            return null;
        }
        answer = likeService.likeAnswer(answer, user);
        answer = answerRepository.save(answer);
        return answer;
    }

    @Override
    public Answer dislikeAnswer(Long id, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);

        Answer answer = answerRepository.findById(id).orElseThrow();
        User user = userRepository.findByEmail(username);

        if (user == null) {
            return null;
        }

        answer = dislikeService.dislikeAnswer(answer, user);
        answer = answerRepository.save(answer);
        return answer;
    }
}
