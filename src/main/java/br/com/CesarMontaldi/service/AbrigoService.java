package br.com.CesarMontaldi.service;

import br.com.CesarMontaldi.domain.Abrigo;
import br.com.CesarMontaldi.infra.RequestDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AbrigoService {

    private RequestDispatcher requestDispatcher;

    public AbrigoService(RequestDispatcher requestDispatcher) {
        this.requestDispatcher = requestDispatcher;
    }

    public void listarAbrigo() throws IOException, InterruptedException {

        String uri = "http://localhost:8080/abrigos";

        HttpResponse<String> response = requestDispatcher.requestGet(uri);
        String responseBody = response.body();
        Abrigo[] abrigos = new ObjectMapper().readValue(responseBody, Abrigo[].class);
        List<Abrigo> abrigoList = Arrays.stream(abrigos).toList();

        if (abrigoList.isEmpty()) {
            System.out.println("Não há abrigos cadastrados:");
        } else {
            mostrarAbrigos(abrigoList);
        }
    }

    private void mostrarAbrigos(List<Abrigo> abrigos) {
        System.out.println("Abrigos cadastrados:");
        abrigos.forEach(abrigo -> {
            System.out.println(abrigo.getId() + " - " + abrigo.getNome());
        });
    }

    public void cadastrarAbrigo() throws IOException, InterruptedException {

        System.out.println("Digite o nome do abrigo:");
        String nome = new Scanner(System.in).nextLine();
        System.out.println("Digite o telefone do abrigo:");
        String telefone = new Scanner(System.in).nextLine();
        System.out.println("Digite o email do abrigo:");
        String email = new Scanner(System.in).nextLine();

        Abrigo abrigo = new Abrigo(nome, telefone, email);
        String uri = "http://localhost:8080/abrigos";

        HttpResponse<String> response = requestDispatcher.requestPost(uri, abrigo);
        int statusCode = response.statusCode();
        String responseBody = response.body();

        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(responseBody);
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(responseBody);
        }
    }

}
