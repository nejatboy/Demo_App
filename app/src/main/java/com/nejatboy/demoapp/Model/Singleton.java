package com.nejatboy.demoapp.Model;

import java.util.List;

public class Singleton {

    private Kullanici loginKullanici;
    private List<Kullanici> tumKullanicilar;


    private static Singleton singleton;
    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }


    public Kullanici getLoginKullanici() {
        return loginKullanici;
    }

    public void setLoginKullanici(Kullanici loginKullanici) {
        this.loginKullanici = loginKullanici;
    }

    public List<Kullanici> getTumKullanicilar() {
        return tumKullanicilar;
    }

    public void setTumKullanicilar(List<Kullanici> tumKullanicilar) {
        this.tumKullanicilar = tumKullanicilar;
    }

    public static Singleton getSingleton() {
        return singleton;
    }

    public static void setSingleton(Singleton singleton) {
        Singleton.singleton = singleton;
    }
}
