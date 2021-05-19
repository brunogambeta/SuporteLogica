package com.brunogambeta.suportelogica.api;
/*
 * Created by Bruno Gambeta on 18/05/2021.
 */

import com.brunogambeta.suportelogica.model.Chamado;
import com.brunogambeta.suportelogica.model.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    //Esta funcao ainda nao foi implementada
    //Requisição GET para recuperar o cliente ao abrir chamado
    @Headers("Content-Type: application/json")
    @GET("/cliente/{cnpj}")
    Call<Cliente> recuperarClienteAbertura(@Path("cnpj") String cnpj);

    //Requisicao POST para abrir um chamado novo
    @POST("chamado/")
    Call<Chamado> enviarChamado (@Body Chamado chamado);

    //Requisicao POST para atualizar um chamado ja existente.
    @POST("chamado/")
    Call<Chamado> atualizarChamado(@Body Chamado chamado);


}


