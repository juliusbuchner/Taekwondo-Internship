package se.taekwondointernship.data.models.form;

import lombok.*;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

public class PassForm {

    private String firstName;
    private String lastName;
    private String parentPhoneNumber;
    private String parentName;
    private String className;

    public PassForm(String firstName, String lastName, String parentPhoneNumber, String parentName, String className) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentPhoneNumber = parentPhoneNumber;
        this.parentName = parentName;
        this.className = className;
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
        return Objects.equals(className, passForm.className);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (parentPhoneNumber != null ? parentPhoneNumber.hashCode() : 0);
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
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
                '}';
    }
}
