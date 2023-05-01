package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.models.Post;
import diplom.sura.rassai.models.PostImage;
import diplom.sura.rassai.repository.PostImageRepository;
import diplom.sura.rassai.repository.PostRepository;
import diplom.sura.rassai.service.FileUploadService;
import diplom.sura.rassai.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final PostService postService;

    private final PostRepository postRepository;

    private final PostImageRepository postImageRepository;

    // @Value("${uploadAvatarURL}")
    // private String myLoadURL;

    @Value("${uploadPostURL}")
    private String uploadURL;

    @Override
    public Post uploadImage(MultipartFile[] files, Post post, HttpServletRequest request) {
        post = postService.addNewPost(post, request);
        try {
            for(MultipartFile file : files){
                if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
                    PostImage postImage = new PostImage();
                    long timestamp = System.currentTimeMillis();
                    String fileName = DigestUtils.sha1Hex(timestamp + "postimage") + ".png";
                    byte bytes[] = file.getBytes();
                    Path path = Paths.get(uploadURL + fileName);
                    Files.write(path, bytes);
                    postImage.setName(fileName);
                    postImage.setPost(post);
                    postImageRepository.save(postImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PostImage> images = postImageRepository.findByPost(post);

        post.setImages(images);
        return post;
    }

    // @Override
    // public byte[] getImage(String token) throws IOException {
    //     String picURL = myLoadURL + "user.png";
    //     if (token != null) {
    //         if (token.equals(((Post) postService.getCurrentPost()).getImage()));
    //             picURL = myLoadURL + token;
    //     }

    //     InputStream in;

    //     try {
    //         ClassPathResource resource = new ClassPathResource(picURL);
    //         in = resource.getInputStream();
    //     } catch (Exception e) {
    //         picURL = myLoadURL + "user.png";
    //         ClassPathResource resource = new ClassPathResource(picURL);
    //         in = resource.getInputStream();
    //     }

    //     return IOUtils.toByteArray(in);
    // }
}
