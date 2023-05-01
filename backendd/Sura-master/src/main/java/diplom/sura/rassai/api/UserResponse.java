package diplom.sura.rassai.api;

import diplom.sura.rassai.models.User;

public class UserResponse {
    private String token;
    private User user;

    
        public User getUser() {
            return user;
        }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
    
}
