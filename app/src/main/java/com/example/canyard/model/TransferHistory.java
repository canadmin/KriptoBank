package com.example.canyard.model;

public class TransferHistory {

    private String alanHesap;
    private String gonderenHesap;
    private String tarih;
    private String tur;
    private String tutar;

    public TransferHistory(String tarih, String tur, String tutar) {
        this.tarih = tarih;
        this.tur = tur;
        this.tutar = tutar;
    }

    public TransferHistory() {

    }

    public String getAlanHesap() {
        return alanHesap;
    }

    public void setAlanHesap(String alanHesap) {
        this.alanHesap = alanHesap;
    }

    public String getGonderenHesap() {
        return gonderenHesap;
    }

    public void setGonderenHesap(String gonderenHesap) {
        this.gonderenHesap = gonderenHesap;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getTutar() {
        return tutar;
    }

    public void setTutar(String tutar) {
        this.tutar = tutar;
    }
}
