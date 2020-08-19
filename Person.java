package contacts;

import java.io.Serializable;
import java.util.Objects;

class Person extends Contact implements Serializable {
    private String name;
    private String surname;
    private String birthday;
    private String gender;

    Person(String name, String surname, String number, String birthday, String gender ) {
        this.name = name;
        this.surname = surname;
        this.birthday = Objects.requireNonNullElse(birthday, "[no data]");
        this.gender = Objects.requireNonNullElse(gender, "[no data]");
        super.setTimeCreated();
        super.setTimeModified();
        if(super.validateNumber(number))
            super.number = number;
        else
            System.out.println("Wrong number format!");
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getBirthday() { return birthday; }
    public String getGender() { return gender; }

    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() { return getName() + " " + getSurname(); }

    @Override
    public String toExtendedString(){
        return
                "Name: " + getName() + "\n" +
                        "Surname: " + getSurname() + "\n" +
                        "Birth date: " + getBirthday() + "\n" +
                        "Gender: " + getGender() + "\n" +
                        "Number: " + getNumber() + "\n" +
                        "Time created: " + getTimeCreated() + "\n" +
                        "Time last edit: " + getTimeModified();

    }
}