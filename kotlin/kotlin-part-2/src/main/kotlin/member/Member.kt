package member

class Member(var name: String, var email: String, var phone: String) {
    constructor() : this("", "", "")

    override fun toString(): String {
        return "[이름]: $name, [이메일]: $email, [연락처]: $phone"
    }
}