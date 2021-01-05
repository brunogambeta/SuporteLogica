package com.brunogambeta.suportelogica.acitivty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brunogambeta.suportelogica.R;
import com.brunogambeta.suportelogica.api.APIService;
import com.brunogambeta.suportelogica.api.ConfiguracaoAPI;
import com.brunogambeta.suportelogica.helper.ValidaCNPJ;
import com.brunogambeta.suportelogica.model.Chamado;
import com.brunogambeta.suportelogica.model.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChamadoExistenteActivity extends AppCompatActivity {

    //Inicializacao dos campos e das variaveis usadas no app.
    private TextView cnpjEmpresaChamado, razaoSocialEmpresaChamado, numeroChamadoExistente;
    private EditText descricaoProblemaChamado;
    private Button botaoGravarChamado;
    private String cnpjEmpresa, razaoSocial, numeroChamado;

    //Configurações do retroFit
    private Retrofit retrofit = ConfiguracaoAPI.getRetrofit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado_existente);

        //Configurações de informações iniciais.
        cnpjEmpresaChamado = findViewById(R.id.textViewCnpjEmpresaChamadoExistente);
        razaoSocialEmpresaChamado = findViewById(R.id.textViewRazaoSocialEmpresaExistente);
        descricaoProblemaChamado = findViewById(R.id.editTextDescricaoProblemaExistente);
        numeroChamadoExistente = findViewById(R.id.textViewNumeroChamadoExistente);
        botaoGravarChamado = findViewById(R.id.buttonGravarChamadoExistente);

        //Metodo para pegar os dados passados pela InicialActivity
        Bundle dados = getIntent().getExtras();
        cnpjEmpresa = dados.getString("cnpj");
        razaoSocial = dados.getString("razaoSocial");
        numeroChamado = dados.getString("numeroChamado");

        //Setando nos seus respectivos campos os dados enviados pela InicialActivity
        cnpjEmpresaChamado.setText("CNPJ: \n" + ValidaCNPJ.imprimeCNPJ(cnpjEmpresa));
        razaoSocialEmpresaChamado.setText("Razão Social: \n" + razaoSocial);
        numeroChamadoExistente.setText("Chamado: \n"+numeroChamado);

        //Atribuindo funcao ao botao de gravar chamado.
        botaoGravarChamado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chamado chamado = new Chamado();
                String descricaoChamado = descricaoProblemaChamado.getText().toString();

                //Validacao do campo descricao do chamado
                if (!descricaoChamado.isEmpty()) {
                    atualizarChamado(descricaoChamado);
                } else {
                    Toast.makeText(ChamadoExistenteActivity.this, "Descrição do chamado não informada!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Netodo para atualizar a descricao do chamado
    private void atualizarChamado(String descricaoChamado) {

        Chamado chamado = new Chamado();
        chamado.setCnpj(cnpjEmpresa);
        chamado.setDescricaoProblemaDuvida(descricaoChamado);
        chamado.setNomeParaContato("");
        chamado.setTelefoneParaContato("");
        chamado.setRazaoSocial(razaoSocial);
        chamado.setNumeroChamado(numeroChamado);

        APIService apiService = retrofit.create(APIService.class);
        Call<Chamado> callChamado = apiService.atualizarChamado(chamado);
        callChamado.enqueue(new Callback<Chamado>() {
            @Override
            public void onResponse(Call<Chamado> call, Response<Chamado> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChamadoExistenteActivity.this, "Chamado alterado com sucesso! ", Toast.LENGTH_SHORT).show();
                    telaPrincipal();

                }
            }

            @Override
            public void onFailure(Call<Chamado> call, Throwable t) {
                Toast.makeText(ChamadoExistenteActivity.this, "Não foi possivel alterar o chamado!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Metodo para abrir a tela principal do app.
    public void telaPrincipal() {
        Intent principal = new Intent(ChamadoExistenteActivity.this, InicialActivity.class);
        startActivity(principal);
    }

}