package diplom.sura.rassai.api;

import diplom.sura.rassai.models.Comment;
import diplom.sura.rassai.models.Like;
import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.repository.PostRepository;
import diplom.sura.rassai.service.CommentService;
import diplom.sura.rassai.service.FileUploadService;
import diplom.sura.rassai.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final FileUploadService fileUploadService;

    @GetMapping(value = "/user/")
    public List<Post> getUserPosts(HttpServletRequest request){
        return postService.getUserPosts(request);
    }

    @GetMapping(value = "")
    public List<Post> getAllPosts(@RequestParam(required = false) String searchValue,
                                  @RequestParam(required = false) String category) {
        return postService.getAllPosts(searchValue, category);
        
    }

    @GetMapping(value = "/{id}")
    public Post getOnePost(@PathVariable(name = "id") Long id) {
        return postService.getOnePost(id);
    }


    @PostMapping(value = "")
    public Post addPost(@ModelAttribute Post post, HttpServletRequest request, @RequestParam(value = "files", required = false) MultipartFile[] files) {
        if(files != null && files.length > 0){
            System.out.println(files.length);
            return fileUploadService.uploadImage(files, post, request);
        }
        return postService.addNewPost(post, request);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
    }

    @PostMapping(value = "/{id}/comment")
    public Post createComment(@RequestBody Comment comment, @PathVariable(name = "id") Long id, HttpServletRequest request) {
        Post post = postService.getOnePost(id);
        return commentService.addComment(comment, post, request).getPost();
    }

    @PostMapping(value = "/{id}/like")
    public Post likePost(@PathVariable(name = "id") Long id, HttpServletRequest request){
        return postService.likePost(id, request);
    }

    @PostMapping(value = "/{id}/dislike")
    public Post dislikePost(@PathVariable(name = "id") Long id, HttpServletRequest request){
        return postService.dislikePost(id, request);
    }
}
