package 과제4

import kotlin.collections.linkedSetOf

class MemberManager {
    private val members = mutableMapOf<EMAIL, Member>()
    private val nameIndex = mutableMapOf<NAME, LinkedHashSet<EMAIL>>()

    fun addMember(member: Member): Boolean {
        if(isFull(member.grade)) return false
        if(isDuplicatedEmail(member.email)) return false

        members[member.email] = member
        nameIndex.getOrPut(member.name) { linkedSetOf() }.add(member.email)
        return true
    }
    fun addMember(name: NAME, email: EMAIL, phone: PHONE, grade: Grade = Grade.LITE)= addMember(Member.create(name, email, phone, grade))
    fun addMember(name: NAME, email: EMAIL, phone: PHONE, grade: String) = addMember(Member.create(name, email, phone, grade))

    fun searchMemberByEmail(email: EMAIL): Member? = members[email]
    fun searchMembersByName(name: NAME) =
        nameIndex[name]
            ?.mapNotNull { members[it] }
            ?: emptyList()
    fun searchMembersByContainsName(name: NAME) = members.values.filter { name in it.name }
    fun searchAllMembers(): List<Member> = members.values.toList()
    fun getMembersByDomain(): Map<String, List<Member>> =
        members.values
            .groupBy { it.email.substringAfter('@') }
            .mapValues { (_, members) ->
                members.sortedBy { it.name }
            }

    fun update(
        oldMember: Member,
        newName: String = oldMember.name,
        newEmail: EMAIL = oldMember.email,
        newPhone: PHONE = oldMember.phone,
        newGrade: Grade = oldMember.grade
    ): Boolean {
        if(newEmail != oldMember.email && isDuplicatedEmail(newEmail)) return false
        if(newGrade != oldMember.grade && isFull(newGrade)) return false
        deleteMember(oldMember.email)
        addMember(newName, newEmail, newPhone, newGrade)
        return true
    }

    fun update(
       oldMember: Member,
        newName: NAME,
        newEmail: EMAIL,
        newPhone: PHONE,
        newGrade: String
    ) = update(oldMember, newName, newEmail, newPhone, Grade.from(newGrade))

    fun deleteMember(email: EMAIL): Boolean {
        val member = searchMemberByEmail(email) ?: return false
        members.remove(email)
        nameIndex[member.name]?.let { emails ->
            emails.remove(email)
            if(emails.isEmpty()) {
                nameIndex.remove(member.name)
            }
        }
        return true
    }

    fun countRegisteredGrade(grade: Grade) = members.values.count { it.grade == grade }

    private fun isFull(grade: Grade) = countRegisteredGrade(grade) >= grade.limit
    private fun isDuplicatedEmail(email: EMAIL) = email in members

}