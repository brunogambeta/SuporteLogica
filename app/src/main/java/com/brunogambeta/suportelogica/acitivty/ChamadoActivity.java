package com.brunogambeta.suportelogica.acitivty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ChamadoActivity extends AppCompatActivity {

    //Configuracao das variaveis iniciais
    private TextView cnpjEmpresaChamado, razaoSocialEmpresaChamado, cnpjParaAPi;
    private EditText nomeContatoChamado, telefoneContatoChamado, descricaoProblemaChamado;
    private Button botaoGravarChamado;
    private String cnpjEmpresa, razaoSocial;

    //Configurações do retroFit
    private Retrofit retrofit = ConfiguracaoAPI.getRetrofit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamado);

        //Configurações de informações iniciais.
        inicializarComponentes();

        //Metodo para pegar os dados passados pela InicialActivity
        Bundle dados = getIntent().getExtras();
        cnpjEmpresa = dados.getString("cnpj");
        razaoSocial = dados.getString("razaoSocial");
        Log.i("Bundle", "Resultado: " + cnpjEmpresa + " - " + razaoSocial + " - ");


        //Setando nos seus respectivos campos os dados enviados pela InicialActivity
        cnpjEmpresaChamado.setText("CNPJ: \n" + ValidaCNPJ.imprimeCNPJ(cnpjEmpresa));
        razaoSocialEmpresaChamado.setText("Razão Social: \n" + razaoSocial);

        //Configuração do botão gravar.
        botaoGravarChamado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeChamado = nomeContatoChamado.getText().toString();
                String telefoneChamado = telefoneContatoChamado.getText().toString();
                String descricaoChamado = descricaoProblemaChamado.getText().toString();

                //Validacao dos campos a serem preenchidos pelo usuario.
                if (!nomeChamado.isEmpty()) {
                    if (!telefoneChamado.isEmpty()) {
                        if (!descricaoChamado.isEmpty()) {

                            //Se todos os campos estivem preenchidos corretamente, faz o procedimento de salvar o chamado.
                            salvarChamado(nomeChamado, telefoneChamado, descricaoChamado);
                            Toast.makeText(ChamadoActivity.this, "Chamado aberto com sucesso!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ChamadoActivity.this, "Descrição do chamado não informada, por favor verifique!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChamadoActivity.this, "Telefone para contato do chamado não informado, por favor verifique!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChamadoActivity.this, "Nome para contato do chamado não informado, por favor verifique!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Metodo para salvar os chamados novos
    private void salvarChamado(String nomeChamado, String telefoneChamado, String descricaoChamado) {

        Chamado chamado = new Chamado();
        chamado.setCnpj(cnpjEmpresa);
        chamado.setDescricaoProblemaDuvida(descricaoChamado);
        chamado.setNomeParaContato(nomeChamado);
        chamado.setTelefoneParaContato(telefoneChamado);
        chamado.setRazaoSocial(razaoSocial);
        chamado.setNumeroChamado("");

        APIService apiService = retrofit.create(APIService.class);
        Call<Chamado> callChamado = apiService.enviarChamado(chamado);
        callChamado.enqueue(new Callback<Chamado>() {
            @Override
            public void onResponse(Call<Chamado> call, Response<Chamado> response) {
                if (response.isSuccessful()) {
                    //Se chamado for aberto com sucesso, retorna mensagem avisando o usuario, e abre a tela principal novamente
                    Toast.makeText(ChamadoActivity.this, "Chamado aberto com sucesso! ", Toast.LENGTH_SHORT).show();
                    telaPrincipal();
                }
            }

            @Override
            public void onFailure(Call<Chamado> call, Throwable t) {
                Cliente chamadoResponse = new Cliente();
                //Caso der falha, retorna erro ao usuario.
                Toast.makeText(ChamadoActivity.this, "Não foi possivel abrir o chamado!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Metodo para abrir a tela principal
    public void telaPrincipal() {
        Intent principal = new Intent(ChamadoActivity.this, InicialActivity.class);
        startActivity(principal);
    }

    //Metodo para inicializar os componentes
    private void inicializarComponentes() {
        cnpjEmpresaChamado = findViewById(R.id.textViewCnpjEmpresaChamado);
        razaoSocialEmpresaChamado = findViewById(R.id.textViewRazaoSocialEmpresa);
        nomeContatoChamado = findViewById(R.id.editTextNomeContato);
        telefoneContatoChamado = findViewById(R.id.editTextTelefoneContato);
        descricaoProblemaChamado = findViewById(R.id.editTextDescricaoProblema);
        botaoGravarChamado = findViewById(R.id.buttonGravarChamado);
    }
}
