package model;

/**
 * Contact class represents a single contact entry in the phonebook
 * This class encapsulates all contact information
 */
public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    private String category; // Family, Friends, Work
    
    // Constructor with all parameters
    public Contact(String name, String phoneNumber, String email, String category) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.category = category;
    }
    
    // Constructor without email (optional field)
    public Contact(String name, String phoneNumber, String category) {
        this(name, phoneNumber, "", category);
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    // toString method for easy display
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Phone: ").append(phoneNumber).append("\n");
        if (email != null && !email.isEmpty()) {
            sb.append("Email: ").append(email).append("\n");
        }
        sb.append("Category: ").append(category).append("\n");
        return sb.toString();
    }
    
    // Equals method for comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return phoneNumber.equals(contact.phoneNumber);
    }
    
    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }
}