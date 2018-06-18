package com.klaudi73.blog.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roles_users", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    //@OneToMany(mappedBy = "roles_users")
    private Collection<UserEntity> users;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
    private Collection<Privilege> privileges;

    @Column
    private String name;

    public Role() {
    }

    public Role(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) &&
                Objects.equals(getUsers(), role.getUsers()) &&
                Objects.equals(getPrivileges(), role.getPrivileges()) &&
                Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getUsers(), getPrivileges(), getName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                //", users=" + users +
                //", privileges=" + privileges +
                ", name='" + name + '\'' +
                '}';
    }
}