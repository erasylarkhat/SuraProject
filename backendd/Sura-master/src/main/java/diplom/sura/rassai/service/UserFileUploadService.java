package diplom.sura.rassai.service;

import org.springframework.web.multipart.MultipartFile;

import diplom.sura.rassai.models.User;


public interface UserFileUploadService {

    User uploadAvatar(MultipartFile file, User user);
    // byte[] getAvatar(String token) throws IOException;

}
