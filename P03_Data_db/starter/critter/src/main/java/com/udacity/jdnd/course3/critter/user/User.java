package com.udacity.jdnd.course3.critter.user;

import org.checkerframework.framework.qual.InheritedAnnotation;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

/**
 * Changed Strategy. Was having too much trouble trying to use inheritance. I'll try later, after the conclusion
 * of this ND.
 */
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Nationalized
    private String name;

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
}
