package app.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteInfoDTO {

    private String noteText;
    private String personName;
    private int personAge;
}
