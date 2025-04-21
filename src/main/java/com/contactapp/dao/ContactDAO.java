package com.contactapp.dao;

import com.contactapp.model.Contact;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    // Cadena de conexión corregida (espacios eliminados)
    private final String url = "jdbc:sqlite:D:\\Downloads\\apache-tomcat-9.0.104-windows-x64\\apache-tomcat-9.0.104\\bin\\database\\contacts.db";

    public ContactDAO() {
        // Registrar el controlador SQLite
        try {
            Class.forName("org.sqlite.JDBC"); // Asegura que el controlador esté cargado
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Ruta DB absoluta: " + new File("database/contacts.db").getAbsolutePath());


    }

    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("cedula")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Contact getContactById(int id) {
        String sql = "SELECT * FROM contacts WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("cedula")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addContact(Contact contact) {
        String sql = "INSERT INTO contacts(name, email, phone,cedula) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Insertando: " + contact.getName());
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getEmail());
            pstmt.setString(3, contact.getPhone());
            pstmt.setString(4, contact.getCedula());

            int rows = pstmt.executeUpdate();
            System.out.println("Filas insertadas: " + rows);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name=?, email=?, phone=?, cedula=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getEmail());
            pstmt.setString(3, contact.getPhone());
            pstmt.setString(4, contact.getCedula());
            pstmt.setInt(5, contact.getId()); // ← CORRECTO: este es el índice 5
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id=?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}