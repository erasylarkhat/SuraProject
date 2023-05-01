package diplom.sura.rassai.service;

import diplom.sura.rassai.models.Post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface PostService {

    List<Post> getAllPosts(String searchValue, String category);
    List<Post> getUserPosts(HttpServletRequest request);
    Post getOnePost(Long id);
    Post addNewPost(Post post, HttpServletRequest request);
    void deletePost(Long id);

    Post likePost(Long id, HttpServletRequest request);
    Post dislikePost(Long id, HttpServletRequest request);

    Post getCurrentPost();

}
