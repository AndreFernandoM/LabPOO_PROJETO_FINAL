/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Vendas;
import model.Produto;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import model.SessaoUsuario;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class VendasDAO {

    private Connection con;
    private PreparedStatement cmd;

    public VendasDAO() {
        this.con = Conexao.conectar();
    }

    public boolean criarVenda() {
        List<Produto> lista = new CarrinhoDAO().getCarrinhoPorUsuario();

        try {
            for (Produto p : lista) {
                String SQL = "INSERT INTO tb_vendas (id_usuario, id_produto, quantidade, total) VALUES (?, ?, ?, ?)";

                cmd = con.prepareStatement(SQL);
                cmd.setInt(1, SessaoUsuario.getInstance().getUsuarioLogado().getId());
                cmd.setInt(2, p.getId());
                cmd.setInt(3, p.getQuantidade());
                cmd.setDouble(4, p.getPreco() * p.getQuantidade());

                if (cmd.executeUpdate() <= 0) {
                    con.rollback();
                    return false;
                }
                
                new ProdutoDAO().diminuirQuantidade(p.getId(), p.getQuantidade());
                
                new CarrinhoDAO().deletarProdutoCarrinho(p.getId());
            }

            return true;
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            try {
                con.rollback();
            } catch (Exception rollbackEx) {
                System.err.println("ERRO no rollback: " + rollbackEx.getMessage());
            }
            return false;
        }
    }

    public Vendas buscarVendaPorId(int id) {
        try {
            String SQL = "SELECT * FROM tb_vendas WHERE id = ?";
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, id);

            ResultSet rs = cmd.executeQuery();

            if (rs.next()) {
                return new Vendas(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("total"),
                        rs.getTimestamp("data_venda")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return null;
        }
    }
// VendasDAO
public double getVendasValor(int idUsuario) {
    double total = 0.0;
    try {
        String SQL = "SELECT SUM(total) AS valor_total FROM tb_vendas WHERE id_usuario = ?";
        cmd = con.prepareStatement(SQL);
        cmd.setInt(1, idUsuario);
        ResultSet rs = cmd.executeQuery();

        if (rs.next()) {
            total = rs.getDouble("valor_total");
        }
        return total;
    } catch (Exception e) {
        System.err.println("ERRO: " + e.getMessage());
        return total;
    }
}

    
    
    
//    public double getVendasValor(int id) {
//        double total = 00;
//        try {
//            String SQL = "SELECT total FROM tb_vendas where id_usuario=?";
//
//            cmd = con.prepareStatement(SQL);
//            cmd.setInt(1, id);
//            ResultSet rs = cmd.executeQuery();
//
//            while (rs.next()) {
//                total += rs.getDouble("total");
//            };
//
//            return total;
//        } catch (Exception e) {
//            System.err.println("ERRO: " + e.getMessage());
//            return total;
//        }
//    }

    public List<Integer> getVendasId() {
        try {
            String SQL = "SELECT id_usuario  FROM tb_vendas";
            cmd = con.prepareStatement(SQL);

            ResultSet rs = cmd.executeQuery();

            List<Integer> lista = new ArrayList<Integer>();

            while (rs.next()) {
                if (!lista.contains(rs.getInt("id_usuario"))) {
                    lista.add(rs.getInt("id_usuario"));
                }

            }
            return lista;
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return null;
        }

    }

    public List<Vendas> getVendas() {
        try {
            String SQL = "SELECT * FROM tb_vendas";
            cmd = con.prepareStatement(SQL);

            ResultSet rs = cmd.executeQuery();

            List<Vendas> lista = new ArrayList<>();

            while (rs.next()) {
                lista.add(
                        new Vendas(
                                rs.getInt("id"),
                                rs.getInt("id_usuario"),
                                rs.getInt("id_produto"),
                                rs.getInt("quantidade"),
                                rs.getDouble("total"),
                                rs.getTimestamp("data_venda")
                        )
                );
            }
            return lista;
        } catch (Exception e) {
            System.err.print("ERRO: " + e.getMessage());
            return null;
        }
    }

    
}
