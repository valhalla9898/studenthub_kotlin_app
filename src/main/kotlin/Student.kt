import kotlinx.serialization.Serializable

// Marks this class as serializable for Kotlin serialization
@Serializable
data class Student(
    val id: Int,
    var name: String,
    var grade: Int?,
    var gpa: Double?,
    var status: String?,
    var notes: String?
) {
    companion object {
        // Static property to generate unique IDs for students
        var nextId: Int = 1
    }
}