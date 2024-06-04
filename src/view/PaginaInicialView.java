/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ProdutoDAO;
import java.awt.Component;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Produto;
import org.postgresql.util.GettableHashMap;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
public class PaginaInicialView extends javax.swing.JFrame {

    /**
     * Creates new form PaginaInicialView
     */
    public PaginaInicialView() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Petshop SillyPet");
        configColumn("");

    }

    private void configColumn(String stg) {
        preencherTabela(stg);
        tabProdutos.setRowHeight(90);
        tabProdutos.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
        tabProdutos.getColumnModel().getColumn(0).setPreferredWidth(50);

        tabProdutos.getColumnModel().getColumn(1).setResizable(false);
        tabProdutos.getColumnModel().getColumn(1).setPreferredWidth(50);

        tabProdutos.getColumnModel().getColumn(2).setResizable(false);
        tabProdutos.getColumnModel().getColumn(2).setPreferredWidth(250);
        tabProdutos.getColumnModel().getColumn(3).setPreferredWidth(25);
    }

    private void configurarTabela() {
        DefaultTableModel m = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        m.addColumn("Produto");
        m.addColumn("Nome");
        m.addColumn("Descrição");
        m.addColumn("Preço");

        tabProdutos.setModel(m);
    }

//    private void preencherTabela(String nome) {
//        configurarTabela();
//
//        DefaultTableModel m = (DefaultTableModel) tabProdutos.getModel();
//
//        ProdutoDAO produtoDAO = new ProdutoDAO();
//        List<Produto> produtosAmostra = produtoDAO.getProdutos();
//
//        if (nome.isBlank()) {
//            for (Produto p : produtosAmostra) {
//                if (p.isDisponivel()) {
//                    m.addRow(new Object[]{
//                        p.getNome(),
//                        p.getNome(),
//                        p.getDescricao(),
//                        "R$ " + p.getPreco()
//                    });
//                }
//            }
//
//        } else {
//            for (Produto p : produtosAmostra) {
//                if (p.isDisponivel() && p.getNome().equalsIgnoreCase(nome)) {
//                    m.addRow(new Object[]{
//                        p.getNome(), 
//                        p.getNome(),
//                        p.getDescricao(),
//                        "R$ "+p.getPreco()
//                    });
//                    break;
//                }
//            }
//        }
//
//        tabProdutos.setModel(m);
//    }
    private void preencherTabela(String nome) {
        configurarTabela();

        DefaultTableModel m = (DefaultTableModel) tabProdutos.getModel();

        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtosAmostra = produtoDAO.getProdutos();

        String searchTerm = nome.toLowerCase();

        for (Produto p : produtosAmostra) {
            if (p.isDisponivel()) {
                String produtoNome = p.getNome().toLowerCase();
                if (produtoNome.contains(searchTerm)) {
                    m.addRow(new Object[]{
                        p.getNome(), 
                        p.getNome(),
                        p.getDescricao(),
                        String.format("R$ %.2f", p.getPreco())
                    });
                }
            }
        }

        tabProdutos.setModel(m);
    }

    private class ImageRender extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String fotoNome = value.toString();
            ImageIcon imageIcon = new ImageIcon(
                    new ImageIcon("src/images/" + fotoNome + ".jpg").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT));
            return new JLabel(imageIcon);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCarrinho = new javax.swing.JButton();
        btn_Sair = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabProdutos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnSobre = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo3 (Personalizado) (2).png"))); // NOI18N

        txtPesquisar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarActionPerformed(evt);
            }
        });
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Busca de Produtos");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Carrinho");

        btnCarrinho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shopping-cart-empty-side-view.png"))); // NOI18N
        btnCarrinho.setText("Compras");
        btnCarrinho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarrinhoActionPerformed(evt);
            }
        });

        btn_Sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        btn_Sair.setText("SAIR");
        btn_Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SairActionPerformed(evt);
            }
        });

        tabProdutos.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tabProdutos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabProdutos.setComponentPopupMenu(btnSobre.getComponentPopupMenu());
        tabProdutos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabProdutos.setRowHeight(55);
        tabProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabProdutos);
        if (tabProdutos.getColumnModel().getColumnCount() > 0) {
            tabProdutos.getColumnModel().getColumn(0).setResizable(false);
            tabProdutos.getColumnModel().getColumn(1).setResizable(false);
            tabProdutos.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Novos Produtos:");

        btnSobre.setText("Sobre");
        btnSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSobreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(679, 679, 679)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(162, 162, 162)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCarrinho)
                                    .addComponent(jLabel3)))
                            .addComponent(txtPesquisar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Sair)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSobre)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Sair)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCarrinho)))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnSobre)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarActionPerformed
        String textoPesquisa = txtPesquisar.getText();
        configColumn(textoPesquisa);
    }//GEN-LAST:event_txtPesquisarActionPerformed

    private void btn_SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SairActionPerformed
        dispose();
        LoginView f = new LoginView();
        f.setVisible(true);
    }//GEN-LAST:event_btn_SairActionPerformed

    private void btnCarrinhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarrinhoActionPerformed
        dispose();
        CarrinhoView f = new CarrinhoView();
        f.setVisible(true);
    }//GEN-LAST:event_btnCarrinhoActionPerformed

    private void btnSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSobreActionPerformed
        dispose();
        SobreView s = new SobreView();
        s.setVisible(true);
    }//GEN-LAST:event_btnSobreActionPerformed

    private void tabProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProdutosMouseClicked
        int index = tabProdutos.getSelectedRow();
        TableModel p = tabProdutos.getModel();

        int id = new ProdutoDAO().getProdutoIdByNome(p.getValueAt(index, 0).toString());

        ProdutoView addCarrinho = new ProdutoView(id);
        addCarrinho.setVisible(true);
    }//GEN-LAST:event_tabProdutosMouseClicked

    private void txtPesquisarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyTyped
    }//GEN-LAST:event_txtPesquisarKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaginaInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaginaInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaginaInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaInicialView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarrinho;
    private javax.swing.JButton btnSobre;
    private javax.swing.JButton btn_Sair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabProdutos;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
