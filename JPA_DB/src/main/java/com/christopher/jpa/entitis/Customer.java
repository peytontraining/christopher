package com.christopher.jpa.entitis;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "customer.getAll", query = "SELECT c FROM Customer c")
public class Customer {
    @Id
    @GeneratedValue
    private int Id;

    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<Project>();

    // Getter and Setter
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
