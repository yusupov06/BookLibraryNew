package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: Yusupov Muhammadqodir
 * @time: 05-06-22 04:32
 * @project: BookLibrary
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Book {

    private Integer id;
    private String name;
    private String author;
    private String genre;
    private Integer numOfPages;
    private String rentedTo;
    private String givenFrom;
    private String status;
    private int rentedDays = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-DD")
    private LocalDate rentedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-DD")
    private LocalDate deadline;



}
