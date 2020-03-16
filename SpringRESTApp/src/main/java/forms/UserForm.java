package java.forms;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructo
public class UserForm {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
