package model;

public class Produto {
    private int id;
    private String nome;
    private int quantidade;
    private double valor;

    public Produto(int id, String nome, double valor, int quantidade) {
        setId(id);
        setNome(nome);
        setQuantidade(quantidade);
        setValor(valor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.format("Id: %-6d\tNome: %-20s\tQuantidade: %-6d\tValor: %-7.2f\n", getId(), getNome(), getQuantidade(), getValor());
    }
}
