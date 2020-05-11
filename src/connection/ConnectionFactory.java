/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Lucas
 */
public class ConnectionFactory{

    public ConnectionFactory() {
        System.out.println("Conectando ao Banco de Dados...");
    }
    public static Connection getConnection() throws Exception{
        File connXml = new File("connection.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        
        Document doc = dBuilder.parse(connXml);
        
        String driverXml = doc.getElementsByTagName("driver").item(0).getTextContent();
        String urlXml = doc.getElementsByTagName("url").item(0).getTextContent();
        String userXml = doc.getElementsByTagName("user").item(0).getTextContent();
        String passXml = doc.getElementsByTagName("passwd").item(0).getTextContent();
        
        try {
            Class.forName(driverXml);
            System.out.println("Conectado!");
            return DriverManager.getConnection(urlXml, userXml, passXml);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: ", ex);
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                System.out.println("Conexão fechada!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {

        closeConnection(con);

        try {

            if (stmt != null) {
                stmt.close();
                System.out.println("Conexão fechada!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {

        closeConnection(con, stmt);

        try {

            if (rs != null) {
                rs.close();
                System.out.println("ResultSet fechado!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
