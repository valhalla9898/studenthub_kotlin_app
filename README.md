# StudentHub – Kotlin Console App

StudentHub is a simple console-based application built with Kotlin.  
It helps manage student data with features like adding, viewing, updating, filtering, and removing students.

This project demonstrates key Kotlin concepts such as:
- data class
- Nullable types
- Collections (MutableList)
- Control flow (when, if)
- Basic OOP and modular structure

## Features

- Add a new student  
- View all students  
- Filter students by:
  - Name  
  - Grade (year)  
  - Status (e.g., passed/failed)  
  - GPA range  
- Update a student's information  
- Remove a student by ID  
- Calculate the average GPA of passed students only

## Project Structure

StudentHub/
├── Student.kt           // Data class for student information  
├── StudentManager.kt    // Handles all student-related logic  
└── main.kt              // Main function and user menu

## Sample Menu

When you run the program, you'll see:

--- StudentHub Menu ---
1. Add student  
2. View all students  
3. Filter students  
4. Update student info  
5. Remove student  
6. Show average GPA of passed students  
0. Exit  
Enter your choice:

## Requirements

- Kotlin 1.9 or above  
- IntelliJ IDEA (or any Kotlin-compatible IDE)  
- Basic terminal/console for interaction

## Notes

- GPA and Grade inputs are optional (nullable).  
- Status can be any string (e.g., "Passed", "Failed", "Pending").  
- IDs are auto-incremented starting from 1.

## Author

Developed as part of a learning project to practice Kotlin fundamentals.

## License

This project is licensed under the MIT License – feel free to use, modify, and share.
