package com.nejatboy.demoapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nejatboy.demoapp.Model.Kullanici;
import com.nejatboy.demoapp.Model.KullanicilarCevap;
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
    private CompositeDisposable compositeDisposable;        //RxJava kullanımı için
    
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
        webServisiCalistir();
        
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())      //Bunu RxJava kullanacaksak yazarız yoksa gerek yok
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        ;
    }




    private void webServisiCalistir () {
        WebServis servis = retrofit.create(WebServis.class);     //Servisimizi oluşturmuş olduk (veri çekmeye hazır)

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                servis.tumKullanicilariGetir()
                        .subscribeOn(Schedulers.io())      //Hangi thread de olacağı
                        .observeOn(AndroidSchedulers.mainThread())        //Sonuçlar main thread de
                        .subscribe(this::tumKullanicilarCevabiAl)        //Sonuçları nerde ele alacaz      (Bu satır önemli aşağıdaki metoda referans eder)
        );
    }




    private  void tumKullanicilarCevabiAl (KullanicilarCevap response) {
        if (response.getSuccess() == 1) {
            tumKullanicilar = response.getKullanicilar();
        }
    }
    
    
    
    private boolean kullaniciKayitliMi (String kullaniciAd, String sifre) {
        for (Kullanici kullanici: tumKullanicilar) {
            if (kullanici.getKullaniciAd().equals(kullaniciAd) && kullanici.getKullaniciSifre().equals(sifre)) {
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

        }
    };
}
