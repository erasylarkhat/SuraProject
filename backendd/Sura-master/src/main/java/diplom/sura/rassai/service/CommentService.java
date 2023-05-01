package diplom.sura.rassai.service;

import diplom.sura.rassai.models.Comment;
import diplom.sura.rassai.models.Post;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {

    Comment getOneComment(Long id);
    List<Comment> getAllComment();
    Comment addComment(Comment comment, Post post, HttpServletRequest request);
    void deleteComment(Long id);

}
