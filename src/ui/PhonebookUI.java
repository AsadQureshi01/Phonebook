package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Contact;
import datastructure.PhonebookManager;

/**
 * JavaFX User Interface for Phonebook Application
 * Clean, minimal design suitable for academic presentation
 */
public class PhonebookUI extends Application {
    
    private PhonebookManager phonebook;
    private TableView<Contact> contactTable;
    private ObservableList<Contact> contactData;
    
    // Form fields
    private TextField nameField, phoneField, emailField;
    private ComboBox<String> categoryCombo;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize phonebook manager
        phonebook = new PhonebookManager();
        
        // Setup main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(15));
        
        // Top: Title
        VBox topSection = createTopSection();
        mainLayout.setTop(topSection);
        
        // Center: Table
        VBox centerSection = createCenterSection();
        mainLayout.setCenter(centerSection);
        
        // Right: Forms and Actions
        VBox rightSection = createRightSection();
        
        ScrollPane rightScrollPane = new ScrollPane(rightSection);
        rightScrollPane.setFitToWidth(true);
        rightScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rightScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rightScrollPane.setPannable(true);
        
        mainLayout.setRight(rightScrollPane);
        
        // Bottom: Statistics
        HBox bottomSection = createBottomSection();
        mainLayout.setBottom(bottomSection);
        
        // Create scene
        Scene scene = new Scene(mainLayout, 1200, 700);
        
