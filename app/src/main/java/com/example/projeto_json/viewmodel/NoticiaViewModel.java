package com.example.projeto_json.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projeto_json.model.Noticia;
import com.example.projeto_json.model.NoticiaResposta;
import com.example.projeto_json.repository.NoticiaRepository;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NoticiaViewModel extends AndroidViewModel {
    private MutableLiveData<List<Noticia>> listanoticia = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private NoticiaRepository repository = new NoticiaRepository();
    private CompositeDisposable disposable = new CompositeDisposable();


    public NoticiaViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<List<Noticia>> retornaNoticias(){
        return this.listanoticia;
    }

    public LiveData<Boolean> retornaValorLoading(){
        return this.loading;
    }

    public void buscaNoticias(){
        disposable.add(
                repository.obterListaNoticias(getApplication().getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 ->loading.setValue(true))
                .doAfterTerminate(()-> loading.setValue(false))
                .subscribe(noticiaResposta ->{
                   listanoticia.setValue(noticiaResposta.getNoticias());
                },
                        throwable -> {
                    Log.i("LOG", "busca noticas" + throwable.getMessage());
                        })

        );



    }






}


