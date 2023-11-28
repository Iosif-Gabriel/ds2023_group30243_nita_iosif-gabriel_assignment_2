package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.userrole.UserRole;

import java.util.UUID;

public class UserDetailsDTO {

    private UUID id;
    private String username;
    private String password;
    private UserRole userRole;

    public UserDetailsDTO() {

    }

    public UserDetailsDTO(UUID id, String username, String password, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

}
