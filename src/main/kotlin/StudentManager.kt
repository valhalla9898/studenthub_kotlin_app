import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class StudentManager {
    private val students = mutableListOf<Student>()
    private val json = Json { prettyPrint = true }

    fun addStudent() {
        var name: String
        while (true) {
            val input = "Enter name (or type 'back' to cancel): ".promptNullable()
            if (input == null || input.equals("back", ignoreCase = true)) return
            if (input.isNotBlank()) {
                name = input
                break
            }
            println("Name cannot be blank.")
        }

        val grade = "Enter grade (or leave blank): ".promptNullable()?.toIntOrNull()

        var gpa: Double? = null
        while (true) {
            val input = "Enter GPA (0-4, or leave blank): ".promptNullable()
            if (input.isNullOrBlank()) break
            val value = input.toDoubleOrNull()
            if (value != null && value in 0.0..4.0) {
                gpa = value
                break
            }
            println("Invalid GPA. Please enter a number between 0 and 4, or leave blank.")
        }

        val status = "Enter status (or leave blank): ".promptOrDefault("passed")
        val notes = "Enter notes (or leave blank): ".promptNullable()

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
        println(
            """
            Filter by: 
            1) Name
            2) Grade 
            3) Status 
            4) GPA Range
            Enter your choice: 
            """.trimIndent()
        )
        when (readLine()?.toIntOrNull()) {
            1 -> {
                val name = "Enter name:".promptNullable()
                students.filter { it.name.contains(name ?: "", ignoreCase = true) }
                    .forEach { println(it) }
            }

            2 -> {
                val grade = "Enter grade:".promptOrDefault("/").toIntOrNull()
                students.filter { it.grade == grade }
                    .forEach { println(it) }
            }

            3 -> {
                val status = "Enter status:".promptNullable()
                students.filter { it.status.equals(status, ignoreCase = true) }
                    .forEach { println(it) }
            }

            4 -> {
                val min = "Enter min GPA:".promptOrDefault("0.0").toDouble()
                val max = "Enter max GPA:".promptOrDefault("4.0").toDouble()
                students.filter { it.gpa != null && it.gpa!! in min..max }
                    .forEach { println(it) }
            }

            else -> println("Invalid option.")
        }
    }

    fun updateStudent() {
        val id = "Enter student ID to update: ".promptNullable()?.toIntOrNull()
        val student = students.find { it.id == id }

        if (student != null) {
            // Name update with validation
            while (true) {
                val input = "Enter new name (${student.name}) (or leave blank to keep current): ".promptNullable()
                if (input.isNullOrBlank()) break
                if (input.isNotBlank()) {
                    student.name = input
                    break
                }
            }

            // Grade update
            val gradeInput = "Enter new grade (${student.grade}) (or leave blank to keep current): ".promptNullable()
            if (!gradeInput.isNullOrBlank()) {
                val grade = gradeInput.toIntOrNull()
                if (grade != null) student.grade = grade
                else println("Invalid grade. Keeping current value.")
            }

            // GPA update with validation
            while (true) {
                val input = "Enter new GPA (${student.gpa}) (0-4, or leave blank to keep current): ".promptNullable()
                if (input.isNullOrBlank()) break
                val value = input.toDoubleOrNull()
                if (value != null && value in 0.0..4.0) {
                    student.gpa = value
                    break
                }
                println("Invalid GPA. Please enter a number between 0 and 4, or leave blank.")
            }

            // Status update
            val status = "Enter new status (${student.status}): ".promptNullable()
            if (!status.isNullOrBlank()) student.status = status

            // Notes update
            val notes = "Enter new notes (${student.notes}): ".promptNullable()
            if (!notes.isNullOrBlank()) student.notes = notes

            println("Student updated.")
        } else {
            println("Student not found.")
        }
    }

    fun removeStudent() {
        val id = "Enter student ID to remove: ".promptNullable()?.toIntOrNull()
        val student = students.find { it.id == id }

        if (student != null) {
            println("Are you sure you want to remove this student?")
            println(student)
            val confirmation = "Type 'yes' to confirm, or anything else to cancel: ".promptNullable()
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

        if (avg.isNaN()) {
            println("No passed students with GPA found.")
        } else {
            println("Average GPA of passed students: $avg")
        }
    }

    fun exportStudentsData() {
        print(
            """
            1) Export to CSV
            2) Export to JSON
            0) Back to main menu
            Enter your choice:
        """.trimIndent()
        )

        when (readLine()?.toIntOrNull()) {
            1 -> exportToCsv()
            2 -> exportToJson()
            else -> println("Invalid choice.")
        }
    }

    // Helper extension function to prompt for input with a default value
    fun String.promptOrDefault(default: String): String {
        print(this)
        val input = readLine()
        return if (input.isNullOrBlank()) default else input
    }

    // Helper extension function to escape CSV values
    fun String.escapeCsv(): String = "\"${this.replace("\"", "\"\"")}\""

    // Helper function to convert any value to a CSV field
    fun Any?.toCsvField(): String = when (this) {
        null -> ""
        is String -> this.escapeCsv()
        else -> this.toString()
    }

    fun exportToCsv() {
        val fileName =
            "Enter file name to export to (or leave blank for students.csv): "
                .promptOrDefault("students.csv")

        val file = File(fileName)
        file.printWriter().use { out ->
            out.println("id,name,grade,gpa,status,notes")
            for (student in students) {
                val line = listOf(
                    student.id.toCsvField(),
                    student.name.toCsvField(),
                    student.grade.toCsvField(),
                    student.gpa.toCsvField(),
                    student.status.toCsvField(),
                    student.notes.toCsvField()
                ).joinToString(",")
                out.println(line)
            }
        }
        println("Exported ${students.size} students to $fileName")
    }

    fun exportToJson() {
        val fileName =
            "Enter file name to export to (or leave blank for students.json): "
                .promptOrDefault("students.json")

        val jsonString = json.encodeToString(students)
        File(fileName).writeText(jsonString)
        println("Exported ${students.size} students to $fileName")
    }
}
