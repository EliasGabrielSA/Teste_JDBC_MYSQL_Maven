/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package start;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lefoly
 */
public class Principal {
    public static void inclusao(String nome_inclusao, String email_inclusao, String telefone_inclusao) {
        try{
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/contato", "root", "");
            
            Contato c_inclusao = new Contato(nome_inclusao, email_inclusao, telefone_inclusao);
                        
            PreparedStatement ps_inclusao = conn.prepareStatement("INSERT INTO Contato (nome, email, telefone) VALUES(?, ?, ?) ");
            ps_inclusao.setString(1, c_inclusao.getNome());
            ps_inclusao.setString(2, c_inclusao.getEmail());
            ps_inclusao.setString(3, c_inclusao.getTelefone());
            ps_inclusao.execute();
        
            conn.close(); 
        } catch(SQLException ex) {
           System.out.println(ex.getMessage());
        }
    }
    
    public static void edicao(String nome_editado, String email_editado, String telefone_editado, int id_mudanca) {
        try{
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/contato", "root", "");
            
            Contato c_editado = new Contato(nome_editado, email_editado, telefone_editado);
                        c_editado.setId(id_mudanca);
                        
            PreparedStatement ps_edicao = conn.prepareStatement("UPDATE Contato SET nome = ?, email = ?, telefone = ? WHERE id = ?");
            ps_edicao.setString(1, c_editado.getNome());
            ps_edicao.setString(2, c_editado.getEmail());
            ps_edicao.setString(3, c_editado.getTelefone());
            ps_edicao.setInt(4, c_editado.getId());
            ps_edicao.execute();
            
            conn.close(); 
        } catch(SQLException ex) {
           System.out.println(ex.getMessage());
        }
    }
    
    public static void exclusao(int id_exclusao) {
        try{
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/contato", "root", "");
   
            Contato c = new Contato();    
            c.setId(id_exclusao);

            PreparedStatement ps = conn.prepareStatement("DELETE FROM Contato WHERE id = ?");
            ps.setInt(1, c.getId());
            ps.execute();
            
            conn.close(); 
        } catch(SQLException ex) {
           System.out.println(ex.getMessage());
        }
    }
    
    public static void listagem() {
        try{
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/contato", "root", "");
            
            PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Contato");
            ResultSet rs = ps1.executeQuery();
            List<Contato> lista = new ArrayList();

            while(rs.next()) {
                Contato c1 = new Contato();

                c1.setId(rs.getInt("id"));
                c1.setNome(rs.getString("nome"));
                c1.setEmail(rs.getString("email"));
                c1.setTelefone(rs.getString("telefone"));
                lista.add(c1);
            }

            StringBuilder listagem = new StringBuilder("Contatos\n==================================\nID | Nome | Email | Telefone\n==================================\n"
                    + "");

            for (Contato c1 : lista) {
                listagem.append(c1.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(null, listagem.toString());
            
            conn.close();
        } catch(SQLException ex) {
           System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        int opcao = -1;
        String menu = "CADASTRO DE CONTATOS \n" +
                          "==================== \n" + 
                          "1- Inclusao \n" +
                          "2- Exclusao \n" +
                          "3- Edicao \n" +
                          "4- Listagem \n" +
                          "0. SAIR \n" +
                          "===================== \n" +
                          "Digite uma opcao";
        
        while(opcao != 0) {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));
        
            switch(opcao) {
                case 1:
                    String nome_inclusao = JOptionPane.showInputDialog("Nome: ");
                    String email_inclusao = JOptionPane.showInputDialog("Email: ");
                    String telefone_inclusao = JOptionPane.showInputDialog("Telefone: ");
                    inclusao(nome_inclusao, email_inclusao, telefone_inclusao);
                    break;
                case 2:
                    int id_exclusao = Integer.parseInt(JOptionPane.showInputDialog("Id do objeto a ser excluido: "));
                    exclusao(id_exclusao);
                    break;
                case 3:
                    String nome_editado = JOptionPane.showInputDialog("Novo Nome: ");
                    String email_editado = JOptionPane.showInputDialog("Novo Email: ");
                    String telefone_editado = JOptionPane.showInputDialog("Novo Telefone: ");
                    int id_mudanca = Integer.parseInt(JOptionPane.showInputDialog("Id do objeto a ser editado: "));
                    edicao(nome_editado, email_editado, telefone_editado, id_mudanca);
                    break;
                case 4:
                    listagem();
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção Invalida");
                    break;
            }
        }
    }
}