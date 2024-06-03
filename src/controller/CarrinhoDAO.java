/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.SessaoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class CarrinhoDAO {

    private Connection con;
    private PreparedStatement cmd;

    public CarrinhoDAO() {
        this.con = Conexao.conectar();
    }
    
    
    public List<Produto> getCarrinhoPorUsuario() {
        int idUsuario = SessaoUsuario.getInstance().getUsuarioLogado().getId();

        try {
            String SQL = "SELECT p.id, p.quant, p.disponivel, p.preco, p.nome, p.descricao, c.quantidade "
                    + "FROM tb_carrinho c "
                    + "JOIN tb_produto p ON c.id_produto = p.id "
                    + "WHERE c.id_usuario = ?";
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, idUsuario);

            ResultSet rs = cmd.executeQuery();

            List<Produto> lista = new ArrayList<>();

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("disponivel"),
                        rs.getDouble("preco"),
                        rs.getString("nome"),
                        rs.getString("descricao"));
                lista.add(produto);
            }
            return lista;
        } catch (Exception e) {
            System.err.println("Erro ao obter carrinho: " + e.getMessage());
            return null;
        }
    }



    public boolean adicionarProdutoAoCarrinho(int idProduto, int quantidade) {
    int idUsuario = SessaoUsuario.getInstance().getUsuarioLogado().getId();

    try {
        String checkSQL = "SELECT quantidade FROM tb_carrinho WHERE id_produto = ? AND id_usuario = ?";
        cmd = con.prepareStatement(checkSQL);
        cmd.setInt(1, idProduto);
        cmd.setInt(2, idUsuario);
        ResultSet rs = cmd.executeQuery();

        if (rs.next()) {
            int quantidadeAtual = rs.getInt("quantidade");
            int novaQuantidade = quantidadeAtual + quantidade;

            String updateSQL = "UPDATE tb_carrinho SET quantidade = ?, total = ? WHERE id_produto = ? AND id_usuario = ?";
            cmd = con.prepareStatement(updateSQL);
            cmd.setInt(1, novaQuantidade);

            String sqlProduto = "SELECT preco FROM tb_produto WHERE id = ?";
            PreparedStatement cmdProduto = con.prepareStatement(sqlProduto);
            cmdProduto.setInt(1, idProduto);
            ResultSet rsProduto = cmdProduto.executeQuery();
            if (rsProduto.next()) {
                double preco = rsProduto.getDouble("preco");
                double total = preco * novaQuantidade;
                cmd.setDouble(2, total);
            } else {
                return false;
            }

            cmd.setInt(3, idProduto);
            cmd.setInt(4, idUsuario);

            return cmd.executeUpdate() > 0;
        } else {
            String sqlProduto = "SELECT preco FROM tb_produto WHERE id = ?";
            cmd = con.prepareStatement(sqlProduto);
            cmd.setInt(1, idProduto);
            rs = cmd.executeQuery();

            if (rs.next()) {
                double preco = rs.getDouble("preco");
                double total = preco * quantidade;

                String SQL = "INSERT INTO tb_carrinho (id_usuario, id_produto, quantidade, total) VALUES (?, ?, ?, ?)";
                cmd = con.prepareStatement(SQL);
                cmd.setInt(1, idUsuario);
                cmd.setInt(2, idProduto);
                cmd.setInt(3, quantidade);
                cmd.setDouble(4, total);

                return cmd.executeUpdate() > 0;
            } else {
                return false;
            }
        }
    } catch (Exception e) {
        System.err.println("Erro ao adicionar produto ao carrinho: " + e.getMessage());
        return false;
    }
}


    private double obterPrecoProduto(int idProduto) {
        try {
            String sqlProduto = "SELECT preco FROM tb_produto WHERE id = ?";
            cmd = con.prepareStatement(sqlProduto);
            cmd.setInt(1, idProduto);
            ResultSet rs = cmd.executeQuery();

            if (rs.next()) {
                return rs.getDouble("preco");
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter preço do produto: " + e.getMessage());
        }
        return 0;
    }

    private double calcularTotal(int idProduto, int quantidade) {
        double preco = obterPrecoProduto(idProduto);
        return preco * quantidade;
    }

    public boolean deletarProdutoCarrinho(int id) {
        try {
            String SQL = "DELETE FROM tb_carrinho WHERE id_produto=?";
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, id);

            int rowsAffected = cmd.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erro ao deletar produto do carrinho: " + e.getMessage());
            return false;
        }
    }

    public boolean diminuirProdutoCarrinho(int id) {
        try {
            String checkSQL = "SELECT quantidade FROM tb_carrinho WHERE id_produto=?";
            cmd = con.prepareStatement(checkSQL);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();

            if (rs.next()) {
                int quantidadeAtual = rs.getInt("quantidade");

                if (quantidadeAtual > 1) {
                    String updateSQL = "UPDATE tb_carrinho SET quantidade=? WHERE id_produto=?";
                    PreparedStatement cmd2 = con.prepareStatement(updateSQL);
                    cmd2.setInt(1, quantidadeAtual - 1);
                    cmd2.setInt(2, id);

                    int rowsAffected = cmd2.executeUpdate();

                    return rowsAffected > 0;
                } else {
                    return deletarProdutoCarrinho(id);
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erro ao diminuir quantidade do produto no carrinho: " + e.getMessage());
            return false;
        }
    }

}

