package 과제4

typealias NAME = String
typealias EMAIL = String
typealias PHONE = String

data class Member (
    val name: NAME,
    val email: EMAIL,
    val phone: PHONE,
    val grade: Grade
) {
    val display: String
        get() = "[이름] $name, [이메일] $email, [연락처] $phone, [등급] $grade"

    companion object {
        fun create(
            name: NAME,
            email: EMAIL,
            phone: PHONE,
            grade: Grade = Grade.LITE
        ): Member {
            require(name.isNotBlank()) { "Name must not be blank" }
            require(email.isNotBlank()) { "Email must not be blank" }
            require(phone.isNotBlank()) { "Phone must not be blank" }
            return Member(name, email, phone, grade)
        }

        fun create(
            name: NAME,
            email: EMAIL,
            phone: PHONE,
            grade: String
        ) = create(name, email, phone, Grade.from(grade))
    }
}
