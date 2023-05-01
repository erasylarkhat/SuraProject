package diplom.sura.rassai.api;

import diplom.sura.rassai.config.JwtUtils;
import diplom.sura.rassai.dto.UserDto;
import diplom.sura.rassai.models.User;
import diplom.sura.rassai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    @GetMapping(value = "/getAllUsers")
    public List<User> userList() {
        return userService.getUsers();
    }

    @GetMapping(value = "/getOneUser/{id}")
    public User getOneUser(@PathVariable(name = "id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            User newUser = userService.saveUserData(user);

            if (newUser != null) {
                String token = jwtUtils.generateToken(newUser.getUsername());
                return new ResponseEntity<Object>(new UserResponse(token, newUser), HttpStatus.OK);
            }

            return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.login(userDto);
            if (user != null) {
                String token = jwtUtils.generateToken(user.getUsername());
                return new ResponseEntity<Object>(new UserResponse(token, user), HttpStatus.OK);
            }
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/reset")
    public ResponseEntity<String> resetUser(@RequestBody UserDto userDto){
        try {
            return userService.reset(userDto);
            // return new ResponseEntity<Object>(user, HttpStatus.OK);     
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/reset-pass")
    public ResponseEntity<Object> resetPassByLink(@RequestBody UserDto userDto,
                                                    @RequestParam(required = true) String email,
                                                    @RequestParam(required = true) String token,
                                                    @RequestParam(required = true) String expires){
        try {
            User user = userService.resetPass(userDto, email, token, expires);
            if (user != null) {
                token = jwtUtils.generateToken(user.getUsername());
                return new ResponseEntity<Object>(new UserResponse(token, user), HttpStatus.OK);
            }
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);           
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    } 


    @PostMapping(value = "update-password")
    public ResponseEntity<Object> updatePassword(@RequestBody UserDto userDto, HttpServletRequest request){
        try {
            User user = userService.updatePassword(userDto.getOldPassword(), userDto.getNewPassword(), request);
            if (user != null) {
                String token = jwtUtils.generateToken(user.getUsername());
                return new ResponseEntity<Object>(new UserResponse(token, user), HttpStatus.OK);
            }
            return new ResponseEntity<>("Somwthing went wrong", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "update-info")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto, HttpServletRequest request){
        try {
            User user = userService.updateUser(userDto, request);
            if (user != null) {
                String token = jwtUtils.generateToken(user.getUsername());
                return new ResponseEntity<Object>(new UserResponse(token, user), HttpStatus.OK);
            }
            return new ResponseEntity<>("Somwthing went wrong", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/avatar")
    public ResponseEntity<Object> uploadAvatar(@RequestParam("avatar") MultipartFile file, HttpServletRequest request){
        try {
            User user = userService.uploadAvatar(file, request);
            if (user != null) {
                String token = jwtUtils.generateToken(user.getUsername());
                return new ResponseEntity<Object>(new UserResponse(token, user), HttpStatus.OK);
            }
            return new ResponseEntity<>("Somwthing went wrong", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
