package diplom.sura.rassai.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String avatar;
    private String credential;
    private String oldPassword;
    private String newPassword;
    private String bio;

}
