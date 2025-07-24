class StudentManager {
    private val students = mutableListOf<Student>()
    private var nextId = 1

    fun addStudent() {
        println("Enter name:")
        val name: String
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

        println("Enter GPA (or leave blank):")
        val gpa = readLine()?.toDoubleOrNull()

        println("Enter status (or leave blank):")
        val status = readLine()

        println("Enter notes (or leave blank):")
        val notes = readLine()

        val student = Student(nextId++, name, grade, gpa, status, notes)
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
        println("Filter by: 1) Name 2) Grade 3) Status 4) GPA Range")
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
            println("Enter new name (${student.name}):")
            val name = readLine()
            if (!name.isNullOrBlank()) student.name = name

            println("Enter new grade (${student.grade}):")
            val grade = readLine()?.toIntOrNull()
            if (grade != null) student.grade = grade

            println("Enter new GPA (${student.gpa}):")
            val gpa = readLine()?.toDoubleOrNull()
            if (gpa != null) student.gpa = gpa

            println("Enter new status (${student.status}):")
            val status = readLine()
            if (!status.isNullOrBlank()) student.status = status

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
        val removed = students.removeIf { it.id == id }

        if (removed) println("Student removed.") else println("Student not found.")
    }

    fun averageGpaOfPassed() {
        val avg = students.filter { it.status.equals("passed", ignoreCase = true) && it.gpa != null }
            .map { it.gpa!! }
            .average()

        println("Average GPA of passed students: $avg")
    }
}
