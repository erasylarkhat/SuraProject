package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.models.*;
import diplom.sura.rassai.repository.LikeRepository;
import diplom.sura.rassai.repository.PostRepository;
import diplom.sura.rassai.repository.UserRepository;
import diplom.sura.rassai.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    // private final PostRepository postRepository;
    // private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public Post likePost(Post post, User user) {
        Optional<Like> existingLike = likeRepository.findByPostAndUser(post, user);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            post.getLikes().remove(existingLike.get());
        }else{
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeRepository.save(like);
            post.getLikes().add(like);
        }
        return post;
    }

    @Override
    public Answer likeAnswer(Answer answer, User user) {
        Optional<Like> existingLike = likeRepository.findByAnswerAndUser(answer, user);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            answer.getLikes().remove(existingLike.get());
        } else {
            Like like = new Like();
            like.setAnswer(answer);
            like.setUser(user);
            likeRepository.save(like);
            answer.getLikes().add(like);
        }
        return answer;
    }
}
