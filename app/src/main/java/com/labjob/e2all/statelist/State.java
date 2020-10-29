package com.labjob.e2all.statelist;


public class State {
    String name, id , country_id;

    public State(String name, String id , String country_id ) {
        this.name = name;
        this.id = id;
        this.country_id = country_id;
    }

    public String getName() {
        return name;
    }

    public String getid() {
        return id;
    }
    public String getcountry_id() {
        return country_id;
    }

    }
