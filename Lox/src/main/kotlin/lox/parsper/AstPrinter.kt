package lox.parsper

class AstPrinter : Expr.Visitor<String> {

    fun print(expr: Expr): String = expr.accept(this)

    override fun visitBinaryExpr(expr: Binary): String = parenthesize(expr.operator.lexeme, expr.left, expr.right)

    override fun visitGroupingExpr(expr: Grouping): String = parenthesize("group", expr.expression)

    override fun visitLiteralExpr(expr: Literal): String = expr.value?.toString() ?: "nil"

    override fun visitUnaryExpr(expr: Unary): String = parenthesize(expr.operator.lexeme, expr.right)

    private fun parenthesize(name: String, vararg exprs: Expr): String =
        exprs
            .asIterable()
            .joinToString(" ", "($name", ")") { it.accept(this) }
}