package com.nejatboy.demoapp.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nejatboy.demoapp.Model.CrudCevap;
import com.nejatboy.demoapp.Model.Singleton;
import com.nejatboy.demoapp.R;
import com.nejatboy.demoapp.Service.WebServis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {

    private Button buttonUpload;
    private ImageView imageView;
    private Bitmap secilenBitmapResim;
    private EditText editTextYorum;

    private String BASE_URL = "http://192.168.1.4";
    private Retrofit retrofit;
    private Gson gson;
    private CompositeDisposable compositeDisposable;




    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonUpload = view.findViewById(R.id.buttonUpload);
        editTextYorum = view.findViewById(R.id.editTextUploadFragment);
        imageView = view.findViewById(R.id.imageViewUploadFragment);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (secilenBitmapResim != null) {
                    String yorum = editTextYorum.getText().toString();
                    int kullaniciId = Singleton.getInstance().getLoginKullanici().getKullaniciId();
                    //yeniGonderi(encodeToBase64(secilenBitmapResim), yorum, kullaniciId);
                    Toast.makeText(getContext(), "Web servis çalışmıyor.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Resim seçilmedi.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        retrofitKurulumuYap();

        imageView.setOnClickListener(imageViewClicked);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {       //İzin sonuçları (imageView'ın if'i)
        if (requestCode == 1) {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {      //İzin varsa ve verilmişse
                Intent galeriyeGit = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeriyeGit, 2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), data.getData());
                    secilenBitmapResim = ImageDecoder.decodeBitmap(source);
                } else {
                    secilenBitmapResim = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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



    private void yeniGonderi (String base64, String yorum, int kullaniciId) {
        WebServis servis = retrofit.create(WebServis.class);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                servis.yeniGonderi(base64, yorum, kullaniciId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::crudCevabiAl)
        );
    }




    private void uploadImage (String image, String name) {
        WebServis servis = retrofit.create(WebServis.class);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                servis.imageUpload(image, name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::crudCevabiAl)
        );
    }



    private  void crudCevabiAl (CrudCevap crudCevap) {
        System.out.println(crudCevap.getSuccess());
        System.out.println(crudCevap.getMessage());
    }



    public static String encodeToBase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }




    private View.OnClickListener imageViewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                Intent galeriyeGit = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeriyeGit, 2);
            }
        }
    };
}
