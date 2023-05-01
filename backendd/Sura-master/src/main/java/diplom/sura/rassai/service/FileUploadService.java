package diplom.sura.rassai.service;

import org.springframework.web.multipart.MultipartFile;

import diplom.sura.rassai.models.Post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface FileUploadService {

    Post uploadImage(MultipartFile[] files, Post post, HttpServletRequest request);
    // byte[] getImage(String token) throws IOException;

}
