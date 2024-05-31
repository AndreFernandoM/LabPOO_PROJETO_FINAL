/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class Carrinho {

    private List<Produto> listaProdutos;

    public Carrinho() {
        this.listaProdutos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        listaProdutos.add(produto);
    }

    public void removerProduto(Produto produto) {
        listaProdutos.remove(produto);
    }

    public void limparCarrinho() {
        listaProdutos.clear();
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }
}
