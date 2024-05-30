/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class Produto {

    private int id;
    private int quant;
    private boolean disponivel;
    private double preco;
    private String nome;
    private String descricao;

    public Produto(int quant, boolean disponivel, double preco, String nome, String descricao) {

        this.quant = quant;
        this.disponivel = disponivel;
        this.preco = preco;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public int getQuant() {
        return quant;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return getNome();
    }

}
