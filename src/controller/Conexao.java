package controller;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/PROJETO_FINAL";
    private static final String USR = "postgres";
    private static final String PWD = "a5b978689d9b428da112e2dc3d0ffed9";
    private static final String DRIVER = "org.postgresql.Driver";

    public static Connection conectar() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USR, PWD);
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public static void desconectar(Connection con) {
        try {
            if(con != null){
                con.close();
            }
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection con = Conexao.conectar();
        if (con != null) {
            System.out.println("Conexao realizada com sucesso!");
        }
        
        Conexao.desconectar(con);
    }
}
