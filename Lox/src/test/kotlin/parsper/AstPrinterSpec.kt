package parsper

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import lox.Token
import lox.TokenType
import lox.parsper.*


class AstPrinterSpec : FunSpec({
    test("should print AST tree") {
        //given
        val expression = Binary(
            Unary(
                Token(TokenType.MINUS, "-", null, 1),
                Literal(123)
            ),
            Token(TokenType.STAR, "*", null, 1),
            Grouping(
                Literal(45.67)
            )
        )
        //when
        val result = AstPrinter().print(expression)

        //then
        result shouldBe "(*(-123) (group45.67))"
    }
})