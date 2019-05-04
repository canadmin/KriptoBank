package com.example.canyard.model;

public class CardMovements {

    private String odenenFirma;
    private String Tutar;
    private String tur;

    public CardMovements(String odenenFirma, String tutar, String tur) {
        this.odenenFirma = odenenFirma;
        Tutar = tutar;
        this.tur = tur;
    }

    public CardMovements() {
    }

    public String getOdenenFirma() {
        return odenenFirma;
    }

    public void setOdenenFirma(String odenenFirma) {
        this.odenenFirma = odenenFirma;
    }

    public String getTutar() {
        return Tutar;
    }

    public void setTutar(String tutar) {
        Tutar = tutar;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }
}
