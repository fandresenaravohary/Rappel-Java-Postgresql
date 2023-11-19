package library_management.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    public int id;
    public String userName;
    public String password;
}
