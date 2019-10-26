package com.example.projeto_json.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.projeto_json.R;
import com.example.projeto_json.model.Noticia;
import com.example.projeto_json.view.adapter.NoticiaRecyclerViewAdapter;
import com.example.projeto_json.viewmodel.NoticiaViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NoticiaViewModel viewModel;
    private List<Noticia> noticias = new ArrayList<>();
    private NoticiaRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.buscaNoticias();

        viewModel.retornaNoticias().observe(this, noticias -> {
            adapter.update(noticias);
        });

        viewModel.retornaValorLoading().observe(this, loading -> {
            if(loading){
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progressBar);
        viewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);
        adapter = new NoticiaRecyclerViewAdapter(noticias);
    }
}
