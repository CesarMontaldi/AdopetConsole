package br.com.CesarMontaldi.command;

import br.com.CesarMontaldi.infra.RequestDispatcher;
import br.com.CesarMontaldi.service.PetService;

import java.io.IOException;

public class ListarPetsDoAbrigoCommand implements Command {

    @Override
    public void execute() {

        try {
            RequestDispatcher request = new RequestDispatcher();
            PetService petService = new PetService(request);

            petService.listarPetsDoAbrigo();

        }catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
