import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MembershipTest {

    @Test
    fun `같은 이메일은 중복 등록할 수 없다`() {
        val membership = Membership()

        membership.addMember(
            Member(
                name = "홍길동",
                email = "hong@test.com",
                phone = "010-1111-1111",
                grade = Grade.LITE
            )
        )

        val exception = assertThrows<IllegalArgumentException> {
            membership.addMember(
                Member(
                    name = "김철수",
                    email = "hong@test.com",
                    phone = "010-2222-2222",
                    grade = Grade.LITE
                )
            )
        }

        assertEquals(
            "Email already in use.",
            exception.message
        )
    }

    @Test
    fun `LITE 등급은 10명을 초과하여 등록할 수 없다`() {
        val membership = Membership()

        repeat(Grade.LITE.limit) { index ->
            membership.addMember(
                Member(
                    name = "회원$index",
                    email = "member$index@test.com",
                    phone = "010-0000-${index.toString().padStart(4, '0')}",
                    grade = Grade.LITE
                )
            )
        }

        val exception = assertThrows<IllegalArgumentException> {
            membership.addMember(
                Member(
                    name = "초과회원",
                    email = "overflow@test.com",
                    phone = "010-9999-9999",
                    grade = Grade.LITE
                )
            )
        }

        assertEquals(
            "LITE is full",
            exception.message
        )
    }

    @Test
    fun `회원 정보를 변경하면 기존 이메일은 제거되고 새 이메일로 조회된다`() {
        val membership = Membership()

        val member = Member(
            name = "홍길동",
            email = "old@test.com",
            phone = "010-1111-1111",
            grade = Grade.LITE
        )

        membership.addMember(member)

        membership.updateMember(
            member = member,
            newName = "김길동",
            newEmail = "new@test.com",
            newPhone = "010-2222-2222",
            newGrade = Grade.BASIC
        )

        assertNull(
            membership.findByExactEmail("old@test.com")
        )

        val updated =
            membership.findByExactEmail("new@test.com")

        assertNotNull(updated)

        assertEquals("김길동", updated?.name)
        assertEquals("new@test.com", updated?.email)
        assertEquals("010-2222-2222", updated?.phone)
        assertEquals(Grade.BASIC, updated?.grade)
    }

    @Test
    fun `회원 이름을 변경하면 이름 인덱스도 함께 변경된다`() {
        val membership = Membership()

        val member = Member(
            name = "홍길동",
            email = "hong@test.com",
            phone = "010-1111-1111",
            grade = Grade.LITE
        )

        membership.addMember(member)

        membership.updateMember(
            member = member,
            newName = "김길동",
            newEmail = "hong@test.com",
            newPhone = "010-1111-1111",
            newGrade = Grade.LITE
        )

        assertTrue(
            membership.findByName("홍길동").isEmpty()
        )

        assertEquals(
            listOf("hong@test.com"),
            membership.findByName("김길동")
                .map { it.email }
        )
    }

    @Test
    fun `중복 이메일로 변경을 시도해도 기존 회원은 삭제되지 않는다`() {
        val membership = Membership()

        val hong = Member(
            name = "홍길동",
            email = "hong@test.com",
            phone = "010-1111-1111",
            grade = Grade.LITE
        )

        val kim = Member(
            name = "김철수",
            email = "kim@test.com",
            phone = "010-2222-2222",
            grade = Grade.LITE
        )

        membership.addMember(hong)
        membership.addMember(kim)

        assertThrows<IllegalArgumentException> {
            membership.updateMember(
                member = hong,
                newName = "홍길동",
                newEmail = "kim@test.com",
                newPhone = "010-3333-3333",
                newGrade = Grade.LITE
            )
        }

        // 실패했어도 기존 상태가 그대로 살아 있어야 한다.
        assertNotNull(
            membership.findByExactEmail("hong@test.com")
        )

        assertNotNull(
            membership.findByExactEmail("kim@test.com")
        )
    }
}