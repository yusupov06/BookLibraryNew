package domain;

import lombok.*;

import java.util.UUID;

/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 05:09
 * @project: BookLibrary
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {

    private final String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;

    private Integer numOfRentedBooks = 0;

    private boolean active = true;

    public User(String firstName, String lastName, String username, String password, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
