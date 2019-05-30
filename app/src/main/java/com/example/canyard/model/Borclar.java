package com.example.canyard.model;

public class Borclar {

    private String borcTuru;
    private String ilgiliBorc;
    private String borcTutar;

    public Borclar(String borcTuru, String ilgiliBorc, String borcTutar) {
        this.borcTuru = borcTuru;
        this.ilgiliBorc = ilgiliBorc;
        this.borcTutar = borcTutar;
    }

    public Borclar(){


    }

    public String getBorcTuru() {
        return borcTuru;
    }

    public void setBorcTuru(String borcTuru) {
        this.borcTuru = borcTuru;
    }

    public String getIlgiliBorc() {
        return ilgiliBorc;
    }

    public void setIlgiliBorc(String ilgiliBorc) {
        this.ilgiliBorc = ilgiliBorc;
    }

    public String getBorcTutar() {
        return borcTutar;
    }

    public void setBorcTutar(String borcTutar) {
        this.borcTutar = borcTutar;
    }
}
