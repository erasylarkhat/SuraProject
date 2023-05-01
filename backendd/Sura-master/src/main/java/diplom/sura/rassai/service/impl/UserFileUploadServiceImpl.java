package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.models.User;
import diplom.sura.rassai.service.UserFileUploadService;
import diplom.sura.rassai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UserFileUploadServiceImpl implements UserFileUploadService {


    // @Value("${loadURL}")
    // private String myLoadURL;

    @Value("${uploadAvatarURL}")
    private String uploadURL;

    @Override
    public User uploadAvatar(MultipartFile file, User user) {
        try {
            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
                String fileName = DigestUtils.sha1Hex(user.getId() + " avatars") + ".png";
                byte bytes[] = file.getBytes();
                Path path = Paths.get(uploadURL + fileName);
                Files.write(path, bytes);

                user.setAvatar(fileName);
                // userService.saveUserData(user);
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // @Override
    // public byte[] getAvatar(String token) throws IOException {
    //     String picURL = myLoadURL + "user.png";
    //     if (token != null) {
    //         if (token.equals(((User) userService.getCurrentUser()).getAvatar()))
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
