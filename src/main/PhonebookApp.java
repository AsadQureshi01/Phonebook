package main;

import datastructure.PhonebookManager;
import model.Contact;
import java.util.Scanner;

/**
 * Main Application Class for Phonebook
 * Provides Console-based User Interface
 */
public class PhonebookApp {
    
    private static PhonebookManager phonebook = new PhonebookManager();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║   📞 PHONEBOOK MANAGEMENT SYSTEM 📞   ║");
        System.out.println("║      Data Structures & Algorithms     ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewContact();
                    break;
                case 2:
                    displayAllContacts();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    updateContact();
                    break;
                case 5:
                    deleteContact();
                    break;
                case 6:
                    sortContacts();
                    break;
                case 7:
                    checkDuplicate();
                    break;
                case 8:
                    viewByCategory();
                    break;
                case 9:
                    phonebook.displayAllCategories();
                    break;
                case 10:
                    running = false;
                    System.out.println("\n👋 Thank you for using Phonebook! Goodbye!");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("               MAIN MENU               ");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1.  ➕ Add New Contact");
        System.out.println("2.  📋 Display All Contacts");
        System.out.println("3.  🔍 Search Contact");
        System.out.println("4.  ✏️  Update Contact");
        System.out.println("5.  🗑️  Delete Contact");
        System.out.println("6.  🔤 Sort Contacts Alphabetically");
        System.out.println("7.  🔄 Check for Duplicate");
        System.out.println("8.  📂 View Contacts by Category");
        System.out.println("9.  📊 Display All Categories");
        System.out.println("10. 🚪 Exit");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Total Contacts: " + phonebook.getTotalContacts());
        System.out.println();
    }
    
    // Feature 1: Add New Contact
    private static void addNewContact() {
        System.out.println("\n➕ ========== ADD NEW CONTACT ==========");
        
        scanner.nextLine(); // Clear buffer
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter Email (optional, press Enter to skip): ");
        String email = scanner.nextLine();
        
        System.out.println("\nCategories: Family | Friends | Work");
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        // Create and add contact
        Contact newContact;
        if (email.isEmpty()) {
            newContact = new Contact(name, phone, category);
        } else {
            newContact = new Contact(name, phone, email, category);
        }
        
        phonebook.addContact(newContact);
    }
    
    // Feature 2: Display All Contacts
    private static void displayAllContacts() {
        phonebook.displayAllContacts();
    }
    
    // Feature 3: Search Contact
    private static void searchContact() {
        System.out.println("\n🔍 ========== SEARCH CONTACT ==========");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Phone Number");
        
        int choice = getIntInput("Enter choice: ");
        scanner.nextLine(); // Clear buffer
        
        switch (choice) {
            case 1:
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                phonebook.displaySearchResults(name, false);
                break;
            case 2:
                System.out.print("Enter Phone Number: ");
                String phone = scanner.nextLine();
                phonebook.displaySearchResults(phone, true);
                break;
            default:
                System.out.println("❌ Invalid choice!");
        }
    }
    
    // Feature 4: Update Contact
    private static void updateContact() {
        System.out.println("\n✏️ ========== UPDATE CONTACT ==========");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Phone Number");
        
        int choice = getIntInput("Enter choice: ");
        scanner.nextLine(); // Clear buffer
        
        String searchTerm;
        boolean isPhone = false;
        
        if (choice == 1) {
            System.out.print("Enter Name: ");
            searchTerm = scanner.nextLine();
        } else if (choice == 2) {
            System.out.print("Enter Phone Number: ");
            searchTerm = scanner.nextLine();
            isPhone = true;
        } else {
            System.out.println("❌ Invalid choice!");
            return;
        }
        
        // First display the contact
        phonebook.displaySearchResults(searchTerm, isPhone);
        
        System.out.println("\nWhat would you like to update?");
        System.out.print("Enter new Phone Number (or press Enter to skip): ");
        String newPhone = scanner.nextLine();
        
        System.out.print("Enter new Email (or press Enter to skip): ");
        String newEmail = scanner.nextLine();
        
        if (newPhone.isEmpty()) newPhone = null;
        if (newEmail.isEmpty()) newEmail = null;
        
        if (newPhone == null && newEmail == null) {
            System.out.println("⚠️ No changes made!");
            return;
        }
        
        phonebook.updateContact(searchTerm, isPhone, newPhone, newEmail);
    }
    
    // Feature 5: Delete Contact
    private static void deleteContact() {
        System.out.println("\n🗑️ ========== DELETE CONTACT ==========");
        System.out.println("1. Delete by Name");
        System.out.println("2. Delete by Phone Number");
        
        int choice = getIntInput("Enter choice: ");
        scanner.nextLine(); // Clear buffer
        
        String searchTerm;
        boolean isPhone = false;
        
        if (choice == 1) {
            System.out.print("Enter Name: ");
            searchTerm = scanner.nextLine();
        } else if (choice == 2) {
            System.out.print("Enter Phone Number: ");
            searchTerm = scanner.nextLine();
            isPhone = true;
        } else {
            System.out.println("❌ Invalid choice!");
            return;
        }
        
        // First display the contact
        phonebook.displaySearchResults(searchTerm, isPhone);
        
        System.out.print("\n⚠️ Are you sure you want to delete this contact? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            phonebook.deleteContact(searchTerm, isPhone);
        } else {
            System.out.println("❌ Deletion cancelled!");
        }
    }
    
    // Feature 6: Sort Contacts
    private static void sortContacts() {
        System.out.println("\n🔤 ========== SORT CONTACTS ==========");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        
        int choice = getIntInput("Choose sorting algorithm: ");
        
        switch (choice) {
            case 1:
                phonebook.sortContactsBubbleSort();
                break;
            case 2:
                phonebook.sortContactsSelectionSort();
                break;
            default:
                System.out.println("❌ Invalid choice!");
                return;
        }
        
        System.out.println("\nDisplaying sorted contacts:");
        phonebook.displayAllContacts();
    }
    
    // Feature 7: Check for Duplicate
    private static void checkDuplicate() {
        System.out.println("\n🔄 ========== CHECK DUPLICATE ==========");
        scanner.nextLine(); // Clear buffer
        
        System.out.print("Enter Phone Number to check: ");
        String phone = scanner.nextLine();
        
        if (phonebook.isDuplicate(phone)) {
            System.out.println("✅ This phone number EXISTS in the phonebook!");
            phonebook.displaySearchResults(phone, true);
        } else {
            System.out.println("❌ This phone number does NOT exist in the phonebook.");
        }
    }
    
    // Feature 8: View by Category
    private static void viewByCategory() {
        System.out.println("\n📂 ========== VIEW BY CATEGORY ==========");
        System.out.println("1. Family");
        System.out.println("2. Friends");
        System.out.println("3. Work");
        
        int choice = getIntInput("Select category: ");
        
        String category;
        switch (choice) {
            case 1:
                category = "Family";
                break;
            case 2:
                category = "Friends";
                break;
            case 3:
                category = "Work";
                break;
            default:
                System.out.println("❌ Invalid choice!");
                return;
        }
        
        phonebook.displayContactsByCategory(category);
    }
    
    // Helper method to get integer input
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! " + prompt);
            scanner.next();
        }
        return scanner.nextInt();
    }
}