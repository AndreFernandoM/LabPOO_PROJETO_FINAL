/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import components.NewTableActionCellEditor;
import components.NewTableActionCellRender;
import components.NewTableActionEvent;

import controller.ProdutoDAO;
import controller.UsuarioDAO;
import controller.VendasDAO;
import controller.DetalhesComprasDAO;
import java.awt.Color;

import model.Usuario;
import model.DetalhesCompras;

import model.Produto;
import model.Vendas;

import java.util.HashMap;
import java.sql.Timestamp;
import java.util.Map;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Andre Fernando Machado - 837864
 */
//TODO: apagar e editar produto;
//      pesquisar usuario/produto por nome e checkbox de role/disponivel para JOIN 
public class AdmInicialView extends javax.swing.JFrame {

    int idVenda = 1;
    double total = 0;
    double faturamento = 0;

    /**
     * Creates new form AdmInicialView
     */
    public AdmInicialView() {
        initComponents();

        configColumnUsers();
        configColumnProds();
        configColumnVendas();

        setResizable(false);
        setTitle("Tela de Administrador");
        setLocationRelativeTo(null);

    }

    private void configColumnUsers() {
        NewTableActionEvent event = new NewTableActionEvent() {
            @Override
            public void onEdit(int row) {
            }
        };
        preencherTabelaUsers();
        tabUsuarios.getColumnModel().getColumn(3).setCellEditor(new NewTableActionCellEditor(event));
        tabUsuarios.getColumnModel().getColumn(3).setCellRenderer(new NewTableActionCellRender());
        tabUsuarios.getColumnModel().getColumn(3).setResizable(false);
        
        tabUsuarios.getColumnModel().getColumn(0).setPreferredWidth(5);
        tabUsuarios.getColumnModel().getColumn(0).setResizable(false);
        
        tabUsuarios.getColumnModel().getColumn(2).setResizable(false);
        
        tabUsuarios.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabUsuarios.getColumnModel().getColumn(1).setResizable(false);
    }

    private void configurarTabelaUsers() {
        DefaultTableModel m = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        m.addColumn("ID");
        m.addColumn("Email");
        m.addColumn("Adm");
        m.addColumn("Edit");

        tabUsuarios.setModel(m);

    }

    private void preencherTabelaUsers() {
        configurarTabelaUsers();
        DefaultTableModel m = (DefaultTableModel) tabUsuarios.getModel();

        for (Usuario t : new UsuarioDAO().getUsuarios()) {
            m.addRow(new Object[]{
                t.getId(), t.getEmail(), t.getRole()?"Administrador":"Usuario"
            });
        }
        tabUsuarios.setModel(m);
    }

    private void configurarTabelaProds() {
        DefaultTableModel g = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        g.addColumn("ID");
        g.addColumn("Nome");
        g.addColumn("Preco");
        g.addColumn("Quant");
        g.addColumn("Disponivel");
        g.addColumn("Descricao");

        tabProdutos.setModel(g);

    }

    private void preencherTabelaProds() {
        configurarTabelaProds();
        DefaultTableModel g = (DefaultTableModel) tabProdutos.getModel();

        for (Produto t : new ProdutoDAO().getAllProdutos()) {

            g.addRow(new Object[]{
                t.getId(), t.getNome(), +t.getPreco(), t.getQuantidade(), t.isDisponivel()?"Disponivel":"Indisponivel", t.getDescricao()
            });
        }
        tabProdutos.setModel(g);
    }

    private void configColumnProds() {
        preencherTabelaProds();

        tabProdutos.getColumnModel().getColumn(0).setPreferredWidth(35);
        tabProdutos.getColumnModel().getColumn(0).setResizable(false);

        tabProdutos.getColumnModel().getColumn(1).setPreferredWidth(125);
        tabProdutos.getColumnModel().getColumn(1).setResizable(false);

        tabProdutos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabProdutos.getColumnModel().getColumn(2).setResizable(false);

        tabProdutos.getColumnModel().getColumn(3).setPreferredWidth(65);
        tabProdutos.getColumnModel().getColumn(3).setResizable(false);

        tabProdutos.getColumnModel().getColumn(4).setPreferredWidth(110);
        tabProdutos.getColumnModel().getColumn(4).setResizable(false);
        
        tabProdutos.getColumnModel().getColumn(5).setPreferredWidth(255);
        tabProdutos.getColumnModel().getColumn(5).setResizable(false);
    }

