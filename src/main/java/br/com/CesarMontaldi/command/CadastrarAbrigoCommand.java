package br.com.CesarMontaldi.command;

import br.com.CesarMontaldi.infra.RequestDispatcher;
import br.com.CesarMontaldi.service.AbrigoService;

import java.io.IOException;

public class CadastrarAbrigoCommand implements Command {

    @Override
    public void execute() {

        try {
            RequestDispatcher request = new RequestDispatcher();
            AbrigoService abrigoService = new AbrigoService(request);

            abrigoService.cadastrarAbrigo();

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
