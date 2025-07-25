fun main() {
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
            0 -> {
                println("Goodbye!")
                return
            }

            else -> println("Invalid choice. Try again.")
        }
    }
}
