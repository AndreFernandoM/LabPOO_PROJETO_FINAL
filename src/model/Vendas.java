/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Andre Fernando Machado - 837864
 */

    // TODO: LISTA DE VENDAS (ID_VENDA -> ID_USUARIO -> ID_PRODUTOS -> DATA_COMPRA ->COMPRA_TOTAL )
public class Vendas {

    private int id;
    private int idUsuario;
    private int idProduto;
    private int quantidade;
    private double total;
    private Timestamp dataVenda;

    public Vendas(int idUsuario, int idProduto, int quantidade, double total, Timestamp dataVenda) {
        this.idUsuario = idUsuario;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.total = total;
        this.dataVenda = dataVenda;
    }

    public Vendas(int id, int idUsuario, int idProduto, int quantidade, double total, Timestamp dataVenda) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.total = total;
        this.dataVenda = dataVenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Timestamp getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Timestamp dataVenda) {
        this.dataVenda = dataVenda;
    }

    @Override
    public String toString() {
        return "Venda: [ID: " + id + ", ID Usuario: " + idUsuario + ", ID Produto: " + idProduto
                + ", Quantidade: " + quantidade + ", Total: " + total + ", Data da Venda: " + dataVenda + "]";
    }
}
