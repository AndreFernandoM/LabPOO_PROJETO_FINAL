/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import model.Usuario;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class SessaoUsuario {
    private static SessaoUsuario instancia;
    private Usuario usuarioLogado;

    private static final Usuario USUARIO_TESTE = new Usuario("teste", "teste", false);

    private SessaoUsuario() {}

    public static SessaoUsuario getInstance() {
        if (instancia == null) {
            instancia = new SessaoUsuario();
        }
        return instancia;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuarioLogado() {
        if (usuarioLogado == null) {
            return USUARIO_TESTE;
        }
        return usuarioLogado;
    }

    // Método para limpar a sessão
    public void limparSessao() {
        usuarioLogado = null;
    }
}
