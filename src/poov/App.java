package poov;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Operacao operacao = new Operacao();
        Scanner s = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("MENU PRINCIPAL");
            System.out.println("1 - Doador");
            System.out.println("2 - Doação");
            System.out.println("3 - Sair");
            System.out.print("Digite uma opção: ");
            opcao = s.nextInt();

            switch (opcao) {
                case 1:
                    operacao.abaDoador();
                    break;
                case 2:
                    operacao.abaDoacao();
                    break;
                case 3:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Digite um dos valores indicados anteriormente: ");
            }
        }while (opcao != 3);
        s.close();
    }
}
