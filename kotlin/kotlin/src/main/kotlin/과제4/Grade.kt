package 과제4

enum class Grade(
    val limit: Int
) {
    LITE(10),
    BASIC(20),
    PREMIUM(30);

    companion object {
        fun from(name: String): Grade
            = entries.firstOrNull {it.name.equals(name, ignoreCase = true)} ?: LITE
    }
}