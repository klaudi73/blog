package com.klaudi73.blog.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    private UserEntity userEntity;

    @Column
    private Date expiryDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(final String token) {
        this.token = token;
    }

    public PasswordResetToken(final String token, final UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(final UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordResetToken)) return false;
        PasswordResetToken token1 = (PasswordResetToken) o;
        return Objects.equals(getId(), token1.getId()) &&
                Objects.equals(getToken(), token1.getToken()) &&
                Objects.equals(getUserEntity(), token1.getUserEntity()) &&
                Objects.equals(getExpiryDate(), token1.getExpiryDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getToken(), getUserEntity(), getExpiryDate());
    }

    @Override
    public String toString() {
        return "VerificationToken [String = " + token + "], " +
                "[Expires = " + expiryDate + " ]";
    }
}
