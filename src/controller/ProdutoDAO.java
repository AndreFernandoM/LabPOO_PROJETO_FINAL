/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdutoDAO {

    private Connection con;
    private PreparedStatement cmd;

    public ProdutoDAO() {
        this.con = Conexao.conectar();
    }

    public boolean criarProduto(Produto produto) {
        try {
            String SQL = "INSERT INTO tb_produto (quant, disponivel, preco, nome, descricao) VALUES (?, ?, ?, ?, ?)";
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, produto.getQuantidade());
            cmd.setBoolean(2, produto.isDisponivel());
            cmd.setDouble(3, produto.getPreco());
            cmd.setString(4, produto.getNome());
            cmd.setString(5, produto.getDescricao());

            int rowsAffected = cmd.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erro ao criar produto: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public List<Produto> getProdutos() {
        try {
            String SQL = "select * from tb_produto";
            cmd = con.prepareStatement(SQL);

            ResultSet rs = cmd.executeQuery();

            List<Produto> lista = new ArrayList<>();

            while (rs.next()) {
                lista.add(
                        new Produto(
                                rs.getInt("id"),
                                rs.getInt("quant"),
                                rs.getBoolean("disponivel"),
                                rs.getDouble("preco"),
                                rs.getString("nome"),
                                rs.getString("descricao")));
            }
            return lista;
        } catch (Exception e) {
            System.err.print("erro: " + e.getMessage());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public boolean editarProduto(int id, Produto produto) {
        try {
            String SQL = "UPDATE tb_produto SET quant=?, disponivel=?, preco=?, nome=?, descricao=? WHERE id=?";
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, produto.getQuantidade());
            cmd.setBoolean(2, produto.isDisponivel());
            cmd.setDouble(3, produto.getPreco());
            cmd.setString(4, produto.getNome());
            cmd.setString(5, produto.getDescricao());
            cmd.setInt(6, id);

            int rowsAffected = cmd.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erro ao editar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean deletProduto(int id) {
        try {
            String checkSQL = "SELECT COUNT(*) FROM tb_carrinho WHERE id_produto = ?";
            cmd = con.prepareStatement(checkSQL);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                String deleteCarrinhoSQL = "DELETE FROM tb_carrinho WHERE id_produto=?";
                PreparedStatement cmd2 = con.prepareStatement(deleteCarrinhoSQL);
                cmd2.setInt(1, id);
                cmd2.executeUpdate();
                cmd2.close();
            }

            String deleteProdutoSQL = "DELETE FROM tb_produto WHERE id=?";
            PreparedStatement cmd3 = con.prepareStatement(deleteProdutoSQL);
            cmd3.setInt(1, id);
            int rowsAffected = cmd3.executeUpdate();
            cmd3.close();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }
}
