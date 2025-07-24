fun main() {
    val manager = StudentManager()
    while (true) {
        println("\n--- StudentHub Menu ---")
        println("1. Add student")
        println("2. View all students")
        println("3. Filter students")
        println("4. Update student info")
        println("5. Remove student")
        println("6. Show average GPA of passed students")
        println("0. Exit")
        print("Enter your choice: ")

        when (readLine()?.toIntOrNull()) {
            1 -> manager.addStudent()
            2 -> manager.viewAllStudents()
            3 -> manager.filterStudents()
            4 -> manager.updateStudent()
            5 -> manager.removeStudent()
            6 -> manager.averageGpaOfPassed()
            0 -> {
                println("Goodbye!")
                return
            }

            else -> println("Invalid choice. Try again.")
        }
    }
}
