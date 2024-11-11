package classe_de_conexão;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tela_de_Acesso {

    private JPanel panelDeAcesso;
    private JPanel westLabelPanel;
    private JPanel eastTextFieldPanel;
    private JPanel southBottonPanel;
    private JTextField txtfUsuario;
    private JPasswordField txtfSenha;
    private JButton btnAcesso;
    private JButton btnAbrirDados;
    private JTextField txtfAbrirDados;

    public Tela_de_Acesso() {


        btnAcesso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    Connection con = Conexao.faz_Conexao();

                    String sql = "select *from primeiroprojeto where usuario=? and senha=?";

                    PreparedStatement stmt = con.prepareStatement(sql);

                    stmt.setString(1, txtfUsuario.getText());
                    stmt.setString(2, new String(txtfSenha.getPassword()));

                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {

                        Tela_de_cadastramento tela = new Tela_de_cadastramento();

                        tela.setContentPane(tela.panelCadastramento);
                        tela.setTitle("Cadastramento");
                        tela.setSize(250,600);
                        tela.setVisible(true);


                    }else {

                        JOptionPane.showMessageDialog(null, "Usúario ou Senha incorreta");
                    }

                    stmt.close();
                    con.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tela de acesso");
        frame.setContentPane(new Tela_de_Acesso().panelDeAcesso);
        frame.setSize(250,220);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