        // Setup stage
        primaryStage.setTitle("📞 Phonebook Management System - DSA Project");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            phonebook.closeDatabase();
            System.out.println("Application closed successfully!");
        });
        primaryStage.show();
        
        // Load initial data
        refreshTable();
    }
    
    /**
     * Create top section with title
     */
    private VBox createTopSection() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(0, 0, 15, 0));
        
        Label titleLabel = new Label("📞 PHONEBOOK MANAGEMENT SYSTEM");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label subtitleLabel = new Label("Data Structures & Algorithms Project");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        
        vbox.getChildren().addAll(titleLabel, subtitleLabel);
        vbox.setAlignment(Pos.CENTER);
        
        return vbox;
    }
    
    /**
     * Create center section with contact table
     */
    private VBox createCenterSection() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        Label tableLabel = new Label("📋 Contact List");
        tableLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Create table
        contactTable = new TableView<>();
        contactData = FXCollections.observableArrayList();
        contactTable.setItems(contactData);
        
        // Define columns
        TableColumn<Contact, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        
        TableColumn<Contact, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneCol.setPrefWidth(150);
        
        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);
        
        TableColumn<Contact, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(120);
        
        contactTable.getColumns().addAll(nameCol, phoneCol, emailCol, categoryCol);
        
        vbox.getChildren().addAll(tableLabel, contactTable);
        VBox.setVgrow(contactTable, Priority.ALWAYS);
        
        return vbox;
    }
    
    /**
     * Create right section with forms and action buttons
     */
    private VBox createRightSection() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(10, 0, 10, 15));
        vbox.setPrefWidth(350);
        vbox.setMaxWidth(350);

        
        // Add Contact Form
        VBox addForm = createAddContactForm();
        
        // Search Section
        VBox searchSection = createSearchSection();
        
        // Action Buttons
        VBox actionButtons = createActionButtons();
        
        // Category Filter
        VBox categoryFilter = createCategoryFilter();
        
        vbox.getChildren().addAll(addForm, new Separator(), searchSection, 
                                   new Separator(), actionButtons, 
                                   new Separator(), categoryFilter);
        
        return vbox;
    }
    
    /**
     * Create add contact form
     */
    private VBox createAddContactForm() {
        VBox vbox = new VBox(10);
        
        Label formLabel = new Label("➕ Add New Contact");
        formLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        nameField = new TextField();
        nameField.setPromptText("Name");
        
        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        
        emailField = new TextField();
        emailField.setPromptText("Email (optional)");
        
        categoryCombo = new ComboBox<>();
        categoryCombo.setItems(FXCollections.observableArrayList(phonebook.getValidCategories()));
        categoryCombo.setPromptText("Select Category");
        categoryCombo.setPrefWidth(Double.MAX_VALUE);
        
        Button addButton = new Button("Add Contact");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setPrefWidth(Double.MAX_VALUE);
        addButton.setOnAction(e -> handleAddContact());
        
        vbox.getChildren().addAll(formLabel, nameField, phoneField, emailField, 
                                   categoryCombo, addButton);
        
        return vbox;
    }
    
    /**
     * Create search section
     */
    private VBox createSearchSection() {
        VBox vbox = new VBox(10);
        
        Label searchLabel = new Label("🔍 Search Contact");
        searchLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        TextField searchField = new TextField();
        searchField.setPromptText("Enter name or phone");
        
        HBox searchButtons = new HBox(10);
        
        Button searchNameBtn = new Button("Search by Name");
        searchNameBtn.setOnAction(e -> handleSearch(searchField.getText(), false));
        
        Button searchPhoneBtn = new Button("Search by Phone");
        searchPhoneBtn.setOnAction(e -> handleSearch(searchField.getText(), true));
        
        searchButtons.getChildren().addAll(searchNameBtn, searchPhoneBtn);
        
        vbox.getChildren().addAll(searchLabel, searchField, searchButtons);
        
        return vbox;
    }
    
    /**
     * Create action buttons
     */
    private VBox createActionButtons() {
        VBox vbox = new VBox(10);
        
        Label actionLabel = new Label("⚙️ Actions");
        actionLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        Button updateButton = new Button("✏️ Update Selected");
        updateButton.setPrefWidth(Double.MAX_VALUE);
        updateButton.setOnAction(e -> handleUpdate());
        
        Button deleteButton = new Button("🗑️ Delete Selected");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setPrefWidth(Double.MAX_VALUE);
        deleteButton.setOnAction(e -> handleDelete());
        
        Button sortButton = new Button("🔤 Sort Alphabetically");
        sortButton.setPrefWidth(Double.MAX_VALUE);
        sortButton.setOnAction(e -> handleSort());
        
        Button refreshButton = new Button("🔄 Refresh View");
        refreshButton.setPrefWidth(Double.MAX_VALUE);
        refreshButton.setOnAction(e -> refreshTable());
        
        vbox.getChildren().addAll(actionLabel, updateButton, deleteButton, 
                                   sortButton, refreshButton);
        
        return vbox;
    }
    
    /**
     * Create category filter
     */
    private VBox createCategoryFilter() {
        VBox vbox = new VBox(10);
        
        Label filterLabel = new Label("📂 Filter by Category");
        filterLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        Button familyBtn = new Button("Family");
        familyBtn.setPrefWidth(Double.MAX_VALUE);
        familyBtn.setOnAction(e -> filterByCategory("Family"));
        
        Button friendsBtn = new Button("Friends");
        friendsBtn.setPrefWidth(Double.MAX_VALUE);
        friendsBtn.setOnAction(e -> filterByCategory("Friends"));
        
        Button workBtn = new Button("Work");
        workBtn.setPrefWidth(Double.MAX_VALUE);
        workBtn.setOnAction(e -> filterByCategory("Work"));
        
        Button showAllBtn = new Button("Show All");
        showAllBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        showAllBtn.setPrefWidth(Double.MAX_VALUE);
        showAllBtn.setOnAction(e -> refreshTable());
        
        vbox.getChildren().addAll(filterLabel, familyBtn, friendsBtn, workBtn, showAllBtn);
        
        return vbox;
    }
    
    /**
     * Create bottom statistics section
     */
    private HBox createBottomSection() {
        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(15, 0, 0, 0));
        hbox.setAlignment(Pos.CENTER);
        
        Label statsLabel = new Label("Total Contacts: " + phonebook.getTotalContacts());
        statsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");
        
        Label dsamsg = new Label("✅ Using DSA: LinkedList, HashMap, HashSet, Linear Search, Bubble Sort");
        dsamsg.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");
        
        hbox.getChildren().addAll(statsLabel, new Separator(), dsamsg);
        
        return hbox;
    }
    
    // Event Handlers
    
    /**
     * Handle add contact
     */
    private void handleAddContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String category = categoryCombo.getValue();
        
        if (name.isEmpty() || phone.isEmpty() || category == null) {
            showAlert("Error", "Please fill in all required fields!", Alert.AlertType.ERROR);
            return;
        }
        
        Contact newContact = new Contact(name, phone, email, category);
        boolean success = phonebook.addContact(newContact);
        
        if (success) {
            showAlert("Success", "Contact added successfully!", Alert.AlertType.INFORMATION);
            clearForm();
            refreshTable();
        } else {
            showAlert("Error", "Failed to add contact! Phone number may already exist.", Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Handle search
     */
    private void handleSearch(String searchTerm, boolean isPhone) {
        if (searchTerm.isEmpty()) {
            showAlert("Error", "Please enter a search term!", Alert.AlertType.ERROR);
            return;
        }
        
        Contact result = isPhone ? phonebook.searchByPhone(searchTerm) : phonebook.searchByName(searchTerm);
        
        if (result != null) {
            contactData.clear();
            contactData.add(result);
            showAlert("Search Result", "Contact found!\n\n" + result.toString(), Alert.AlertType.INFORMATION);
        } else {
            showAlert("Search Result", "Contact not found!", Alert.AlertType.WARNING);
        }
    }
    
    /**
     * Handle update
     */
    private void handleUpdate() {
        Contact selected = contactTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert("Error", "Please select a contact to update!", Alert.AlertType.ERROR);
            return;
        }
        
        // Create update dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Contact");
        dialog.setHeaderText("Update: " + selected.getName());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField newPhoneField = new TextField(selected.getPhoneNumber());
        TextField newEmailField = new TextField(selected.getEmail());
        
        grid.add(new Label("New Phone:"), 0, 0);
        grid.add(newPhoneField, 1, 0);
        grid.add(new Label("New Email:"), 0, 1);
        grid.add(newEmailField, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String newPhone = newPhoneField.getText().trim();
                String newEmail = newEmailField.getText().trim();
                
                boolean success = phonebook.updateContact(selected.getPhoneNumber(), true, 
                                                          newPhone.isEmpty() ? null : newPhone,
                                                          newEmail.isEmpty() ? null : newEmail);
                
                if (success) {
                    showAlert("Success", "Contact updated successfully!", Alert.AlertType.INFORMATION);
                    refreshTable();
                }
            }
        });
    }
    
    /**
     * Handle delete
     */
    private void handleDelete() {
        Contact selected = contactTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert("Error", "Please select a contact to delete!", Alert.AlertType.ERROR);
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Delete Contact");
        confirm.setContentText("Are you sure you want to delete:\n" + selected.getName() + "?");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = phonebook.deleteContact(selected.getPhoneNumber(), true);
                
                if (success) {
                    showAlert("Success", "Contact deleted successfully!", Alert.AlertType.INFORMATION);
                    refreshTable();
                }
            }
        });
    }
    
    /**
     * Handle sort
     */
    private void handleSort() {
        // Create sort dialog
        Alert sortDialog = new Alert(Alert.AlertType.CONFIRMATION);
        sortDialog.setTitle("Sort Contacts");
        sortDialog.setHeaderText("Choose Sorting Algorithm");
        sortDialog.setContentText("Select the algorithm to use:");
        
        ButtonType bubbleSort = new ButtonType("Bubble Sort");
        ButtonType selectionSort = new ButtonType("Selection Sort");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        sortDialog.getButtonTypes().setAll(bubbleSort, selectionSort, cancel);
        
        sortDialog.showAndWait().ifPresent(response -> {
            if (response == bubbleSort) {
                phonebook.sortContactsBubbleSort();
                showAlert("Success", "Contacts sorted using Bubble Sort!", Alert.AlertType.INFORMATION);
                refreshTable();
            } else if (response == selectionSort) {
                phonebook.sortContactsSelectionSort();
                showAlert("Success", "Contacts sorted using Selection Sort!", Alert.AlertType.INFORMATION);
                refreshTable();
            }
        });
    }
    
    /**
     * Filter by category
     */
    private void filterByCategory(String category) {
        contactData.clear();
        contactData.addAll(phonebook.getContactsByCategory(category));
    }
    
    /**
     * Refresh table with all contacts
     */
    private void refreshTable() {
        contactData.clear();
        contactData.addAll(phonebook.getAllContacts());
    }
    
    /**
     * Clear form fields
     */
    private void clearForm() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        categoryCombo.setValue(null);
    }
    
    /**
     * Show alert dialog
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}