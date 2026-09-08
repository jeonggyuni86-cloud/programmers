package 과제4

class MemberApp {
    private val memberManager = MemberManager()

    private val info = """
        [요금제를 선택하세요]
        [1] Lite : ${Grade.LITE.limit}명
        [2] Basic : ${Grade.BASIC.limit}명
        [3] Premium : ${Grade.PREMIUM.limit}명
    """.trimIndent()

    fun run() {
        val grade = selectGrade()
        println()
        while (true) {
            println()
            printMenu(grade)

            when (readln().trim().toIntOrNull()) {
                1 -> addMember(grade)
                2 -> searchByEmail()
                3 -> searchByName()
                4 -> printAllMembers()
                5 -> updateMember()
                6 -> deleteMember()
                7 -> searchByContainsName()
                8 -> printStatistics()
                9 -> {
                    println("프로그램을 종료합니다.")
                    return
                }

                else -> println("잘못된 입력입니다.")
            }
        }
    }

    private fun selectGrade(): Grade {
        while (true) {
            println(info)
            print("> ")

            when (readln().trim().toIntOrNull()) {
                1 -> return Grade.LITE
                2 -> return Grade.BASIC
                3 -> return Grade.PREMIUM
                else -> println("잘못된 요금제입니다.\n")
            }
        }
    }

    private fun printMenu(grade: Grade) {
        val count = memberManager.countRegisteredGrade(grade)

        println(
            """
            [수행할 업무를 선택하세요 - 현재 회원수 : $count/${grade.limit}]
            [1]회원추가 [2]회원조회(메일) [3]회원조회(이름)
            [4]회원전체조회 [5]회원정보 수정 [6]회원삭제
            [7]이름검색 [8]도메인별통계 [9]프로그램 종료
            """.trimIndent()
        )

        print("> ")
    }

    private fun addMember(grade: Grade) {
        println()
        println("[회원 추가]")

        val name = input("이름")
        val email = input("이메일")
        val phone = input("연락처")

        val success = memberManager.addMember(
            name = name,
            email = email,
            phone = phone,
            grade = grade
        )

        if (success) {
            println("회원이 추가되었습니다.")
        } else {
            println("회원 추가에 실패했습니다.")
        }
    }

    private fun searchByEmail() {
        println()
        val email = input("조회할 이메일")

        val member = memberManager.searchMemberByEmail(email)

        if (member == null) {
            println("회원을 찾을 수 없습니다.")
            return
        }

        printMember(member)
    }

    private fun searchByName() {
        println()
        val name = input("조회할 이름")

        val members = memberManager.searchMembersByName(name)

        printMembers(members)
    }

    private fun printAllMembers() {
        println()
        println("[전체 회원]")

        val members = memberManager.searchAllMembers()

        printMembers(members)
    }

    private fun updateMember() {
        println()
        val email = input("수정할 회원의 이메일")

        val oldMember = memberManager.searchMemberByEmail(email)

        if (oldMember == null) {
            println("회원을 찾을 수 없습니다.")
            return
        }

        println("변경하지 않을 값은 Enter를 누르세요.")

        print("이름 [${oldMember.name}] > ")
        val newName = readln().trim().ifEmpty { oldMember.name }

        print("이메일 [${oldMember.email}] > ")
        val newEmail = readln().trim().ifEmpty { oldMember.email }

        print("연락처 [${oldMember.phone}] > ")
        val newPhone = readln().trim().ifEmpty { oldMember.phone }

        println(
            """
            요금제
            [1] Lite
            [2] Basic
            [3] Premium
            [Enter] 유지
            """.trimIndent()
        )

        print("> ")

        val newGrade = when (readln().trim()) {
            "1" -> Grade.LITE
            "2" -> Grade.BASIC
            "3" -> Grade.PREMIUM
            else -> oldMember.grade
        }

        val success = memberManager.update(
            oldMember = oldMember,
            newName = newName,
            newEmail = newEmail,
            newPhone = newPhone,
            newGrade = newGrade
        )

        if (success) {
            println("회원 정보가 수정되었습니다.")
        } else {
            println("회원 정보 수정에 실패했습니다.")
        }
    }

    private fun deleteMember() {
        println()
        val email = input("삭제할 회원의 이메일")

        if (memberManager.deleteMember(email)) {
            println("회원이 삭제되었습니다.")
        } else {
            println("회원을 찾을 수 없습니다.")
        }
    }

    private fun searchByContainsName() {
        println()
        println("검색할 이름의 일부를 입력하세요.")
        print("> ")

        val keyword = readln().trim()
        val members = memberManager.searchMembersByContainsName(keyword)

        println("${members.size}명을 찾았습니다.")
        printMembers(members)
    }

    private fun printStatistics() {
        println()

        val allMembers = memberManager.searchAllMembers()

        if (allMembers.isEmpty()) {
            println("등록된 회원이 없습니다.")
            return
        }

        val membersByDomain = memberManager.getMembersByDomain()

        println("[이메일 도메인별]")

        membersByDomain.forEach { (domain, members) ->
            println(
                "  $domain : ${members.size}명  " +
                        "(${members.joinToString { it.name }})"
            )
        }

        println("[이름순]")

        println(
            "  " + allMembers
                .sortedBy { it.name }
                .joinToString { it.name }
        )

        println("[이름이 겹치는 회원]")

        val duplicatedNames = allMembers
            .groupBy { it.name }
            .filterValues { it.size > 1 }

        if (duplicatedNames.isEmpty()) {
            println("  없음")
            return
        }

        duplicatedNames.forEach { (name, members) ->
            println(
                "  $name : ${members.size}명  " +
                        "(${members.joinToString { it.email }})"
            )
        }
    }

    private fun printMembers(members: List<Member>) {
        if (members.isEmpty()) {
            println("회원을 찾을 수 없습니다.")
            return
        }

        members.forEachIndexed { index, member ->
            print("${index + 1}. ")
            printMember(member)
        }
    }

    private fun printMember(member: Member) {
        println(
            "[이름] ${member.name}, " +
                    "[이메일] ${member.email}, " +
                    "[연락처] ${member.phone}"
        )
    }

    private fun input(label: String): String {
        print("$label > ")
        return readln().trim()
    }
}