package com.nejatboy.demoapp.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.nejatboy.demoapp.R;

public class GirisActivity extends AppCompatActivity {

    private EditText editTextKullaniciAdi, editTextSifre;
    private Button buttonGirisYap, buttonHesapOlustur;
    private CheckBox checkBoxBeniHatirla;
    private ProgressBar progressBar;
    private CardView cardView;




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
    }
}
