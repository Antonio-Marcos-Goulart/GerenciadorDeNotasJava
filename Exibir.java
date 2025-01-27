package p02_SistemaNotas;

import javax.swing.*;

public class Exibir {
    public static int menu() {
        String msgMenu = """
                1 - Cadastrar aluno
                2 - Calcular a média e situação
                3 - Percentual de reprovados por sexo
                4 - Listar idades e média de idades
                5 - Listar por sexo

                0 - Sair
                """;
        try {
            String entrada = JOptionPane.showInputDialog(msgMenu);
            return (entrada != null && !entrada.trim().isEmpty()) ? Integer.parseInt(entrada) : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void mensagem(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
}
