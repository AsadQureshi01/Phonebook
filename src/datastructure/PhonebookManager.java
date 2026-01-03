package datastructure;

import model.Contact;
import database.DatabaseManager;
import java.util.*;

/**
 * PhonebookManager handles all phonebook operations
 * Enhanced with database persistence while maintaining DSA logic
 * ALL operations use DSA structures - database only for persistence
 */
public class PhonebookManager {
    
    // Main contact list - Linear Data Structure
    private LinkedList<Contact> contacts;
    
    // HashMap for duplicate detection - Hashing
    private HashSet<String> phoneNumbers;
    
    // Maps for categorization - Multiple Lists
    private HashMap<String, LinkedList<Contact>> categorizedContacts;
    
    // Database manager for persistence
    private DatabaseManager dbManager;
    
    // Valid categories
    private final String[] VALID_CATEGORIES = {"Family", "Friends", "Work"};
    
    // Constructor
    public PhonebookManager() {
        contacts = new LinkedList<>();
        phoneNumbers = new HashSet<>();
        categorizedContacts = new HashMap<>();
        
        // Initialize category lists
        for (String category : VALID_CATEGORIES) {
            categorizedContacts.put(category, new LinkedList<>());
        }
        
        // Initialize database
        dbManager = new DatabaseManager();
        
        // Load existing contacts from database
        loadContactsFromDatabase();
    }
    
    /**
     * Load contacts from database into DSA structures
     * Called on startup
     */
    private void loadContactsFromDatabase() {
        List<Contact> loadedContacts = dbManager.loadAllContacts();
        
        for (Contact contact : loadedContacts) {
            // Add to DSA structures (without database sync)
            contacts.add(contact);
            phoneNumbers.add(contact.getPhoneNumber());
            categorizedContacts.get(contact.getCategory()).add(contact);
        }
    }
    
    /**
     * Feature 1: Add New Contact
     * DSA logic first, then database persistence
     */
    public boolean addContact(Contact contact) {
        // Duplicate detection using HashSet (DSA)
        if (phoneNumbers.contains(contact.getPhoneNumber())) {
            System.out.println("❌ Error: Contact with this phone number already exists!");
            return false;
        }
        
        // Validate category
        if (!isValidCategory(contact.getCategory())) {
            System.out.println("❌ Error: Invalid category. Use Family, Friends, or Work.");
            return false;
        }
        
        // Insert into DSA structures
        contacts.add(contact);
        phoneNumbers.add(contact.getPhoneNumber());
        categorizedContacts.get(contact.getCategory()).add(contact);
        
        // Persist to database
        boolean dbSuccess = dbManager.insertContact(contact);
        
        if (dbSuccess) {
            System.out.println("✅ Contact added successfully!");
            return true;
        } else {
            // Rollback DSA changes if database fails
            contacts.remove(contact);
            phoneNumbers.remove(contact.getPhoneNumber());
            categorizedContacts.get(contact.getCategory()).remove(contact);
            System.out.println("❌ Failed to save to database!");
            return false;
        }
    }
    
