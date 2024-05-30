/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class Usuario {
    
    private int id;
    private String email;
    private String senha;
    private boolean role = false;

    public Usuario(String email, String senha,boolean role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean isRole() {
        return role;
    }

    public Usuario(int id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }
     
    public Usuario() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }



    @Override
    public String toString() {
        return getEmail();
    }

    
}
