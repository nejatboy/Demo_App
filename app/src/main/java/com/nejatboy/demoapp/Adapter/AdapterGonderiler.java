package com.nejatboy.demoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nejatboy.demoapp.Model.Gonderi;
import com.nejatboy.demoapp.Model.Kullanici;
import com.nejatboy.demoapp.Model.Singleton;
import com.nejatboy.demoapp.R;

import java.util.List;

public class AdapterGonderiler extends RecyclerView.Adapter<AdapterGonderiler.HucreTasarim>{

    private Context context;
    private List<Gonderi> list;

    public AdapterGonderiler(Context context, List<Gonderi> list) {
        this.context = context;
        this.list = list;
    }




    @NonNull
    @Override
    public HucreTasarim onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hucre_gonderi, parent, false);
        return new HucreTasarim(view);
    }




    @Override
    public void onBindViewHolder(@NonNull HucreTasarim holder, int position) {
        Gonderi gonderi = list.get(position);

        holder.textViewYorum.setText(gonderi.getGonderiYorum());
        holder.textViewKullaniciAd.setText(kullaniciAdiGetirByKullaniciId(gonderi.getKullaniciId()));
        holder.imageView.setImageResource(context.getResources().getIdentifier(gonderi.getGonderiResim() , "drawable", context.getPackageName()));
        if (gonderi.getPuanlayanSayisi() > 0) {
            holder.ratingBar.setRating((float) (gonderi.getGonderiPuan() / gonderi.getPuanlayanSayisi()));
        }

    }




    @Override
    public int getItemCount() {
        return list.size();
    }




    private String kullaniciAdiGetirByKullaniciId (int kullaniciId) {
        for (Kullanici kullanici: Singleton.getInstance().getTumKullanicilar()) {
            if (kullanici.getKullaniciId() == kullaniciId) {
                return kullanici.getKullaniciAd();
            }
        }
        return "";
    }






    // inner class
    class HucreTasarim extends RecyclerView.ViewHolder {

        TextView textViewKullaniciAd, textViewYorum;
        RatingBar ratingBar;
        ImageView imageView;

        public HucreTasarim(@NonNull View itemView) {
            super(itemView);

            textViewKullaniciAd = itemView.findViewById(R.id.textViewHucreGonderiKullaniciAd);
            textViewYorum = itemView.findViewById(R.id.textViewHucreGonderiYorum);
            ratingBar = itemView.findViewById(R.id.ratingBarHucreGonderi);
            imageView = itemView.findViewById(R.id.imageViewHucreGonderiResim);
        }
    }
}
