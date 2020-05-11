package com.nejatboy.demoapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nejatboy.demoapp.Adapter.AdapterGonderiler;
import com.nejatboy.demoapp.Model.Gonderi;
import com.nejatboy.demoapp.R;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Gonderi> gonderiler = new ArrayList<>();
    private AdapterGonderiler adapterGonderiler;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewFeedFragment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        staticVeriEkle();
    }




    private void staticVeriEkle () {
        gonderiler.clear();

        gonderiler.add(new Gonderi(1, "test1", "Test yorum1", 1, 10.0, 2));
        gonderiler.add(new Gonderi(2, "test2", "Test yorum2", 2, 8.0, 4));
        gonderiler.add(new Gonderi(3, "test3", "Test yorum3", 2, 14.0, 2));
        gonderiler.add(new Gonderi(4, "test4", "Test yorum4", 4, 12.0, 2));
        gonderiler.add(new Gonderi(5, "test5", "Test yorum5", 1, 5.0, 2));
        gonderiler.add(new Gonderi(6, "test6", "Test yorum6", 3, 3.0, 2));

        adapterGonderiler = new AdapterGonderiler(getContext(), gonderiler);
        recyclerView.setAdapter(adapterGonderiler);
    }
}
