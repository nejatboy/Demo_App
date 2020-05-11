package com.nejatboy.demoapp.Service;

import android.graphics.Bitmap;

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

    @POST("gonderiEkle.php")
    @FormUrlEncoded
    Observable<CrudCevap> yeniGonderi(@Field("encoded_string") String base64, @Field("yorum") String yorum, @Field("kullanici_id") int kullaniciId);

    @POST("updateinfo.php")
    @FormUrlEncoded
    Observable<CrudCevap> imageUpload(@Field("image") String image, @Field("name") String name);
}
