package com.brunogambeta.suportelogica.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/*
    Created by Bruno on 19/10/2020
*/
public class EmpresaPreferencias {

    //Configuracao inicial das variaveis usadas no sharedPreferences.
    private final String NOME_ARQUIVO = "empresa.preferencias";
    private final String CHAVE_CNPJ = "cnpj";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public EmpresaPreferencias (Context c) {
        this.context = c;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, 0);
        editor = preferences.edit();
    }
    //Metodo usuado para gravar o ultimo cnpj ou cpf informado.
    public void salvarCNPJ (String cnpj) {
        editor.putString(CHAVE_CNPJ, cnpj);
        editor.commit();
    }
    //Metodo usado para recuperar o ultimo cnpj ou cpf informado
    public String recuperarCnpj () {
        return preferences.getString(CHAVE_CNPJ, "");
    }
}

