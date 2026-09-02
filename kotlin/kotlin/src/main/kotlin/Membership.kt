typealias NAME = String
typealias EMAIL = String

class Membership {

    private enum class Grade(
        val limit: Int
    ) {
        LITE(10),
        BASIC(20),
        PREMIUM(30);
    }

    private data class Member(
        val name: String,
        val email: String,
        val phone: String,
        val grade: Grade = Grade.LITE
    )

    companion object {
        private fun create(
            name: String,
            email: String,
            phone: String,
            grade: Grade
        ): Member {
            require(name.isNotBlank()) {
                "Name must not be blank"
            }
            require(email.isNotBlank()) {
                "Email must not be blank"
            }
            require(phone.isNotBlank()) {
                "Phone must not be blank"
            }

            return Member(
                name = name,
                email = email,
                phone = phone,
                grade = grade
            )
        }
    }

    private val members = mutableMapOf<EMAIL, Member>()

    private val nameIndexes =
        mutableMapOf<NAME, LinkedHashSet<EMAIL>>()

    private val menus = listOf(
        "회원추가",
        "회원조회(메일)",
        "회원조회(이름)",
        "회원 전체조회",
        "회원 정보 수정",
        "회원삭제",
        "프로그램 종료"
    )

    private fun getGradeCount(grade: Grade): Int =
        members.values.count {
            it.grade == grade
        }

    fun printMenu() {
        while (true) {
            println("[요금제를 선택하세요]")
            printGrades()
            print("> ")

            val gradeSelect =
                getInput().toIntOrNull() ?: continue

            val grade =
                Grade.entries.getOrNull(gradeSelect - 1) ?: continue

            println(
                "[수행할 업무를 선택하세요 - " +
                        "현재 회원수 : ${getGradeCount(grade)} / ${grade.limit}]"
            )

            printMenus()
            print("> ")

            val select =
                getInput().toIntOrNull()?.minus(1) ?: continue

            if (select !in menus.indices) {
                continue
            }

            when (select) {
                0 -> addMemberMenu(grade)
                1 -> findByEmailMenu()
                2 -> findByNameMenu()
                3 -> printMembers(findAllMembers(grade))
                4 -> updateMemberMenu(grade)
                5 -> deleteMemberMenu()
                6 -> return
            }
        }
    }

    private fun printGrades() {
        println(
            Grade.entries
                .mapIndexed { index, grade ->
                    val name =
                        grade.name
                            .lowercase()
                            .replaceFirstChar { it.uppercase() }

                    "[${index + 1}]. $name: ${grade.limit}명"
                }
                .joinToString(" ")
        )
    }

    private fun printMenus() {
        menus.forEachIndexed { index, menu ->
            print("[${index + 1}]. $menu\t")

            if ((index + 1) % 3 == 0) {
                println()
            }
        }

        if (menus.size % 3 != 0) {
            println()
        }
    }

    private fun addMemberMenu(grade: Grade) {
        println("이름을 입력하세요.")
        print("> ")
        val name = getInput()

        println("이메일을 입력하세요.")
        print("> ")
        val email = getInput()

        println("연락처를 입력하세요.")
        print("> ")
        val phone = getInput()

        val member = create(
            name = name,
            email = email,
            phone = phone,
            grade = grade
        )

        addMember(member)

        println("회원이 등록되었습니다.")
    }

    private fun findByEmailMenu() {
        println("찾고자 하는 이메일을 입력하세요 (부분 검색 가능).")
        print("> ")

        val email = getInput()

        printMembers(
            findByEmail(email)
        )
    }

    private fun findByNameMenu() {
        println("찾고자 하는 이름을 입력하세요.")
        print("> ")

        val name = getInput()

        printMembers(
            findByName(name)
        )
    }

    private fun updateMemberMenu(grade: Grade) {
        println("수정하고자 하는 회원의 이메일을 입력하세요.")
        print("> ")

        val email = getInput()

        val member =
            findByExactEmail(email) ?: run {
                println("존재하지 않는 회원입니다.")
                return
            }

        print("수정할 이름을 입력하세요: ")
        val newName = getInput()

        print("수정할 이메일을 입력하세요: ")
        val newEmail = getInput()

        print("수정할 휴대폰 번호를 입력하세요: ")
        val newPhone = getInput()

        updateMember(
            member = member,
            newName = newName,
            newEmail = newEmail,
            newPhone = newPhone,
            newGrade = grade
        )

        println("회원 정보가 수정되었습니다.")
    }

    private fun deleteMemberMenu() {
        println("삭제하고자 하는 회원의 이메일을 입력하세요.")
        print("> ")

        val email = getInput()

        val member =
            findByExactEmail(email) ?: run {
                println("존재하지 않는 회원입니다.")
                return
            }

        deleteMember(member)

        println("회원이 삭제되었습니다.")
    }

    private fun printMembers(members: List<Member>) {
        if (members.isEmpty()) {
            println("조회된 회원이 없습니다.")
            return
        }

        println(
            members
                .mapIndexed { index, member ->
                    "[${index + 1}]. " +
                            "${member.name} / " +
                            "${member.email} / " +
                            "${member.phone} / " +
                            member.grade
                }
                .joinToString("\n")
        )
    }

    private fun addMember(member: Member) {
        require(member.email !in members) {
            "Email already in use."
        }

        require(
            getGradeCount(member.grade) < member.grade.limit
        ) {
            "${member.grade} is full"
        }

        members[member.email] = member

        nameIndexes
            .getOrPut(member.name) {
                linkedSetOf()
            }
            .add(member.email)
    }

    private fun updateMember(
        member: Member,
        newName: String,
        newEmail: String,
        newPhone: String,
        newGrade: Grade
    ) {
        require(newName.isNotBlank()) {
            "Name must not be blank"
        }

        require(newEmail.isNotBlank()) {
            "Email must not be blank"
        }

        require(newPhone.isNotBlank()) {
            "Phone must not be blank"
        }

        require(
            newEmail == member.email ||
                    newEmail !in members
        ) {
            "Email already in use."
        }

        if (member.grade != newGrade) {
            require(
                getGradeCount(newGrade) < newGrade.limit
            ) {
                "$newGrade is full"
            }
        }

        deleteMember(member)

        addMember(
            create(
                name = newName,
                email = newEmail,
                phone = newPhone,
                grade = newGrade
            )
        )
    }

    private fun findByExactEmail(email: EMAIL): Member? =
        members[email]

    private fun findByEmail(email: String): List<Member> =
        members.values.filter {
            it.email.contains(
                email,
                ignoreCase = true
            )
        }

    private fun findByName(name: NAME): List<Member> =
        nameIndexes[name]
            ?.mapNotNull(members::get)
            ?: emptyList()

    private fun findAllMembers(grade: Grade): List<Member> =
        members.values
            .filter {
                it.grade == grade
            }
            .sortedBy {
                it.name
            }

    private fun deleteMember(member: Member) {
        require(member.email in members) {
            "존재하지 않는 회원입니다."
        }

        members.remove(member.email)

        nameIndexes[member.name]?.let { emails ->
            emails.remove(member.email)

            if (emails.isEmpty()) {
                nameIndexes.remove(member.name)
            }
        }
    }
}

internal fun getInput(): String =
    readln()

fun main() {
    Membership().printMenu()
}