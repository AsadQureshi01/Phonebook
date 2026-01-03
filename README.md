# 📞 Phonebook Management System  
### Mini Project – Data Structures & Algorithms

This repository contains a **Phonebook Management System** developed as a **mini project** for the **Data Structures and Algorithms** course.

The project demonstrates the **practical application of DSA concepts** such as LinkedList, HashMap, HashSet, searching, and sorting algorithms, along with a **JavaFX-based graphical user interface** and **SQLite database persistence**.

---

## 🏫 Institute Details

- **Institute:** Thakur Institute of Management Studies Career Development and Research (TIMSCDR), Mumbai  
- **Project Type:** Mini Project  
- **Course:** Data Structures and Algorithms  
- **Academic Year:** 2025–2026  

---

## 👥 Project Team

| Roll No | Name |
|------|------|
| 137 | Aditya Prajapati |
| 140 | Aakah Prasad |
| 142 | Asad Qureshi |
| 144 | Priyanshu Rajak |
| 145 | Yash Rajbhar |
| 151 | Prateek Roy |
| 154 | John Sanchis |
| 165 | Ashok Shetty |

---

## 🎯 Project Aim

To design and implement a **Phonebook Management System** that applies **Data Structures and Algorithms** to efficiently manage contacts while providing a user-friendly interface and persistent storage.

---

## ✨ Features

- ➕ Add new contacts  
- 📋 View all contacts  
- 🔍 Search contacts by name or phone number  
- ✏️ Update existing contacts  
- 🗑️ Delete contacts  
- 🔤 Sort contacts alphabetically (Bubble Sort / Selection Sort)  
- 🔄 Duplicate detection using HashSet  
- 📂 Category-based filtering (Family, Friends, Work)  
- 💾 Persistent storage using SQLite  
- 🖥️ JavaFX-based graphical user interface  

---

## 🧠 Data Structures & Algorithms Used

### Data Structures
- **LinkedList** – Dynamic contact storage  
- **HashSet** – Duplicate phone number detection (O(1))  
- **HashMap** – Category-wise contact organization  

### Algorithms
- **Linear Search** – Search by name or phone number  
- **Bubble Sort** – Alphabetical sorting  
- **Selection Sort** – Alternative sorting approach  

---

## 🏗️ Project Structure

The project follows a layered structure with separate packages for:
- **model** – Contact entity representation  
- **datastructure** – Core data structures and algorithms logic  
- **database** – SQLite database persistence  
- **ui** – JavaFX user interface  
- **main** – Application entry point  
- **.github/workflows** – CI/CD configuration using GitHub Actions

---

## 🖥️ User Interface

The project uses **JavaFX** to provide:
- Contact table view  
- Input forms  
- Dialogs and alerts  
- Sorting and filtering controls  

The UI is designed to be **clean, simple, and suitable for academic presentation**.

---

## 🔄 CI/CD Pipeline (Deployment)

This project uses **GitHub Actions** to implement a **Continuous Integration (CI)** pipeline.

### What the CI pipeline does:
- Automatically installs **JavaFX**
- Compiles **all Java source files**, including UI components
- Validates build correctness on every push

📌 **CI/CD (Deployment) Link:**  
👉 https://github.com/AsadQureshi01/Phonebook/actions

> Since this is a desktop-based JavaFX application, deployment focuses on automated build verification rather than web hosting.

---

## ▶️ How to Run the Project Locally

### Prerequisites
- Java JDK 21  
- JavaFX SDK 21  
- SQLite JDBC Driver  

### Steps
1. Clone the repository  
2. Open the project in Eclipse / IntelliJ  
3. Configure JavaFX VM options:
--module-path <path-to-javafx-lib> --add-modules javafx.controls,javafx.fxml
4. Run `PhonebookUI.java`

---

## 📌 Conclusion

This mini project successfully demonstrates how **Data Structures and Algorithms** can be applied to a real-world problem using Java.  
It integrates **DSA concepts, database persistence, UI development, and CI automation**, making it suitable for academic evaluation and viva discussions.

---

## 📜 License

This project is developed strictly for **educational purposes**.
