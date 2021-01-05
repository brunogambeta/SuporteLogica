package com.brunogambeta.suportelogica.api;

import android.content.Intent;
import android.view.View;

import androidx.annotation.RestrictTo;

import com.brunogambeta.suportelogica.acitivty.InicialActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
    Created by Bruno on 30/11/2020
*/
public class ConfiguracaoAPI {

    private static Retrofit retornoRetrofit;

    //Variaveis estaticas para utilização da API
    private final static String URL_API_INTERNO = "http://192.168.1.54:211/datasnap/rest/tsm/";
    private final static String URL_API_EXTERNO = "http://logicabrusque.no-ip.info:211/datasnap/rest/tsm/";
    private final static String TAG_CLIENTE = "cliente/";
    private final static String TAG_CHAMADO = "chamado/";

    //Retorna a configuração do retrofit.
    public static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retornoRetrofit = new Retrofit.Builder()
                //.baseUrl(URL_API_INTERNO)
                .baseUrl(URL_API_EXTERNO)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retornoRetrofit;
    }

    //Metodo estatico para retornar a url da api internamente.
    public static String getUrlApiInterno() {
        return URL_API_INTERNO;
    }

    //Metodo estatico para retornar a url da api externamente.
    public static String getUrlApiExterno() {
        return URL_API_EXTERNO;
    }

    //Metodo usado para retornar a tag do cliente.
    public static String getTagCliente() {
        return TAG_CLIENTE;
    }

    //Metodo usado para retornar a tag do chamado
    public static String getTagChamado() {
        return TAG_CHAMADO;
    }


}
