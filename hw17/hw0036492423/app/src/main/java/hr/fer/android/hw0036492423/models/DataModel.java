package hr.fer.android.hw0036492423.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Class represents model which contains data for Json.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public class DataModel implements Serializable {

    /**
     * Avatar location.
     */
    @SerializedName("avatar_location")
    private String avatarLocation;

    /**
     * First name.
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * Last name.
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * Phone number.
     */
    @SerializedName("phone_no")
    private String phoneNo;

    /**
     * Email.
     */
    @SerializedName("email_sknf")
    private String emailSknf;

    /**
     * Spouse.
     */
    @SerializedName("spouse")
    private String spouse;

    /**
     * Age.
     */
    @SerializedName("age")
    private String age;

    /**
     * Getter for the avatar location.
     *
     * @return avatar location
     */
    public String getAvatarLocation() {
        return avatarLocation;
    }

    /**
     * Setter for the avatar location.
     *
     * @param avatarLocation avatar lovation
     */
    public void setAvatarLocation(String avatarLocation) {
        this.avatarLocation = avatarLocation;
    }

    /**
     * Getter for the first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the last name
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the phone number.
     *
     * @return phone number
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Setter for the phone number.
     *
     * @param phoneNo phone number
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Getter for the email.
     *
     * @return email
     */
    public String getEmailSknf() {
        return emailSknf;
    }

    /**
     * Setter for the email.
     *
     * @param emailSknf email
     */
    public void setEmailSknf(String emailSknf) {
        this.emailSknf = emailSknf;
    }

    /**
     * Getter for the spouse.
     *
     * @return spouse
     */
    public String getSpouse() {
        return spouse;
    }

    /**
     * Setter for the spouse.
     *
     * @param spouse spouse
     */
    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    /**
     * Getter for the age.
     *
     * @return age
     */
    public String getAge() {
        return age;
    }

    /**
     * Setter for the age.
     *
     * @param age age
     */
    public void setAge(String age) {
        this.age = age;
    }
}
