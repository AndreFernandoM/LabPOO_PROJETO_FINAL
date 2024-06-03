/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.security.Timestamp;
import java.util.List;
//{2={2024-06-03 17:00:00.0=[45, 28, 27, 26, 44]}, 11={2024-06-03 17:00:00.0=[28, 27, 44]}}
/**
 *
 * @author Andre Fernando Machado - 837864
 */


public class DetalhesCompras {
    private int idUsuario;
    private List<Integer> idsProdutosComprados;
    private Timestamp data;

    public DetalhesCompras() {
    }

    public DetalhesCompras(int idUsuario, List<Integer> idsProdutosComprados, Timestamp data) {
        this.idUsuario = idUsuario;
        this.idsProdutosComprados = idsProdutosComprados;
        this.data = data;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Integer> getIdsProdutosComprados() {
        return idsProdutosComprados;
    }

    public void setIdsProdutosComprados(List<Integer> idsProdutosComprados) {
        this.idsProdutosComprados = idsProdutosComprados;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DetalhesCompras{" +
                "idUsuario=" + idUsuario +
                ", idsProdutosComprados=" + idsProdutosComprados +
                ", data=" + data +
                '}';
    }
}
