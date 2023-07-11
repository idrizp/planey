package dev.idriz.planey.model;

import dev.idriz.planey.PlaneyApplication;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "profiles",
        indexes = {
                @Index(
                        columnList = "email",
                        name = "email_index"
                )
        }
)
public class Profile {

    @Id
    @GeneratedValue
    private UUID profileId;

    private String firstName;
    private String lastName;

    private String email;

    private String passportNumber;
    private String nationality;

    private String password;
    private Role role;

    @OneToMany
    private List<Ticket> tickets;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     * @apiNote This method should not be used directly. Use {@link #updatePassword(String)} instead if you are passing a plain text password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void updatePassword(String password) {
        this.password = PlaneyApplication.PASSWORD_ENCODER.encode(password);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        ADMIN,
        USER
    }
}
