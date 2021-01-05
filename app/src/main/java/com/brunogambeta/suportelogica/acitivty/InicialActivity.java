package com.brunogambeta.suportelogica.acitivty;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.brunogambeta.suportelogica.R;
import com.brunogambeta.suportelogica.api.ConfiguracaoAPI;
import com.brunogambeta.suportelogica.model.Cliente;
import com.brunogambeta.suportelogica.preferences.EmpresaPreferencias;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Retrofit;

public class InicialActivity extends AppCompatActivity {

    //Declarações das variaveis
    private TextView cnpjTelaInicial;
    private EmpresaPreferencias empresaPreferencias;

    private Retrofit retrofit = ConfiguracaoAPI.getRetrofit();
    private String urlInterna = ConfiguracaoAPI.getUrlApiInterno();
    private String urlExterna = ConfiguracaoAPI.getUrlApiExterno();
    private String tagGet = ConfiguracaoAPI.getTagCliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        //Configuração dos campos inicais
        cnpjTelaInicial = findViewById(R.id.editTextCNPJInicial);

        //Inicialização das variaveis
        Button botaoEntrar = findViewById(R.id.buttonEntrar);

        //Buscando o ultimo cnpj utilizado
        recuperarCNPJPreferences();

        //Configuração do botão Entrar
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cnpj = cnpjTelaInicial.getText().toString();
                if (!cnpj.isEmpty()) {
                    MyTask task = new MyTask();
                    task.execute(urlExterna + tagGet + cnpj);
                    salvarCNPJPreferences(cnpj);
                } else {
                    Toast.makeText(InicialActivity.this, "Necessário digitar o CNPJ ou CPF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Metodo usado para quando abrir o app, ja carregar o ultimo CNPJ informado.
    @Override
    protected void onStart() {
        super.onStart();
        recuperarCNPJPreferences();
    }

    //Metodo usado para abrir a segunda Tela
    public void abrirTelaChamado(String cnpj, String razaoSocial, String numeroChamado) {
        Intent telaChamado = new Intent(InicialActivity.this, ChamadoActivity.class);
        telaChamado.putExtra("cnpj", cnpj);
        telaChamado.putExtra("razaoSocial", razaoSocial);
        telaChamado.putExtra("numeroChamado", numeroChamado);
        startActivity(telaChamado);
    }

    //Metodo usado para abrir a tela de chamado já existente para ser adicionado mais 1 descricao.
    public void abrirTelaChamadoExistente(String cnpj, String razaoSocial, String numeroChamado) {
        Intent telaChamado = new Intent(InicialActivity.this, ChamadoExistenteActivity.class);
        telaChamado.putExtra("cnpj", cnpj);
        telaChamado.putExtra("razaoSocial", razaoSocial);
        telaChamado.putExtra("numeroChamado", numeroChamado);
        startActivity(telaChamado);
    }

    //Método para abrir a tela inicial.
    public void abrirTelaInicial() {
        Intent telaInicial = new Intent(InicialActivity.this, InicialActivity.class);
        startActivity(telaInicial);
    }

    //Método para salvar o ultimo cnpj/cpf informado.
    public void salvarCNPJPreferences(String cnpj) {
        empresaPreferencias = new EmpresaPreferencias(this);
        empresaPreferencias.salvarCNPJ(cnpj);
    }

    //Método para retornar o ultimo cnpj/cpf cadastrado
    public void recuperarCNPJPreferences() {
        empresaPreferencias = new EmpresaPreferencias(this);
        String cnpjRecuperado = empresaPreferencias.recuperarCnpj();
        if (!cnpjRecuperado.isEmpty() || !cnpjRecuperado.equals("")) {
            cnpjTelaInicial.setText(cnpjRecuperado);
        }
    }

    //Configurações do dialog para dizer que ja existe chamado
    public void dialogConfiguracao(String cnpj, String razaoSocial, String numeroChamado) {

        //Instância do Alertdialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //Configurando AlertDialog
        dialog.setTitle("Chamado Existente!");
        dialog.setMessage("Atenção ja possui um chamado em aberto para esse CNPJ, aguarde ser atendido.\nDeseja adicionar mais um comentario nesse chamado?");

        //Função de cancelamento do AlertDialog
        dialog.setCancelable(false);

        //Configuração do AlertDialog Para Sim
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                abrirTelaChamadoExistente(cnpj, razaoSocial, numeroChamado);

            }
        });
        //Configuração do AlertDialog Para Não
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Suporte Logica agradece!", Toast.LENGTH_SHORT).show();

            }
        });

        //Criar e exibir AlertDialog
        dialog.create();
        dialog.show();
    }

    //Classe interna para fazer o GET.(Analisar uma possivel mudanca desta forma de fazer o get.)
    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
                //cria conexão
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //Recupera os dados em bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader lê os dados em bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para leitura dos caracteres do InputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String cnpj;
            String razaosocial;
            String numerochamado;
            Cliente cliente = new Cliente();

            //Validacao do json para buscar os dados
            try {
                JSONObject jsonObject = new JSONObject(s);
                cnpj = jsonObject.getString("cnpj");
                razaosocial = jsonObject.getString("razaosocial");
                numerochamado = jsonObject.getString("numerodochamado");

                //Validacao para ver se o cliente existe
                if (!razaosocial.isEmpty()) {
                    cliente.setCnpj(cnpj);
                    cliente.setRazaoSocial(razaosocial);
                    cliente.setNumeroChamado(numerochamado);

                    //Validacao para ver se tem o numero de chamado, caso  tenha, abre a tela de pergunta se quer adicionar uma nova descricao no chamado.
                    if (!numerochamado.isEmpty()) {
                        dialogConfiguracao(cliente.getCnpj(), cliente.getRazaoSocial(), cliente.getNumeroChamado());
                    } else {
                        //Caso nao tiver numero de chamado, abrir um tela para cadastrar um novo chamado.rr
                        abrirTelaChamado(cliente.getCnpj(), cliente.getRazaoSocial(), cliente.getNumeroChamado());
                    }
                } else {
                    Toast.makeText(InicialActivity.this, "Cliente não cadastrado!", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
