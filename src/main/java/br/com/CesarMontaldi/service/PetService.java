package br.com.CesarMontaldi.service;

import br.com.CesarMontaldi.domain.Pet;
import br.com.CesarMontaldi.infra.RequestDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PetService {

    private RequestDispatcher requestDispatcher;

    public PetService(RequestDispatcher requestDispatcher) {
        this.requestDispatcher = requestDispatcher;
    }

    public void listarPetsDoAbrigo() throws IOException, InterruptedException {

        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();

        String uri = "http://localhost:8080/abrigos/" + idOuNome +"/pets";

        HttpResponse<String> response = requestDispatcher.requestGet(uri);
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
        }

        String responseBody = response.body();
        Pet[] pets = new ObjectMapper().readValue(responseBody, Pet[].class);
        List<Pet> petList = Arrays.stream(pets).toList();

        System.out.println("Pets cadastrados:");
        petList.forEach(System.out::println);

    }

    public void importarPetsDoAbrigo() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o id ou nome do abrigo: ");
        String idOuNome = scanner.nextLine();

        System.out.println("Digite o nome do arquivo CSV: ");
        String nomeArquivo = scanner.nextLine();

        scanner.close();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + nomeArquivo);
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] campos = line.split(",");
            String tipo = campos[0].toUpperCase();
            String nome = campos[1];
            String raca = campos[2];
            int idade = Integer.parseInt(campos[3]);
            String cor = campos[4];
            Double peso = Double.valueOf(campos[5]);

            Pet pet = new Pet(tipo, nome, raca, idade, cor, peso);
            String uri = "http://localhost:8080/abrigos/" + idOuNome + "/pets";

            HttpResponse<String> response = requestDispatcher.requestPost(uri, pet);
            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + nome);
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + nome);
                System.out.println(responseBody);
                break;
            }
        }
        reader.close();
    }
}
