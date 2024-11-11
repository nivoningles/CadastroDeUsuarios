package classe_de_conexão;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tela_de_cadastramento extends JFrame {

    public JPanel panelCadastramento;
    private JTextField txtfID;
    private JPanel westLabelPanel;
    private JPanel eastTextFieldPanel;
    private JPanel southBottonPanel;
    private JTextField txtfUsuario;
    private JTextField txtfSenha;
    private JButton btnCadastrar;
    private JButton btnAbrirDados;
    private JTextField txtfAbrirDados;
    private JPanel south2BottonPanel;
    private JTable tbDados;
    private JButton btnListarDados;
    private JScrollPane scrollPane1;

    public Tela_de_cadastramento() {

            btnCadastrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (txtfUsuario.getText().equals("") || txtfSenha.getText().equals("")) {
                        JOptionPane.showMessageDialog(null,"Usuario ou senha em branco");
                    }else {

                        try {

                            Connection con = Conexao.faz_Conexao();

                            String slq = "insert into primeiroprojeto(usuario, senha) value (?, ?)";
                            PreparedStatement stmt = con.prepareStatement(slq);

                            stmt.setString(1, txtfUsuario.getText());
                            stmt.setString(2, txtfSenha.getText());

                            stmt.execute();
                            stmt.close();
                            con.close();

                            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");

                            txtfUsuario.setText("");
                            txtfSenha.setText("");

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            });


        btnAbrirDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtfAbrirDados.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Campo de Dados em Branco");
                }else {

                    try {

                        Connection con = Conexao.faz_Conexao();

                        String sql = "select *from primeiroprojeto where id like ?";

                        PreparedStatement stmt = con.prepareStatement(sql);

                        stmt.setString(1, "%" + txtfAbrirDados.getText());

                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {

                            txtfID.setText(rs.getString("id"));
                            txtfUsuario.setText(rs.getString("usuario"));
                            txtfSenha.setText(rs.getString("senha"));

                        }

                        stmt.close();
                        con.close();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        btnListarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = Conexao.faz_Conexao();

                    String sql = "select * from primeiroprojeto";

                    PreparedStatement stmt = con.prepareStatement(sql);

                    ResultSet rs = stmt.executeQuery();

                    DefaultTableModel model = (DefaultTableModel) tbDados.getModel();

                    model.setColumnIdentifiers(new String[]{"ID", "USUARIO", "SENHA"});
                    model.setRowCount(0);

                    while (rs.next()) {

                        model.addRow(new Object[]{rs.getString("id"),rs.getString("usuario"),rs.getString("senha")});

                    }

                    tbDados.addMouseListener(new MouseAdapter() {

                        public void mouseClicked(MouseEvent e) {

                            int selectedRow = tbDados.getSelectedRow();

                            if (selectedRow !=-1) {

                                String id = (String) tbDados.getValueAt(selectedRow, 0).toString();
                                String usuario = (String) tbDados.getValueAt(selectedRow, 1).toString();
                                String senha = (String) tbDados.getValueAt(selectedRow, 2).toString();

                                txtfUsuario.setText(usuario);
                                txtfSenha.setText(senha);
                                txtfID.setText(id);

                            }

                        }

                    });

                    rs.close();
                    stmt.close();
                    con.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Tela_de_cadastramento frame = new Tela_de_cadastramento();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setContentPane(frame.panelCadastramento);
                    frame.setSize(500,600);
                    frame.setVisible(true);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }}
