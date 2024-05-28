/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Usuario;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class UsuarioDAO {
    
        private Connection con;
    private PreparedStatement cmd;

    public UsuarioDAO() {
        this.con = Conexao.conectar();
    }

    //    
    //    Login: user auth 
    //    botao entrar
    public boolean login(Usuario u) {
        try {
            String SQL = "select * from tb_usuario where email=? and senha=MD5(?)";
            cmd = con.prepareStatement(SQL);
            cmd.setString(1, u.getEmail());
            cmd.setString(2, u.getSenha());

            ResultSet rs = cmd.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }
    
//
    // criarUsuario: Insere um novo usuário na tabela tb_usuarios
    //
    public boolean criarUsuario(Usuario u) {
        try {
            String SQL = "insert into tb_usuario(email,senha) values (?, MD5(?));";
            cmd = con.prepareStatement(SQL);
            cmd.setString(1, u.getEmail());
            cmd.setString(2, u.getSenha());

            return cmd.executeUpdate() > 0 ? true : false;
           
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }
    
    public List<Usuario> pesquisarPorEmail(String email) {
        try {
            String SQL = "select * from tb_usutario where email like ?";
            cmd = con.prepareStatement(SQL);
            cmd.setString(1, '%' + email + '%');

            ResultSet rs = cmd.executeQuery();

            List<Usuario> lista = new ArrayList();

            while (rs.next()) {
                lista.add(
                        new Usuario(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("senha")));
            }
            return lista;
        } catch (Exception e) {
            System.err.print("erro: " + e.getMessage());
            return null;
        }
    }
    
    
    
    
}
