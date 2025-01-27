package p02_SistemaNotas;

import javax.swing.*;
import java.util.ArrayList;

import static p02_SistemaNotas.Exibir.mensagem;

public class SistemaNotas {
    public static void main(String[] args) {
        ArrayList<DadosAluno> dadosAlunos = new ArrayList<>();

        int op;

        do {
           op = Exibir.menu();
            switch (op) {
                case 1 -> cadastrarAluno(dadosAlunos);
                case 2 -> calcularMediaAlunosESituacao(dadosAlunos);
                case 3 -> percentualMulheresHomensReprovados(dadosAlunos);
                case 4 -> listarIdades_MediaDeIdades(dadosAlunos);
                case 5 -> listarPorSexo(dadosAlunos);
                case 0 -> mensagem("Encerando!");
                default -> mensagem("Opção inválida!");
            }
        } while (op != 0);
    }

// CADASTRO ==========================

    private static void cadastrarAluno(ArrayList<DadosAluno> dadosAlunos) {
        String nome;
        int idade = 0;
        char sexo = ' ';
        double[] notas = new double[3];
// NOME
        do {
            nome = JOptionPane.showInputDialog("Nome do aluno:");
            if (nome == null || nome.trim().isEmpty()) {
                mensagem("Erro: Nome é obrigatório no cadastro!");
            }
        } while (nome == null || nome.trim().isEmpty());

// IDADE
        boolean entradaidade = false;
        while (!entradaidade) {
            try {
                String idadeDoALuno = JOptionPane.showInputDialog("Idade do Aluno: ");
                if (idadeDoALuno == null || idadeDoALuno.isEmpty()) {
                    mensagem("Erro: Idade é obrigatório no cadastro!");
                    return;
                }
                int idadeAluno = Integer.parseInt(idadeDoALuno);
                if (idadeAluno >= 17 && idadeAluno < 100) {
                    idade = idadeAluno;
                    entradaidade = true;
                } else {
                    mensagem("Erro: Idade fora dos padrões permitidos para entrada no cadastro");
                }
            } catch (NumberFormatException e) {
                mensagem("Erro: Entrada inválida! Por favor, insira uma idade válida!");
            }
        }

// SEXO

        boolean entradaSexo = false;
        while (!entradaSexo) {
            try {
                String sexoAluno = JOptionPane.showInputDialog("Sexo (M/F):");
                char sexoChar = sexoAluno.toUpperCase().charAt(0);
                if (sexoChar == 'M' || sexoChar == 'F') {
                    sexo = sexoChar;
                    entradaSexo = true;

                } else {
                    mensagem("Erro: Entrada inválida! Por favor, insira o sexo (M = Masculino / F = Feminino)");
                }
            } catch (RuntimeException e) {
                mensagem("Erro: Entrada inválida! Por favor, insira o sexo (M = Masculino / F = Feminino)");
            }
        }

// Notas
        for (int i = 0; i < 3; i++) {
            boolean notaValida = false;
            while (!notaValida) {
                try {
                    String notaEntrada = JOptionPane.showInputDialog("Digite a " + (i + 1) + "ª nota do aluno " + nome + ":");
                    double nota = Double.parseDouble(notaEntrada);
                    if (nota >= 0 && nota <= 10) {
                        notas[i] = nota;
                        notaValida = true;
                    } else {
                        mensagem("Erro: Entrada inválida! Por favor a nota deve ser entre 0 e 10.");
                    }
                } catch (NumberFormatException e) {
                    mensagem("Erro: Entrada inválida! Por favor, insira um número entre 0 e 10.");
                }
            }
        }

        DadosAluno novoAluno = new DadosAluno(nome, idade, sexo, notas);
        dadosAlunos.add(novoAluno);
        mensagem("Aluno cadastrado!");
    }

// PARTE LÓGICA DO PROGRAMA ==========

