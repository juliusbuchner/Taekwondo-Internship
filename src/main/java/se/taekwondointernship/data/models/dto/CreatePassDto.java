package se.taekwondointernship.data.models.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class CreatePassDto {
    private String className;
    private LocalDate date;
    private LocalTime startTime;
    private int duration;
    private boolean isExtraPass;
}
