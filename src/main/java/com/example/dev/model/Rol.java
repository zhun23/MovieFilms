package com.example.dev.model;

import com.example.dev.utilities.References;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name=References.ROL_TABLE_NAME)
@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rolid;

    @Column(name = "rolname")
    private String rolname;

    //@OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    //private java.util.List<UserCt> usuarios;

    public Rol() {
    }

    public Rol(String rolname) {
        this.rolname = rolname;
    }

    public Integer getRolid() {
        return rolid;
    }

    public void setRolid(int rolid) {
        this.rolid = rolid;
    }

    public String getRolname() {
        return rolname;
    }

    public void setRolname(String rolname) {
        this.rolname = rolname;
    }

}
