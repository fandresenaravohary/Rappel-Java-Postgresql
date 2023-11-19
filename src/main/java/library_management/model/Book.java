package library_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String bookName;
    private int pageNumbers;
    private char sex;
    private library_management.Connection.model.Topic topic;
    private LocalDate releaseDate;
    private Long authorId;

    public Book(String s, int i, char m, library_management.Connection.model.Topic topic, LocalDate now, long l) {
    }
}
