package com.nejatboy.demoapp.Service;

import com.nejatboy.demoapp.Model.Kullanici;
import com.nejatboy.demoapp.Model.KullanicilarCevap;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WebServis {

    @GET("tumKullanicilar.php")
    Observable<KullanicilarCevap> tumKullanicilariGetir ();


}
