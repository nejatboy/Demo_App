package com.nejatboy.demoapp.Service;

import com.nejatboy.demoapp.Model.CrudCevap;
import com.nejatboy.demoapp.Model.Kullanici;
import com.nejatboy.demoapp.Model.KullanicilarCevap;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServis {

    @GET("tumKullanicilar.php")
    Observable<KullanicilarCevap> tumKullanicilariGetir ();


    @POST("yeniKullanici.php")
    @FormUrlEncoded
    Observable<CrudCevap> yeniKullanici(@Field("kullanici_ad") String kullaniciAd, @Field("kullanici_sifre") String kullaniciSifre);
}
