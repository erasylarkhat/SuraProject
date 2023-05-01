package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.config.JwtUtils;
import diplom.sura.rassai.models.Like;
import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.User;
import diplom.sura.rassai.repository.LikeRepository;
import diplom.sura.rassai.repository.PostRepository;
import diplom.sura.rassai.repository.UserRepository;
import diplom.sura.rassai.service.DislikeService;
import diplom.sura.rassai.service.LikeService;
import diplom.sura.rassai.service.PostService;
import diplom.sura.rassai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final LikeService likeService;
    private final DislikeService dislikeService;

    @Override
    public List<Post> getAllPosts(String searchValue, String category) {
        List<Post> posts;
        if (searchValue != null && category != null) {
            posts = postRepository.findByTitleContainingIgnoreCaseAndCategory(searchValue, category);
        } else if (searchValue != null) {
            posts =  postRepository.findByTitleContainingIgnoreCase(searchValue);
        } else if (category != null) {
            posts =  postRepository.findByCategory(category);
        } else {
            posts =  postRepository.findAll();
        }
        Collections.sort(posts, Comparator.comparing(Post::getCreated_at).reversed());
        return posts;
    }

    @Override
    public List<Post> getUserPosts(HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        return postRepository.findByUser(user);
    }

    @Override
    public Post getOnePost(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Override
    public Post addNewPost(Post post, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        post.setUser(user);

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post likePost(Long id, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);

        Post post = postRepository.findById(id).orElse(null);
        // Post post = getOnePost(id);
        User user = userRepository.findByEmail(username);

        if(user == null)
            return null;

        post = likeService.likePost(post, user);

        post = postRepository.save(post);
        return post;
    }

    @Override
    public Post dislikePost(Long id, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);

        Post post = postRepository.findById(id).orElse(null);
        User user = userRepository.findByEmail(username);

        if(user == null)
            return null;

        post = dislikeService.dislikePost(post, user);

        post = postRepository.save(post);
        return post;
    }

    @Override
    public Post getCurrentPost() {
        return null;
    }

}
