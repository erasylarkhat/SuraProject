package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.config.JwtUtils;
import diplom.sura.rassai.models.Comment;
import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.User;
import diplom.sura.rassai.repository.CommentRepository;
import diplom.sura.rassai.repository.UserRepository;
import diplom.sura.rassai.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    public Comment getOneComment(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment addComment(Comment comment, Post post, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);

        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
