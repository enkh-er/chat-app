package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Hereglegchiin medeelliig aguulah class
 * @author enkherdene
 */
public class User {
    private IntegerProperty pk_id;
    private StringProperty name;
    private StringProperty password;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty major;
    private StringProperty birthday;
    private StringProperty phone;

    public User() {
        this.pk_id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("") ;
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.major =new SimpleStringProperty( "");
        this.birthday =new SimpleStringProperty( "");
        this.phone = new SimpleStringProperty("");
    }

    public User(int pk_id, String name, String password, String firstName, String lastName, String major, String birthday, String phone) {
        this.pk_id = new SimpleIntegerProperty(pk_id);
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password) ;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.major =new SimpleStringProperty( major);
        this.birthday =new SimpleStringProperty( birthday);
        this.phone = new SimpleStringProperty(phone);
    }

    public int getPk_id() {
        return pk_id.get();
    }

    public IntegerProperty pk_idProperty() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id.set(pk_id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMajor() {
        return major.get();
    }

    public StringProperty majorProperty() {
        return major;
    }

    public void setMajor(String major) {
        this.major.set(major);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
