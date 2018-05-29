package com.klaudi73.blog.models;

import com.klaudi73.blog.validators.PasswordMatches;
import com.klaudi73.blog.validators.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@PasswordMatches(message = "Hasła muszą być identyczne")
public class RegisterUser {

    @NotEmpty(message = "Pole 'login' nie może być puste")
    @NotNull(message = "Pole 'login' nie może być puste")
    private String login;

    @NotEmpty(message = "Pole 'name' nie może być puste")
    @NotNull(message = "Pole 'name' nie może być puste")
    private String name;

    @NotEmpty(message = "Pole 'lastName' nie może być puste")
    @NotNull(message = "Pole 'lastName' nie może być puste")
    private String lastName;


    @NotEmpty(message = "Pole 'email' nie może być puste")
    @NotNull(message = "Pole 'email' nie może być puste")
    @ValidEmail
    private String email;

    @NotEmpty(message = "Pole 'password' nie może być puste")
    @NotNull(message = "Pole 'password' nie może być puste")
    private String password;

    @NotEmpty(message = "Pole 'confirm password' nie może być puste")
    @NotNull(message = "Pole 'confirm password' nie może być puste")
    private String password2;

    @NotEmpty(message = "Pole 'role' nie może być puste")
    @NotNull(message = "Pole 'role' nie może być puste")
    private String role;

    public RegisterUser() {
    }

    public RegisterUser(String login, String name, String lastName, String email,
                        String password, String password2, String role) {
        this.login = login;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisterUser)) return false;
        RegisterUser that = (RegisterUser) o;
        return Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getPassword2(), that.getPassword2()) &&
                Objects.equals(getRole(), that.getRole());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLogin(), getName(), getLastName(), getEmail(), getPassword(), getPassword2(), getRole());
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
