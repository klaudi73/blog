package com.klaudi73.blog.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private Long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

    public Privilege() {
    }

    public Privilege(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege)) return false;
        Privilege privilege = (Privilege) o;
        return Objects.equals(getId(), privilege.getId()) &&
                Objects.equals(getName(), privilege.getName()) &&
                Objects.equals(getRoles(), privilege.getRoles());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getRoles());
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}