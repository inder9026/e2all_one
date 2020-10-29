package com.labjob.e2all.allcountry;

public class Country {
    String name, id , sortname, phonecode;

    public Country(String name, String id , String sortname ,  String phonecode) {
        this.name = name;
        this.id = id;
        this.sortname = sortname;
        this.phonecode = phonecode;
    }

    public String getName() {
        return name;
    }

    public String getid() {
        return id;
    }
    public String getSortname() {
        return sortname;
    }
    public String getPhonecode() {
        return phonecode;
    }
}