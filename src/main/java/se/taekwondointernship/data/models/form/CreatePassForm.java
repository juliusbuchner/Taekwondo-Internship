package se.taekwondointernship.data.models.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreatePassForm {
    private String className;
    @Nullable
    @JsonProperty("date")
    @JsonDeserialize(using= LocalDateDeserialization.class)
   // @JsonDeserialize(converter=DateConvertor.class)
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate date;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
   // @DateTimeFormat(style="HH:mm")
 //   @JsonDeserialize(using = TimeDeserializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime startTime;
    private int duration;
    private boolean isExtraPass;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getDate() {
        if(date == null){
            date=LocalDate.now();
        }
        return date;
    }

    public void setDate(LocalDate date) {
        if(date == null){
            date=LocalDate.now();
        }
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isExtraPass() {
        return isExtraPass;
    }

    public void setExtraPass(boolean extraPass) {
        isExtraPass = extraPass;
    }

}
