class VendingMachine {
    private var totalMoney = 0
    data class Drink(
        val name: String,
        val price: Int,
        var stock: Int,
        var soldCount: Int = 0,
    )

    private val drinks = listOf(
        Drink("콜라", 500, 10),
        Drink("사이다", 500, 10),
        Drink("환타", 300, 10),
        Drink("물", 200, 10),
    )

    fun printMenu() {
        while (true) {
            val menu = drinks
                .mapIndexed { index, (name, price, stock) ->
                    "[${index + 1}] $name : ${if (stock == 0) "품절" else "%,d".format(price)}"
                }
                .joinToString(", ")
            val max = drinks.size
            println("===========================")
            println("$menu, [${max + 1}]돈 넣기, [${max + 2}]종료")
            println("현재 금액: %,d".format(totalMoney))
            println("===========================")
            println("원하는 메뉴를 선택하시오.")

            when (val selected = getSelect() - 1) {
                in drinks.indices -> selectDrink(selected)
                max -> insertMoney()
                max + 1 ->  {
                    getStatistics()
                    return
                }
                else -> println("잘못된 입력")
            }
        }
    }

    private fun selectDrink(selected: Int) {
        val drink = drinks[selected]
        if(drink.stock == 0) {
            println("품절된 메뉴입니다.")
            return
        }
        if(totalMoney < drink.price) {
            println("잔액이 부족합니다")
            return
        }
        totalMoney -= drink.price
        println("${drink.name}(이)가 나왔습니다")
        drink.soldCount++;
        drink.stock--;
    }

    private fun insertMoney() {
        println("돈을 넣으시오")
        val insert = getSelect()
        if(insert <= 0 ) {
            println("잘못된 입력입니다.")
            return
        } else if (insert % 100 != 0) {
            println("100원 단위로 넣어주세요")
            return
        }
        totalMoney += insert
    }

    private fun getStatistics() {
        println("========== 판매 통계 ==========")

        drinks.forEach { drink ->
            println("${drink.name} : ${drink.soldCount}개 판매")
        }

        val totalSales = drinks.sumOf { it.price * it.soldCount }

        val coin500 = totalMoney / 500
        val coin100 = totalMoney % 500 / 100

        println("총 판매 금액 : %,d원".format(totalSales))
        println("반환 금액 : %,d원".format(totalMoney))
        println("거스름돈 : 500원 ${coin500}개, 100원 ${coin100}개")
        println("==============================")
    }

}

internal fun getSelect(): Int = readln().toIntOrNull() ?: -1

fun main() {
    VendingMachine().printMenu()
}