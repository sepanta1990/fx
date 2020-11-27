package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Mohammad Fathizadeh 2020-01-04
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
