package library_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private Long id;
    private String authorName;
    private char sex;

    public Author(String johnDoe, char m) {

    }
}





