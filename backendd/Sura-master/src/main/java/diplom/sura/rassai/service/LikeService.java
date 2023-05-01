package diplom.sura.rassai.service;


import diplom.sura.rassai.models.Answer;
import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.User;

public interface LikeService {

    Post likePost(Post post, User user);
    Answer likeAnswer(Answer answer, User user);
}
