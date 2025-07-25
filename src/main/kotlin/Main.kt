fun main() {
    val adminUsername = "admin"
    val adminPassword = "1234"

    println("Welcome to StudentHub Admin Panel")
    print("Enter username: ")
    val username = readLine()
    print("Enter password: ")
    val password = readLine()

    if (username != adminUsername || password != adminPassword) {
        println("Access denied. Exiting.")
        return
    }
    println("Login successful. Welcome, admin!")
    val manager = StudentManager()
    while (true) {
        print("""
            --- StudentHub Menu ---
            1. Add student
            2. View all students
            3. Filter students
            4. Update student info
            5. Remove student
            6. Show average GPA of passed students
            7. Export students data
            0. Exit
            Enter your choice: 
        """.trimIndent())

        when (readLine()?.toIntOrNull()) {
            1 -> manager.addStudent()
            2 -> manager.viewAllStudents()
            3 -> manager.filterStudents()
            4 -> manager.updateStudent()
            5 -> manager.removeStudent()
            6 -> manager.averageGpaOfPassed()
            7 -> manager.exportStudentsData()
            0 -> {
                println("Goodbye!")
                return
            }

            else -> println("Invalid choice. Try again.")
        }
    }
}
