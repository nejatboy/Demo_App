package com.nejatboy.demoapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KullanicilarCevap {

    @SerializedName("kullanicilar")
    private List<Kullanici> kullanicilar = null;

    @SerializedName("success")
    private int success;


    public List<Kullanici> getKullanicilar() {
        return kullanicilar;
    }

    public void setKullanicilar(List<Kullanici> kullanicilar) {
        this.kullanicilar = kullanicilar;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
