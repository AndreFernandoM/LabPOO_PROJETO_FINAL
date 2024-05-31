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

    public boolean adicionarProdutoAoCarrinho(int idProduto, int quantidade) {
        int idUsuario = SessaoUsuario.getInstance().getUsuarioLogado().getId();
        
        try {
            String sqlProduto = "SELECT preco FROM tb_produto WHERE id = ?";
            cmd = con.prepareStatement(sqlProduto);
            cmd.setInt(1, idProduto);
            ResultSet rs = cmd.executeQuery();
            
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
        } catch (Exception e) {
            System.err.println("Erro ao adicionar produto ao carrinho: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public List<Produto> getCarrinhoPorUsuario() {
        int idUsuario = SessaoUsuario.getInstance().getUsuarioLogado().getId();

        try {
            String SQL = "SELECT p.id, p.quant, p.disponivel, p.preco, p.nome, p.descricao, c.quantidade " +
                         "FROM tb_carrinho c " +
                         "JOIN tb_produto p ON c.id_produto = p.id " +
                         "WHERE c.id_usuario = ?";
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
        } finally {
            Conexao.desconectar(con);
        }
    }
}
