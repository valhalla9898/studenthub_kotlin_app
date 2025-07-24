data class Student(
    val id: Int,
    var name: String,
    var grade: Int?,
    var gpa: Double?,
    var status: String?,
    var notes: String?
) {
    companion object {
        var nextId: Int = 1
    }
}