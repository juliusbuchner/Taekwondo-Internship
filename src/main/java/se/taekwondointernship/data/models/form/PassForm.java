package se.taekwondointernship.data.models.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PassForm {

    /*

    private String firstName;
    private String lastName;
    private String parentPhoneNumber;
    private String parentName;
    private String className;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate date=LocalDate.now();
    private String age;

    PersonSmallForm person;


    public PassForm(String firstName, String lastName, String parentPhoneNumber, String parentName, String className, LocalDate date, String age, PersonSmallForm personSmallForm) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentPhoneNumber = parentPhoneNumber;
        this.parentName = parentName;
        this.className = className;
        this.date=LocalDate.now();
        this.age=age;
        this.person=personSmallForm;
    }

    public PassForm() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public PersonSmallForm getPerson() {
        return person;
    }

    public void setPerson(PersonSmallForm personSmallForm) {
        this.person = personSmallForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PassForm passForm = (PassForm) o;

        if (!Objects.equals(firstName, passForm.firstName)) return false;
        if (!Objects.equals(lastName, passForm.lastName)) return false;
        if (!Objects.equals(parentPhoneNumber, passForm.parentPhoneNumber))
            return false;
        if (!Objects.equals(parentName, passForm.parentName)) return false;
        if (!Objects.equals(className, passForm.className)) return false;
        if (!Objects.equals(date, passForm.date)) return false;
        return Objects.equals(age, passForm.age);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (parentPhoneNumber != null ? parentPhoneNumber.hashCode() : 0);
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PassForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", parentPhoneNumber='" + parentPhoneNumber + '\'' +
                ", parentName='" + parentName + '\'' +
                ", className='" + className + '\'' +
                ", date=" + date +
                ", age='" + age + '\'' +
                '}';
    } */

    private Integer passId;
    private Integer personId;
    private Integer createPassId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate date=LocalDate.now();
}
