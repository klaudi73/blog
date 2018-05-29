package com.klaudi73.blog.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id")
    private Long userInfoId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "about_me", length = 1000)
    private String aboutMe;

    @Column(name = "image")
    private byte[] image;

    public UserInfoEntity() {
    }

    public UserInfoEntity(UserEntity userEntity, String aboutMe, byte[] image) {
        this.userEntity = userEntity;
        this.aboutMe = aboutMe;
        this.image = image;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoEntity)) return false;
        UserInfoEntity that = (UserInfoEntity) o;
        return Objects.equals(getUserInfoId(), that.getUserInfoId()) &&
                Objects.equals(getUserEntity(), that.getUserEntity()) &&
                Objects.equals(getAboutMe(), that.getAboutMe()) &&
                Arrays.equals(getImage(), that.getImage());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getUserInfoId(), getUserEntity(), getAboutMe());
        result = 31 * result + Arrays.hashCode(getImage());
        return result;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "userInfoId=" + userInfoId +
                ", userEntity=" + userEntity +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }
}
