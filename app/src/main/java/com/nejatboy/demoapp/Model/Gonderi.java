package com.nejatboy.demoapp.Model;

import com.google.gson.annotations.SerializedName;

public class Gonderi {

    @SerializedName("gonderi_id")
    private int gonderiId;

    @SerializedName("gonderi_resim")
    private String gonderiResim;

    @SerializedName("gonderi_yorum")
    private String gonderiYorum;

    @SerializedName("kullanici_id")
    private int kullaniciId;

    @SerializedName("gonderi_puan")
    private Double gonderiPuan;

    @SerializedName("puanlayan_sayisi")
    private int puanlayanSayisi;


    public Gonderi(int gonderiId, String gonderiResim, String gonderiYorum, int kullaniciId, Double gonderiPuan, int puanlayanSayisi) {
        this.gonderiId = gonderiId;
        this.gonderiResim = gonderiResim;
        this.gonderiYorum = gonderiYorum;
        this.kullaniciId = kullaniciId;
        this.gonderiPuan = gonderiPuan;
        this.puanlayanSayisi = puanlayanSayisi;
    }


    public int getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(int gonderiId) {
        this.gonderiId = gonderiId;
    }

    public String getGonderiResim() {
        return gonderiResim;
    }

    public void setGonderiResim(String gonderiResim) {
        this.gonderiResim = gonderiResim;
    }

    public String getGonderiYorum() {
        return gonderiYorum;
    }

    public void setGonderiYorum(String gonderiYorum) {
        this.gonderiYorum = gonderiYorum;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public Double getGonderiPuan() {
        return gonderiPuan;
    }

    public void setGonderiPuan(Double gonderiPuan) {
        this.gonderiPuan = gonderiPuan;
    }

    public int getPuanlayanSayisi() {
        return puanlayanSayisi;
    }

    public void setPuanlayanSayisi(int puanlayanSayisi) {
        this.puanlayanSayisi = puanlayanSayisi;
    }
}
