package com.nejatboy.demoapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nejatboy.demoapp.Model.CrudCevap;
import com.nejatboy.demoapp.Model.Kullanici;
import com.nejatboy.demoapp.Model.KullanicilarCevap;
import com.nejatboy.demoapp.Model.Singleton;
import com.nejatboy.demoapp.R;
import com.nejatboy.demoapp.Service.WebServis;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GirisActivity extends AppCompatActivity {

    private EditText editTextKullaniciAdi, editTextSifre;
    private Button buttonGirisYap, buttonHesapOlustur;
    private CheckBox checkBoxBeniHatirla;
    private ProgressBar progressBar;
    private CardView cardView;

    private String BASE_URL = "http://192.168.1.4";
    private Retrofit retrofit;
    private Gson gson;
    private CompositeDisposable compositeDisposable;
    
    private List<Kullanici> tumKullanicilar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        editTextKullaniciAdi = findViewById(R.id.editTextGirisActivityKullaniciAdi);
        editTextSifre = findViewById(R.id.editTextGirisActivitySifre);
        buttonGirisYap = findViewById(R.id.buttonGirisActivityGirisYap);
        buttonHesapOlustur = findViewById(R.id.buttonGirisActivityHesapOlustur);
        checkBoxBeniHatirla = findViewById(R.id.checkBoxBeniHatirla);
        progressBar = findViewById(R.id.progressBarGirisActivity);
        cardView = findViewById(R.id.cardViewGirisActivity);

        retrofitKurulumuYap();
        tumKullanicilariGetir();
        
        buttonGirisYap.setOnClickListener(buttonGirisYapClicked);
        buttonHesapOlustur.setOnClickListener(buttonHesapOlusturClicked);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }




    private void retrofitKurulumuYap () {
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        ;
    }




    private void tumKullanicilariGetir () {
        WebServis servis = retrofit.create(WebServis.class);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                servis.tumKullanicilariGetir()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::tumKullanicilarCevabiAl)
        );
    }




    private void kullaniciEkle (String kullaniciAd, String kullaniciSifre) {
        WebServis servis = retrofit.create(WebServis.class);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                servis.yeniKullanici(kullaniciAd, kullaniciSifre)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::crudCevabiAl)
        );
    }




    private  void tumKullanicilarCevabiAl (KullanicilarCevap response) {
        if (response.getSuccess() == 1) {
            tumKullanicilar = response.getKullanicilar();
        }
    }




    private  void crudCevabiAl (CrudCevap crudCevap) {
        if (crudCevap.getSuccess() == 1) {
            Toast.makeText(this, "Kullanıcı oluşturuldu", Toast.LENGTH_SHORT).show();
            tumKullanicilariGetir();
        }
    }
    
    
    
    private boolean kullaniciKayitliMi (String kullaniciAd, String sifre) {
        for (Kullanici kullanici: tumKullanicilar) {
            if (kullanici.getKullaniciAd().equals(kullaniciAd) && kullanici.getKullaniciSifre().equals(sifre)) {
                Singleton.getInstance().setLoginKullanici(kullanici);
                Singleton.getInstance().setTumKullanicilar(tumKullanicilar);
                return true;
            }
        }
        return  false;
    }
    
    
    
    
    private boolean kullaniciAdiMevcutMu (String kullaniciAd) {
        for (Kullanici kullanici: tumKullanicilar) {
            if (kullanici.getKullaniciAd().equals(kullaniciAd)) {
                return true;
            }
        }
        return  false;
    }
    
    
    
    
    private View.OnClickListener buttonGirisYapClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String kullaniciAd = editTextKullaniciAdi.getText().toString();
            String sifre = editTextSifre.getText().toString();
            
            if (kullaniciAd.equals("") || sifre.equals("")) {
                Toast.makeText(GirisActivity.this, "Kullanıcı adı ve şifrenizi giriniz.", Toast.LENGTH_SHORT).show();

            } else {
                if (kullaniciKayitliMi(kullaniciAd, sifre)) {
                    Toast.makeText(GirisActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finishAffinity();

                } else {
                    Toast.makeText(GirisActivity.this, "Kullanıcı bulunamadı.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };




    private View.OnClickListener buttonHesapOlusturClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = LayoutInflater.from(GirisActivity.this).inflate(R.layout.dialog_hesap_olustur, null);
            EditText kullaniciAd = view.findViewById(R.id.editTextDialogHesapOlusturKullaniciAd);
            EditText sifre = view.findViewById(R.id.editTextDialogHesapOlusturKullaniciSifre);
            Button buttonKayitOl = view.findViewById(R.id.buttonDialogHesapOlusturKayitOl);

            buttonKayitOl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kullaniciAd.getText().toString().equals("") || sifre.getText().toString().equals("")) {
                        Toast.makeText(GirisActivity.this, "Kullanıcı adı ve şifre boş olamaz", Toast.LENGTH_SHORT).show();
                    } else {
                        if (kullaniciAdiMevcutMu(kullaniciAd.getText().toString()))  {
                            Toast.makeText(GirisActivity.this, "Böyle bir kullanıcı adı sistemde mevcut", Toast.LENGTH_SHORT).show();
                        } else {
                            kullaniciEkle(kullaniciAd.getText().toString(), sifre.getText().toString());
                        }
                    }
                }
            });

            Dialog dialog = new Dialog(GirisActivity.this);
            dialog.setContentView(view);
            dialog.show();
        }
    };
}
