package diplom.sura.rassai.service;

import diplom.sura.rassai.dto.UserDto;
import diplom.sura.rassai.models.Permission;
import diplom.sura.rassai.models.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

//    boolean registerUser(UserDto userDto);
    User updatePassword(String oldPassword, String newPassword, HttpServletRequest request);
    UserDetails getCurrentUser();
    User saveUserData(User user);
    List<User> getUsers();
    User getUser(Long id);
    List<Permission> getRoles();
    User saveRole(User user);
    User login(UserDto userDto);
    ResponseEntity<String> reset(UserDto userDto);

    User resetPass(UserDto userDto, String email, String token, String expires);
    User updateUser(UserDto userDto, HttpServletRequest request);
    User uploadAvatar(MultipartFile file, HttpServletRequest request);
}
