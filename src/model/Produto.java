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
    private int quantidade;
    private boolean disponivel;
    private double preco;
    private String nome;
    private String descricao;

    public Produto(int quantidade, boolean disponivel, double preco, String nome, String descricao) {
        this.quantidade = quantidade;
        this.disponivel = disponivel;
        this.preco = preco;
        this.nome = nome;
        this.descricao = descricao;
    }
    public Produto(int id, int quantidade, boolean disponivel, double preco, String nome, String descricao) {
        this.id = id;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
        this.preco = preco;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
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
}


