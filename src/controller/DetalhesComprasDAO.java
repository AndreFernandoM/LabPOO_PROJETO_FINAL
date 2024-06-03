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
 * @author Magi
 */
public class DetalhesComprasDAO {
    
    private Connection con;
    private PreparedStatement cmd;

    public DetalhesComprasDAO() {
        this.con = Conexao.conectar();
    }
    
    public Map<Integer, Map<Timestamp, List<Integer>>> getProdutosCompradosNaMesmaHora() {
    Map<Integer, Map<Timestamp, List<Integer>>> resultado = new HashMap<>();

    try {
        String SQL = "SELECT id_usuario, DATE_TRUNC('minute', data_venda) AS hora_venda, ARRAY_AGG(id_produto) AS produtos_comprados "
                + "FROM tb_vendas "
                + "GROUP BY id_usuario, DATE_TRUNC('minute', data_venda) "
                + "ORDER BY id_usuario, hora_venda";

        cmd = con.prepareStatement(SQL);
        ResultSet rs = cmd.executeQuery();

        while (rs.next()) {
            int idUsuario = rs.getInt("id_usuario");
            Timestamp horaVenda = rs.getTimestamp("hora_venda");
            Integer[] produtosCompradosArray = (Integer[]) rs.getArray("produtos_comprados").getArray();
            List<Integer> produtosComprados = Arrays.asList(produtosCompradosArray);

            if (!resultado.containsKey(idUsuario)) {
                resultado.put(idUsuario, new HashMap<>());
            }

            if (!resultado.get(idUsuario).containsKey(horaVenda)) {
                resultado.get(idUsuario).put(horaVenda, new ArrayList<>());
            }

            resultado.get(idUsuario).get(horaVenda).addAll(produtosComprados);
        }
    } catch (Exception e) {
        System.err.println("ERRO: " + e.getMessage());
    }

    return resultado;
}
}
