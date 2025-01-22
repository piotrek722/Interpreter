package lox.parser

object AstPrinter {

    fun print(expr: Expr): String =
        when (expr) {
            is Binary -> parenthesize(expr.operator.lexeme, expr.left, expr.right)
            is Grouping -> parenthesize("group", expr.expression)
            is Literal -> expr.value?.toString() ?: "nil"
            is Unary -> parenthesize(expr.operator.lexeme, expr.right)
        }

    private fun parenthesize(name: String, vararg exprs: Expr): String =
        exprs
            .asIterable()
            .joinToString(" ", "($name ", ")") { print(it) }
}