    /**
     * Feature 2: Display All Contacts
     * DSA Concept: Traversal
     */
    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("📭 No contacts found!");
            return;
        }
        
        System.out.println("\n📖 ========== ALL CONTACTS ==========");
        int count = 1;
        for (Contact contact : contacts) {
            System.out.println("Contact #" + count++);
            System.out.println(contact);
            System.out.println("-----------------------------------");
        }
    }
    
    /**
     * Get all contacts as list (for UI)
     */
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }
    
    /**
     * Feature 3: Search Contact by Name or Phone Number
     * DSA Concept: Linear Search
     */
    public Contact searchByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
    
    public Contact searchByPhone(String phone) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phone)) {
                return contact;
            }
        }
        return null;
    }
    
    public void displaySearchResults(String searchTerm, boolean isPhone) {
        Contact result = isPhone ? searchByPhone(searchTerm) : searchByName(searchTerm);
        
        if (result != null) {
            System.out.println("\n🔍 Contact Found:");
            System.out.println(result);
        } else {
            System.out.println("❌ Contact not found!");
        }
    }
    
    /**
     * Feature 4: Update Existing Contact
     * DSA logic first, then database sync
     */
    public boolean updateContact(String searchTerm, boolean isPhone, String newPhone, String newEmail) {
        Contact contact = isPhone ? searchByPhone(searchTerm) : searchByName(searchTerm);
        
        if (contact == null) {
            System.out.println("❌ Contact not found!");
            return false;
        }
        
        String oldPhone = contact.getPhoneNumber();
        
        // Check if new phone number already exists (for another contact)
        if (newPhone != null && !newPhone.equals(contact.getPhoneNumber())) {
            if (phoneNumbers.contains(newPhone)) {
                System.out.println("❌ Error: New phone number already exists!");
                return false;
            }
            
            // Update DSA structures
            phoneNumbers.remove(contact.getPhoneNumber());
            contact.setPhoneNumber(newPhone);
            phoneNumbers.add(newPhone);
        }
        
        if (newEmail != null) {
            contact.setEmail(newEmail);
        }
        
        // Sync to database
        boolean dbSuccess = dbManager.updateContact(oldPhone, contact);
        
        if (dbSuccess) {
            System.out.println("✅ Contact updated successfully!");
            return true;
        } else {
            System.out.println("⚠️ Contact updated in memory but database sync failed!");
            return true; // Still return true as DSA update succeeded
        }
    }
    
    /**
     * Feature 5: Delete a Contact
     * DSA deletion first, then database sync
     */
    public boolean deleteContact(String searchTerm, boolean isPhone) {
        Contact contact = isPhone ? searchByPhone(searchTerm) : searchByName(searchTerm);
        
        if (contact == null) {
            System.out.println("❌ Contact not found!");
            return false;
        }
        
        String phoneToDelete = contact.getPhoneNumber();
        
        // Remove from DSA structures
        contacts.remove(contact);
        phoneNumbers.remove(contact.getPhoneNumber());
        categorizedContacts.get(contact.getCategory()).remove(contact);
        
        // Sync to database
        boolean dbSuccess = dbManager.deleteContact(phoneToDelete);
        
        if (dbSuccess) {
            System.out.println("✅ Contact deleted successfully!");
            return true;
        } else {
            System.out.println("⚠️ Contact deleted from memory but database sync failed!");
            return true;
        }
    }
    
    /**
     * Feature 6: Sort Contacts Alphabetically
     * DSA Concept: Sorting - Bubble Sort
     */
    public void sortContactsBubbleSort() {
        if (contacts.isEmpty()) {
            System.out.println("📭 No contacts to sort!");
            return;
        }
        
        List<Contact> contactList = new ArrayList<>(contacts);
        int n = contactList.size();
        
        // Bubble Sort Algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (contactList.get(j).getName().compareToIgnoreCase(contactList.get(j + 1).getName()) > 0) {
                    Contact temp = contactList.get(j);
                    contactList.set(j, contactList.get(j + 1));
                    contactList.set(j + 1, temp);
                }
            }
        }
        
        contacts.clear();
        contacts.addAll(contactList);
        
        System.out.println("✅ Contacts sorted alphabetically using Bubble Sort!");
    }
    
    /**
     * Selection Sort implementation
     */
    public void sortContactsSelectionSort() {
        if (contacts.isEmpty()) {
            System.out.println("📭 No contacts to sort!");
            return;
        }
        
        List<Contact> contactList = new ArrayList<>(contacts);
        int n = contactList.size();
        
        // Selection Sort Algorithm
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (contactList.get(j).getName().compareToIgnoreCase(contactList.get(minIdx).getName()) < 0) {
                    minIdx = j;
                }
            }
            Contact temp = contactList.get(minIdx);
            contactList.set(minIdx, contactList.get(i));
            contactList.set(i, temp);
        }
        
        contacts.clear();
        contacts.addAll(contactList);
        
        System.out.println("✅ Contacts sorted alphabetically using Selection Sort!");
    }
    
    /**
     * Feature 7: Duplicate Detection using HashSet
     */
    public boolean isDuplicate(String phoneNumber) {
        return phoneNumbers.contains(phoneNumber);
    }
    
    /**
     * Feature 8: Contact Categorization
     */
    public void displayContactsByCategory(String category) {
        if (!isValidCategory(category)) {
            System.out.println("❌ Invalid category!");
            return;
        }
        
        LinkedList<Contact> categoryList = categorizedContacts.get(category);
        
        if (categoryList.isEmpty()) {
            System.out.println("📭 No contacts in " + category + " category!");
            return;
        }
        
        System.out.println("\n📂 ========== " + category.toUpperCase() + " CONTACTS ==========");
        int count = 1;
        for (Contact contact : categoryList) {
            System.out.println("Contact #" + count++);
            System.out.println(contact);
            System.out.println("-----------------------------------");
        }
    }
    
    /**
     * Get contacts by category (for UI)
     */
    public List<Contact> getContactsByCategory(String category) {
        if (!isValidCategory(category)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(categorizedContacts.get(category));
    }
    
    public void displayAllCategories() {
        System.out.println("\n📂 ========== CONTACTS BY CATEGORY ==========");
        for (String category : VALID_CATEGORIES) {
            LinkedList<Contact> categoryList = categorizedContacts.get(category);
            System.out.println("\n" + category + " (" + categoryList.size() + " contacts):");
            
            if (!categoryList.isEmpty()) {
                for (Contact contact : categoryList) {
                    System.out.println("  • " + contact.getName() + " - " + contact.getPhoneNumber());
                }
            } else {
                System.out.println("  (No contacts)");
            }
        }
    }
    
    // Helper methods
    private boolean isValidCategory(String category) {
        for (String validCategory : VALID_CATEGORIES) {
            if (validCategory.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }
    
    public int getTotalContacts() {
        return contacts.size();
    }
    
    public String[] getValidCategories() {
        return VALID_CATEGORIES;
    }
    
    /**
     * Close database connection (call on application exit)
     */
    public void closeDatabase() {
        dbManager.closeConnection();
    }
}