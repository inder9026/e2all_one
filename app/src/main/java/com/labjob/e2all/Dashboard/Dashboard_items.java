package com.labjob.e2all.Dashboard;

public class Dashboard_items {
    String startdate, enddate , result_date  , catname , name;

    public Dashboard_items(String startdate, String enddate , String result_date,String catname ,String name  ) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.result_date = result_date;
        this.catname = catname;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCatname() {
        return catname;
    }
    public String getResult_date() {
        return result_date;
    }
    public String getEnddate() {
        return enddate;
    }
    public String getStartdate() {
        return startdate;
    }

}