    private void configurarTabelaVendas() {
        DefaultTableModel v = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        v.addColumn("N° Venda");
        v.addColumn("ID Cliente");
        v.addColumn("IDs Produtos");
        v.addColumn("Data");
        v.addColumn("Total");

        tabVendas.setModel(v);
    }

    private void preencherTabelaVendas() {
        configurarTabelaVendas();
        DefaultTableModel v = (DefaultTableModel) tabVendas.getModel();
        DetalhesComprasDAO detalhesComprasDAO = new DetalhesComprasDAO();
        Map<Integer, Map<Timestamp, List<Integer>>> detalhesComprasMap = detalhesComprasDAO.getProdutosCompradosNaMesmaHora();

        for (Map.Entry<Integer, Map<Timestamp, List<Integer>>> entry : detalhesComprasMap.entrySet()) {
            Integer idUsuario = entry.getKey();
            Map<Timestamp, List<Integer>> detalhesPorData = entry.getValue();

            for (Map.Entry<Timestamp, List<Integer>> detalhesData : detalhesPorData.entrySet()) {
                Timestamp dataCompra = detalhesData.getKey();
                List<Integer> idsProdutosComprados = detalhesData.getValue();
                StringBuilder produtosComprados = new StringBuilder();
                double valorTotal = 0.0;

                for (Integer idProduto : idsProdutosComprados) {
                    double precoProduto = new ProdutoDAO().getValorProduto(idProduto);
                    produtosComprados.append(idProduto).append(", ");
                    valorTotal += precoProduto;
                }

                if (produtosComprados.length() > 0) {
                    produtosComprados.setLength(produtosComprados.length() - 2);
                }
                faturamento += valorTotal;

                v.addRow(new Object[]{
                    idVenda,
                    idUsuario,
                    produtosComprados.toString(),
                    dataCompra,
                    "R$ " + String.valueOf(String.format("%.2f", valorTotal))
                });
                idVenda++;
            }
            txtFaturamento.setText(String.valueOf(String.format("%.2f", faturamento)));

        }
    }

    private void configColumnVendas() {

        preencherTabelaVendas();
        NewTableActionEvent event = new NewTableActionEvent() {
            @Override
            public void onEdit(int row) {
            }
        };

        tabVendas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabVendas.getColumnModel().getColumn(1).setPreferredWidth(55);
        tabVendas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabVendas.getColumnModel().getColumn(3).setPreferredWidth(125);
        tabVendas.getColumnModel().getColumn(4).setPreferredWidth(50);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabProdutos = new javax.swing.JTable();
        btnNovoProd = new javax.swing.JButton();
        btnAtualizar2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabUsuarios = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnSair2 = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSair3 = new javax.swing.JButton();
        btnAtualizar3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabVendas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtFaturamento = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo3 (Personalizado) (2).png"))); // NOI18N

        btnSair.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Lista de Produtos Cadastrados");

        tabProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabProdutos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabProdutos.setRowHeight(45);
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
            tabProdutos.getColumnModel().getColumn(3).setResizable(false);
        }

        btnNovoProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus-sign.png"))); // NOI18N
        btnNovoProd.setText("Novo Produto");
        btnNovoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoProdActionPerformed(evt);
            }
        });

        btnAtualizar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btnAtualizar2.setText("Atualizar");
        btnAtualizar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSair)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNovoProd))
                            .addComponent(btnAtualizar2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btnNovoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualizar2)
                        .addGap(4, 4, 4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Produtos", jPanel2);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Lista de Usuarios Cadastrados");

        tabUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabUsuarios.setRowHeight(45);
        tabUsuarios.getTableHeader().setReorderingAllowed(false);
        tabUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabUsuariosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabUsuarios);
        if (tabUsuarios.getColumnModel().getColumnCount() > 0) {
            tabUsuarios.getColumnModel().getColumn(0).setResizable(false);
            tabUsuarios.getColumnModel().getColumn(1).setResizable(false);
            tabUsuarios.getColumnModel().getColumn(2).setResizable(false);
            tabUsuarios.getColumnModel().getColumn(3).setResizable(false);
            tabUsuarios.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo3 (Personalizado) (2).png"))); // NOI18N

        btnSair2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSair2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        btnSair2.setText("Sair");
        btnSair2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair2ActionPerformed(evt);
            }
        });

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(291, 291, 291)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAtualizar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnSair2, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 16, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSair2)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtualizar)
                        .addGap(3, 3, 3)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuarios", jPanel3);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo3 (Personalizado) (2).png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Vendas Realizadas");

        btnSair3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSair3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        btnSair3.setText("Sair");
        btnSair3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair3ActionPerformed(evt);
            }
        });

        btnAtualizar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btnAtualizar3.setText("Atualizar");
        btnAtualizar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizar3ActionPerformed(evt);
            }
        });

        tabVendas.setModel(new javax.swing.table.DefaultTableModel(
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
        tabVendas.setRowHeight(45);
        tabVendas.getTableHeader().setReorderingAllowed(false);
        tabVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabVendasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabVendas);
        if (tabVendas.getColumnModel().getColumnCount() > 0) {
            tabVendas.getColumnModel().getColumn(0).setResizable(false);
            tabVendas.getColumnModel().getColumn(1).setResizable(false);
            tabVendas.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Faturamento:   R$");

        txtFaturamento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(315, 315, 315)
                                .addComponent(btnSair3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAtualizar3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFaturamento)
                                .addGap(31, 31, 31)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnSair3)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(btnAtualizar3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtFaturamento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Vendas", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
        LoginView f = new LoginView();
        f.setVisible(true);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnNovoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoProdActionPerformed

        dispose();
        CadastroProduto f = new CadastroProduto();
        f.setVisible(true);
    }//GEN-LAST:event_btnNovoProdActionPerformed

    private void btnSair2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair2ActionPerformed
        dispose();
        LoginView f = new LoginView();
        f.setVisible(true);
    }//GEN-LAST:event_btnSair2ActionPerformed

    private void tabUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabUsuariosMouseClicked
        int index = tabUsuarios.getSelectedRow();
        TableModel m = tabUsuarios.getModel();

        int id = Integer.parseInt(m.getValueAt(index, 0).toString());

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean sucesso = usuarioDAO.invertRole(id);
        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Role de usuario modificado!");
            return;

        }
    }//GEN-LAST:event_tabUsuariosMouseClicked

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        configColumnUsers();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnAtualizar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizar2ActionPerformed
        configColumnProds();
    }//GEN-LAST:event_btnAtualizar2ActionPerformed

    private void tabProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabProdutosMouseClicked
        int index = tabProdutos.getSelectedRow();
        TableModel p = tabProdutos.getModel();

        int id = Integer.parseInt(p.getValueAt(index, 0).toString());
        int quantidade = Integer.parseInt(p.getValueAt(index, 3).toString());
        double preco = Double.parseDouble(p.getValueAt(index, 2).toString());
        String nome = p.getValueAt(index, 1).toString();
        String descricao = p.getValueAt(index, 5).toString();
        boolean disponibilidade = p.getValueAt(index, 4).toString().equals("Disponível");

        EditarProdutoView ep = new EditarProdutoView(id, quantidade, preco, nome, descricao, disponibilidade);
        ep.setVisible(true);
    }//GEN-LAST:event_tabProdutosMouseClicked

    private void btnSair3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair3ActionPerformed
        dispose();
        LoginView f = new LoginView();
        f.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSair3ActionPerformed

    private void btnAtualizar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizar3ActionPerformed
        configColumnVendas();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtualizar3ActionPerformed

    private void tabVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabVendasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabVendasMouseClicked

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
            java.util.logging.Logger.getLogger(AdmInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdmInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdmInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdmInicialView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdmInicialView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnAtualizar2;
    private javax.swing.JButton btnAtualizar3;
    private javax.swing.JButton btnNovoProd;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSair2;
    private javax.swing.JButton btnSair3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabProdutos;
    private javax.swing.JTable tabUsuarios;
    private javax.swing.JTable tabVendas;
    private javax.swing.JLabel txtFaturamento;
    // End of variables declaration//GEN-END:variables
}
