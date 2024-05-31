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

    public boolean getRole(Usuario u) {
        try {
            String SQL = "SELECT EXISTS (SELECT 1 FROM tb_usuario WHERE email=? AND role=TRUE)";
            cmd = con.prepareStatement(SQL);
            cmd.setString(1, u.getEmail());

            ResultSet rs = cmd.executeQuery();

            if (rs.next()) {
                return rs.getBoolean(1);
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
            String SQL = "select * from tb_usuario where email like ?";
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
        } finally {
            Conexao.desconectar(con);
        }
    }

    public boolean checkEmailDuplicado(String email) {
        try {
            String SQL = "select * from tb_usuario where email = ?";
            cmd = con.prepareStatement(SQL);
            cmd.setString(1, email);

            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.print("erro: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public List<Usuario> getUsuarios() {
        try {
            String SQL = "select * from tb_usuario";
            cmd = con.prepareStatement(SQL);

            ResultSet rs = cmd.executeQuery();

            List<Usuario> lista = new ArrayList<>();

            while (rs.next()) {
                lista.add(
                        new Usuario(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("senha"),
                                rs.getBoolean("role")));
            }
            return lista;
        } catch (Exception e) {
            System.err.print("erro: " + e.getMessage());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public boolean invertRole(int id) {
        try {
            String SQL = "UPDATE tb_usuario SET role = NOT role WHERE id = ?";
            cmd = con.prepareStatement(SQL);
            cmd.setInt(1, id);

            int rowsAffected = cmd.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.print("Erro: " + e.getMessage());
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }

}
