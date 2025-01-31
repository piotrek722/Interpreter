package lox

import lox.parser.AstPrinter
import lox.parser.Parser
import lox.scanner.Scanner
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess


object Lox {

    private var hadError: Boolean = false

    fun run(args: Array<String>) {
        if (args.size > 1) {
            println("Usage: jlox [script]")
            exitProcess(64)
        } else if (args.size == 1) {
            runFile(args[0])
        } else {
            runPrompt()
        }
    }

    private fun run(source: String) {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)

        val expr = parser.parse()
        if (hadError) {
            return
        }
        expr?.let {
            println(AstPrinter.print(it))
        }
    }

    private fun runFile(path: String) {
        val bytes = Files.readAllBytes(Paths.get(path))
        run(String(bytes, Charset.defaultCharset()))
        if (hadError) exitProcess(65)
    }

    private fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            val line = reader.readLine() ?: break

            if(line == "exit") {
                break
            }

            run(line)
            hadError = false
        }
    }

    fun error(line: Int, message: String) {
        report(line, "", message)
    }

    private fun report(
        line: Int, where: String,
        message: String
    ) {
        System.err.println(
            "[line $line] Error$where: $message"
        )
        hadError = true
    }

    fun error(token: Token, message: String) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message)
        } else {
            report(token.line, " at '" + token.lexeme + "'", message)
        }
    }

}