
// * 상속 - open, override, super

// 1. 코틀린의 클래스 기본이 '상속 금지'다
// 자바에서는 아무 클래스나 extends 할 수 있고, 막으려면 final을 붙여야 한다.
// 코틀린은 반대다. 기본이 final이고, 상속을 허용하려면 open을 붙여야 한다.

// class Animal {} // 상속 불가
// open class Animal {} // 상속 가능

// 메서드와 프로퍼티도 마찬가지다. open을 붙인 것만 자식이 재정의할 수 있다.
// -> "상속을 염두에 두고 설계한 것만 상속하라"는 의도이다.

// 2. 상속하기 - 콜론(:)
// class Dog : Animal() {}
// 부모에 주 생성자가 있으면 여기서 부모의 생성자를 호출해야 하므로 괄호가 붙는다.

// 3. 오버라이딩 - override 키워드
// 부모 : open fun sound() {}
// 자식 : override fun sound()  {} -> override 는 생략할 수 없다.

// super로 부모의 것을 부를 수 있다.
// override fun sound() {
//      super.sound()
//      println("추가 동작")
// }

// 프로퍼티도 오버라이드할 수 있다. 부모에 open val ..로 선언되어 있어야 한다.

// 4. Any - 모든 클래스의 최상위 부모
// 아무것도 상속하지 않으면 모든 클래스는 자동으로 Any를 상속한다.(자바의 Object)
// toString(), equals(), hashCode() ...

open class Animal(val name: String) {

    open fun sound() {
        println("$name ... ")
    }

    fun sleep() {
        println("$name sleep")
    }

    open val legs: Int = 4

    override fun toString(): String {
        return "Animal(name=$name)"
    }
}

class Dog(name: String): Animal(name) {

    override fun sound() {
        println("$name : 멍멍~")
    }

}

class Cat(name: String): Animal(name) {

    override fun sound() {
        super.sound()
        println("$name : 야옹~")
    }

}

class Bird(name: String): Animal(name) {
    override val legs: Int = 2

    override fun sound() {
        println("$name : 짹짹~")
    }
}

fun main() {

    val animals: List<Animal> = listOf(
        Dog("멍멍이"),
        Cat("야옹이"),
        Bird("짹짹이")
    )

    for (animal in animals) {
        animal.sound()
    }

}