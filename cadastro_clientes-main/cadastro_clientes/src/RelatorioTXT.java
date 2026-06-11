import dao.AnimalDAO;
import dao.ClienteDAO;
import dao.RacaDAO;
import modelo.Animal;
import modelo.Cliente;
import modelo.Raca;

import java.io.FileWriter;
import java.util.ArrayList;

public class RelatorioTXT {

    public static void gerarClientes() {

        try {

            ArrayList<Cliente> lista =
                    new ClienteDAO().consultar("");

            FileWriter fw =
                    new FileWriter("relatorio_clientes.txt");

            for (Cliente c : lista) {

                fw.write(
                        "ID: " + c.getId() +
                        " | Nome: " + c.getNome() +
                        " | CPF: " + c.getCpf() +
                        "\n"
                );
            }

            fw.close();

            System.out.println("Relatório de clientes gerado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gerarRacas() {

        try {

            ArrayList<Raca> lista =
                    new RacaDAO().consultar();

            FileWriter fw =
                    new FileWriter("relatorio_racas.txt");

            for (Raca r : lista) {

                fw.write(
                        "ID: " + r.getId() +
                        " | Raça: " + r.getNomeRaca() +
                        " | Tipo: " + r.getTipoAnimal() +
                        "\n"
                );
            }

            fw.close();

            System.out.println("Relatório de raças gerado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gerarAnimais() {

        try {

            ArrayList<Animal> lista =
                    new AnimalDAO().consultar();

            FileWriter fw =
                    new FileWriter("relatorio_animais.txt");

            for (Animal a : lista) {

                fw.write(
                        "ID: " + a.getId() +
                        " | Nome: " + a.getNome() +
                        " | Sexo: " + a.getSexo() +
                        " | Cliente: " + a.getIdCliente() +
                        " | Raça: " + a.getIdRaca() +
                        "\n"
                );
            }

            fw.close();

            System.out.println("Relatório de animais gerado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}