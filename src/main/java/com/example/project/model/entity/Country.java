package com.example.project.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity{
    private String name;
    private Set<Town> towns;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
@OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    public Set<Town> getTowns() {
        return towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }
}
