package database;

import model.Contact;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseManager handles all database operations
 * Uses SQLite for persistent storage
 * Database is ONLY used for persistence - all DSA logic remains in PhonebookManager
 */
public class DatabaseManager {
    
    private static final String DB_URL = "jdbc:sqlite:phonebook.db";
    private Connection connection;
    
    /**
     * Constructor - Initialize database connection and create table
     */
    public DatabaseManager() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("✅ Database connection established!");
            
            // Create table if not exists
            createTable();
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQLite JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Database connection error!");
            e.printStackTrace();
        }
    }
    
    /**
     * Create contacts table
     */
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL UNIQUE," +
                    "email TEXT," +
                    "category TEXT NOT NULL" +
                    ");";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Database table ready!");
        } catch (SQLException e) {
            System.err.println("❌ Error creating table!");
            e.printStackTrace();
        }
    }
    
    /**
     * Load all contacts from database
     * Called on application startup to populate DSA structures
     */
    public List<Contact> loadAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT name, phone, email, category FROM contacts";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String category = rs.getString("category");
                
                Contact contact = new Contact(name, phone, email, category);
                contacts.add(contact);
            }
            
            System.out.println("✅ Loaded " + contacts.size() + " contacts from database");
            
        } catch (SQLException e) {
            System.err.println("❌ Error loading contacts!");
            e.printStackTrace();
        }
        
        return contacts;
    }
    
    /**
     * Insert a new contact into database
     * Called AFTER successful addition to DSA structures
     */
    public boolean insertContact(Contact contact) {
        String sql = "INSERT INTO contacts(name, phone, email, category) VALUES(?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhoneNumber());
            pstmt.setString(3, contact.getEmail());
            pstmt.setString(4, contact.getCategory());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("❌ Error inserting contact into database!");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update an existing contact in database
     * Called AFTER successful update in DSA structures
     */
    public boolean updateContact(String oldPhone, Contact updatedContact) {
        String sql = "UPDATE contacts SET name = ?, phone = ?, email = ?, category = ? WHERE phone = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, updatedContact.getName());
            pstmt.setString(2, updatedContact.getPhoneNumber());
            pstmt.setString(3, updatedContact.getEmail());
            pstmt.setString(4, updatedContact.getCategory());
            pstmt.setString(5, oldPhone);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error updating contact in database!");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete a contact from database
     * Called AFTER successful deletion from DSA structures
     */
    public boolean deleteContact(String phone) {
        String sql = "DELETE FROM contacts WHERE phone = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error deleting contact from database!");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database connection!");
            e.printStackTrace();
        }
    }
    
    /**
     * Clear all contacts from database (for testing purposes)
     */
    public void clearAllContacts() {
        String sql = "DELETE FROM contacts";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("✅ All contacts cleared from database!");
        } catch (SQLException e) {
            System.err.println("❌ Error clearing contacts!");
            e.printStackTrace();
        }
    }
}