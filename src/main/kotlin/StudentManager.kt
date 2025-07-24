import kotlin.collections.remove

class StudentManager {
    private val students = mutableListOf<Student>()

    fun addStudent() {
        println("Enter name:")
        var name: String
        while (true) {
            println("Enter name (or type 'back' to cancel):")
            val input = readLine()
            if (input == null || input.equals("back", ignoreCase = true)) return
            if (input.isNotBlank()) {
                name = input
                break
            }
            println("Name cannot be blank.")
        }

        println("Enter grade (or leave blank):")
        val grade = readLine()?.toIntOrNull()

        var gpa: Double? = null
        while (true) {
            println("Enter GPA (0-4, or leave blank):")
            val input = readLine()
            if (input.isNullOrBlank()) break
            val value = input.toDoubleOrNull()
            if (value != null && value in 0.0..4.0) {
                gpa = value
                break
            }
            println("Invalid GPA. Please enter a number between 0 and 4, or leave blank.")
        }

        println("Enter status (or leave blank):")
        val status = readLine()

        println("Enter notes (or leave blank):")
        val notes = readLine()

        val student = Student(Student.nextId++, name, grade, gpa, status, notes)
        students.add(student)
        println("Student added successfully.")
    }

    fun viewAllStudents() {
        if (students.isEmpty()) {
            println("No students found.")
            return
        }

        students.forEach { println(it) }
    }

    fun filterStudents() {
        println("""
            Filter by: 
            1) Name
            2) Grade 
            3) Status 
            4) GPA Range
            Enter your choice: """.trimIndent())
        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Enter name:")
                val name = readLine()
                students.filter { it.name.contains(name ?: "", ignoreCase = true) }
                    .forEach { println(it) }
            }

            2 -> {
                println("Enter grade:")
                val grade = readLine()?.toIntOrNull()
                students.filter { it.grade == grade }
                    .forEach { println(it) }
            }

            3 -> {
                println("Enter status:")
                val status = readLine()
                students.filter { it.status.equals(status, ignoreCase = true) }
                    .forEach { println(it) }
            }

            4 -> {
                println("Enter min GPA:")
                val min = readLine()?.toDoubleOrNull() ?: 0.0
                println("Enter max GPA:")
                val max = readLine()?.toDoubleOrNull() ?: 4.0
                students.filter { it.gpa != null && it.gpa!! in min..max }
                    .forEach { println(it) }
            }

            else -> println("Invalid option.")
        }
    }

    fun updateStudent() {
        println("Enter student ID to update:")
        val id = readLine()?.toIntOrNull()
        val student = students.find { it.id == id }

        if (student != null) {
            // Name update with validation
            while (true) {
                println("Enter new name (${student.name}) (or leave blank to keep current):")
                val input = readLine()
                if (input.isNullOrBlank()) break
                if (input.isNotBlank()) {
                    student.name = input
                    break
                }
            }

            // Grade update
            println("Enter new grade (${student.grade}) (or leave blank to keep current):")
            val gradeInput = readLine()
            if (!gradeInput.isNullOrBlank()) {
                val grade = gradeInput.toIntOrNull()
                if (grade != null) student.grade = grade
                else println("Invalid grade. Keeping current value.")
            }

            // GPA update with validation
            while (true) {
                println("Enter new GPA (${student.gpa}) (0-4, or leave blank to keep current):")
                val input = readLine()
                if (input.isNullOrBlank()) break
                val value = input.toDoubleOrNull()
                if (value != null && value in 0.0..4.0) {
                    student.gpa = value
                    break
                }
                println("Invalid GPA. Please enter a number between 0 and 4, or leave blank.")
            }

            // Status update
            println("Enter new status (${student.status}):")
            val status = readLine()
            if (!status.isNullOrBlank()) student.status = status

            // Notes update
            println("Enter new notes (${student.notes}):")
            val notes = readLine()
            if (!notes.isNullOrBlank()) student.notes = notes

            println("Student updated.")
        } else {
            println("Student not found.")
        }
    }

    fun removeStudent() {
        println("Enter student ID to remove:")
        val id = readLine()?.toIntOrNull()
        val student = students.find { it.id == id }

        if (student != null) {
            println("Are you sure you want to remove this student?")
            println(student)
            println("Type 'yes' to confirm, or anything else to cancel:")
            val confirmation = readLine()
            if (confirmation.equals("yes", ignoreCase = true)) {
                students.remove(student)
                println("Student removed.")
            } else {
                println("Removal cancelled.")
            }
        } else {
            println("Student not found.")
        }
    }

    fun averageGpaOfPassed() {
        val avg = students.filter { it.status.equals("passed", ignoreCase = true) && it.gpa != null }
            .map { it.gpa!! }
            .average()

        println("Average GPA of passed students: $avg")
    }
}
