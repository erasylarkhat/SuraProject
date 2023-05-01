package diplom.sura.rassai.service;

import diplom.sura.rassai.models.Answer;
import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.User;

public interface DislikeService {
    Post dislikePost(Post post, User user);
    Answer dislikeAnswer(Answer answer, User user);
}
