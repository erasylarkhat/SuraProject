package diplom.sura.rassai.service.impl;

import java.util.Optional;

import diplom.sura.rassai.models.Answer;
import org.springframework.stereotype.Service;

import diplom.sura.rassai.models.Dislike;
import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.User;
import diplom.sura.rassai.repository.DislikeRepository;
import diplom.sura.rassai.service.DislikeService;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;

@Service
@RequiredArgsConstructor
public class DislikeServiceImpl implements DislikeService{

    private final DislikeRepository dislikeRepository;

    @Override
    public Post dislikePost(Post post, User user) {
        Optional<Dislike> existingDislike = dislikeRepository.findByPostAndUser(post, user);
        if (existingDislike.isPresent()) {
            dislikeRepository.delete(existingDislike.get());
            post.getDislikes().remove(existingDislike.get());
        }else{
            Dislike dislike = new Dislike();
            dislike.setPost(post);
            dislike.setUser(user);
            dislikeRepository.save(dislike);
            post.getDislikes().add(dislike);
        }
        return post;
    }

    @Override
    public Answer dislikeAnswer(Answer answer, User user) {
        Optional<Dislike> existingDislike = dislikeRepository.findByAnswerAndUser(answer, user);
        if (existingDislike.isPresent()) {
            dislikeRepository.delete(existingDislike.get());
            answer.getDislikes().remove(existingDislike.get());
        } else {
            Dislike dislike = new Dislike();
            dislike.setAnswer(answer);
            dislike.setUser(user);
            dislikeRepository.save(dislike);
            answer.getDislikes().add(dislike);
        }
        return answer;
    }

}
