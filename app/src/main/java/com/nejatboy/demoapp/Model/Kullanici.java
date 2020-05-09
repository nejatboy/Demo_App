package com.nejatboy.demoapp.Model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class Kullanici {

    @SerializedName("kullanici_id")
    private int kullaniciId;

    @SerializedName("kullanici_ad")
    private String kullaniciAd;

    @SerializedName("kullanici_sifre")
    private String kullaniciSifre;


    public Kullanici(int kullaniciId, String kullaniciAd, String kullaniciSifre) {
        this.kullaniciId = kullaniciId;
        this.kullaniciAd = kullaniciAd;
        this.kullaniciSifre = kullaniciSifre;
    }


    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getKullaniciAd() {
        return kullaniciAd;
    }

    public void setKullaniciAd(String kullaniciAd) {
        this.kullaniciAd = kullaniciAd;
    }

    public String getKullaniciSifre() {
        return kullaniciSifre;
    }

    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }
}
