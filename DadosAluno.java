package p02_SistemaNotas;

public class DadosAluno {
    private final String nome;
    private final int idade;
    private final char sexo;
    private final double[] notas;

    public DadosAluno(String nome, int idade, char sexo, double[] notas) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.notas = notas;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public char getSexo() {
        return sexo;
    }

    public double calcularMedia() {
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }
}