    private static void calcularMediaAlunosESituacao(ArrayList<DadosAluno> dadosAlunos) {
        if (dadosAlunos.isEmpty()) {
            mensagem("Nenhum aluno cadastrado");
            return;
        }

        double somaDasNotas = 0;

        for (DadosAluno alunos : dadosAlunos) {
            somaDasNotas += alunos.calcularMedia();
        }
        double mediaGeral = somaDasNotas / dadosAlunos.size();

        StringBuilder sb = new StringBuilder("Médias individuais dos alunos: \n");

        for (DadosAluno aluno : dadosAlunos) {
            double media = aluno.calcularMedia();
            sb.append("Aluno: ").append(aluno.getNome())
                    .append(" - Média: ").append(String.format("%.2f", media));
            if (media <= 3) {
                sb.append(" - Reprovado(a)\n");
            } else if (media > 3 && media < 7) {
                sb.append(" - Recuperação\n");
            } else {
                sb.append(" - Aprovado (a)\n");
            }
        }

        sb.append("\nMédia geral dos alunos: ").append(String.format("%.2f", mediaGeral));
        mensagem(sb.toString());
    }


    private static void percentualMulheresHomensReprovados(ArrayList<DadosAluno> dadosAlunos) {
        if (dadosAlunos.isEmpty()) {
            mensagem("Nenhum aluno cadastrado");
            return;
        }

        int totalMasc = 0, totalFem = 0;
        int mascReprovado = 0, femReprovado = 0;

        for (DadosAluno aluno : dadosAlunos) {
            char sexo = Character.toUpperCase(aluno.getSexo());
            double media = aluno.calcularMedia();

            if (sexo == 'M') {
                totalMasc++;
                if (media <= 3) {
                    mascReprovado++;
                }
            } else if (sexo == 'F') {
                totalFem++;
                if (media <= 3) {
                    femReprovado++;
                }
            }
        }

        double porcentagemHomensReprovados = (totalMasc > 0) ? ((double) mascReprovado * 100 / totalMasc) : 0;
        double porcentagemFemininaReprovadas = (totalFem > 0) ? ((double) femReprovado * 100 / totalFem) : 0;

        String sb = "Percentual de reprovados por sexo:\n\n" +
                "Homens: " + String.format("%.2f", porcentagemHomensReprovados) + "\n" +
                "Mulheres: " + String.format("%.2f", porcentagemFemininaReprovadas);
        mensagem(sb);

    }

    private static void listarIdades_MediaDeIdades(ArrayList<DadosAluno> dadosAlunos) {
        if (dadosAlunos.isEmpty()) {
            mensagem("Nenhum aluno cadastrado");
        }

        int somaIdades = 0;

        StringBuilder sb = new StringBuilder("Lista de alunos:\n");

        for (DadosAluno aluno : dadosAlunos) {
            somaIdades += aluno.getIdade();
            sb.append("Nome: ").append(aluno.getNome()).append(" - Idade: ").append(aluno.getIdade()).append("\n");
        }

        double mediaIdades = (double) somaIdades / dadosAlunos.size();

        sb.append("\nMédia geral de idade dos alunos: ").append(String.format("%.2f", mediaIdades));

        mensagem(sb.toString());
    }

    private static void listarPorSexo(ArrayList<DadosAluno> dadosAlunos) {
        if (dadosAlunos.isEmpty()) {
            mensagem("Nenhum aluno cadastrado");
            return;
        }

        StringBuilder sexoMasc = new StringBuilder("Alunos do sexo masculino\n");
        StringBuilder sexoFem = new StringBuilder("Alunos do sexo feminino\n");

        for (DadosAluno aluno : dadosAlunos) {
            char sexo = Character.toUpperCase(aluno.getSexo());

            if (sexo == 'M') {
                sexoMasc.append("→ ").append(aluno.getNome()).append("\n");
            } else {
                sexoFem.append("→ ").append(aluno.getNome()).append("\n");
            }
        }

        mensagem(sexoMasc + "\n" + sexoFem);


    }

}

/*

Faça um programa que cadastre os alunos da disciplina de Algoritmos II.
Cada aluno tem nome, idade, sexo, e tres notas.

O programa deve ter o seguinte menu:
    1 - Cadastrar aluno (Cada aluno tem nome, idade, sexo, e tres notas.) -- ok
    2 - Calcular a média de todos os alunos e situação deles -- ok
    3 - Percentual de mulheres e homens reprovados -- ok
    4 - Listar alunos por idade e media de idades
    5 - Listar alunos por sexo


Validações:
    Nome obrigatório
    Idade - entre 17 e 100
    sexo - M ou F
    Notas - double entre 0 e 10

        **** UTILIZAR O MÁXIMO DE PROCEDIMENTOS E FUNÇÕES
 */