package diplom.sura.rassai.service.impl;

import diplom.sura.rassai.config.JwtUtils;
import diplom.sura.rassai.config.PasswordResetLinkGenerator;
import diplom.sura.rassai.dto.UserDto;
import diplom.sura.rassai.models.PasswordResetRequest;
import diplom.sura.rassai.models.Permission;
import diplom.sura.rassai.models.User;
import diplom.sura.rassai.repository.PasswordResetRequestRepository;
import diplom.sura.rassai.repository.PermissionRepository;
import diplom.sura.rassai.repository.UserRepository;
import diplom.sura.rassai.service.UserFileUploadService;
import diplom.sura.rassai.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetLinkGenerator passwordResetLinkGenerator;

    @Autowired
    private PasswordResetRequestRepository passwordResetRequestRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired 
    private UserFileUploadService userFileUploadService;

    /*@Override
    public boolean registerUser(UserDto userDto) {
        if (userDto.getPassword().equals(userDto.getReTypePassword())) {
            User checkUser = userRepository.findByEmail(userDto.getEmail());
            if (checkUser == null) {
                Permission permission = permissionRepository.findByName("ROLE_USER");
                User user = new User();
                user.setEmail(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setFullName(userDto.getName());
                List<Permission> permissions = new ArrayList<>();
                permissions.add(permission);
                user.setPermissions(permissions);
                User newUser = userRepository.save(user);
                return newUser.getId() != null;
            }
        }

        return false;
    }*/

    @Override
    public User updatePassword(String oldPassword, String newPassword, HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }

        return null;
    }

    @Override
    public User saveUserData(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setFirstname(user.getFirstname());
            newUser.setLastname(user.getLastname());
            return userRepository.save(newUser);
        }

        return null;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Permission> getRoles() {
        return permissionRepository.findAll();
    }

    @Override
    public User saveRole(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(UserDto userDto) {

            User user = userRepository.findByEmail(userDto.getEmail());
            if (user != null) {
                if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                    return user;
                }
            }

            return null;
    }

    @Override
    public ResponseEntity<String> reset(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            String resetLink = passwordResetLinkGenerator.generatePasswordResetLink(user.getEmail());

            PasswordResetRequest passwordResetRequest = new PasswordResetRequest(user.getEmail(), resetLink);
            passwordResetRequestRepository.save(passwordResetRequest);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("${spring.mail.username}");
            message.setTo(user.getEmail());
            message.setSubject("Password Reset Request");
            message.setText("Dear " + user.getFirstname() + ",\n\n" +
                "You recently requested a password reset for your account on Sura.kz. " +
                "If you did not make this request, please ignore this message. " +
                "Otherwise, click the following link to reset your password:\n\n" +
                resetLink + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "Sincerely,\n" +
                "Sura.kz Support Team");
            
            mailSender.send(message);

            return ResponseEntity.ok("Password reset email send");
        }
        return null;
    }

    @Override
    public User resetPass(UserDto userDto, String email, String token, String expires) {
        User user = userRepository.findByEmail(passwordResetLinkGenerator.decodeEmail(email));
        if(user == null){
            return null;
        }
        PasswordResetRequest passwordResetRequest =passwordResetRequestRepository.findByEmail(user.getEmail());

        if(passwordResetRequest == null){
            return null;
        }

        if (Instant.now().getEpochSecond() > Long.parseLong(expires)) {
            passwordResetRequestRepository.delete(passwordResetRequest);
            return null;
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        passwordResetRequestRepository.delete(passwordResetRequest);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDto userDto, HttpServletRequest request) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user == null){
            return null;
        }
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setBio(userDto.getBio());
        user.setCredential(userDto.getCredential());
        return userRepository.save(user);
    }

    @Override
    public User uploadAvatar(MultipartFile file, HttpServletRequest request){
        String token = jwtUtils.getTokenFromRequest(request);
        String username = jwtUtils.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username);
        user = userFileUploadService.uploadAvatar(file, user);
        if(user != null){
            return userRepository.save(user);
        }
        return null;
    }
}
