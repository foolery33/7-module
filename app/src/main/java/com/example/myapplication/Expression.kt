package com.example.myapplication

class Expression(expression: String): MainActivity() {

    open var expression = ""
    var correctExpression: String? = null
    var valueOfExpression = ""
    var success = true
    var errors: MutableList<String> = mutableListOf()

    init {
        this.expression = expression
        var newExpression = getCorrectExpression(this.expression)
        if (newExpression != null) {
            this.correctExpression = newExpression
            this.valueOfExpression = getVal(this.correctExpression.toString());
        }
    }

    /*private fun getValueOfExpression(): Int {

    }*/

    private fun isCorrectExpression(expression: String): Boolean {

        val expressionWithoutArrays = processArrays(expression)

        val nonZeroVariable = "(?:-?(?:[1-9][0-9]*|[a-zA-Z]\\w*))"
        val zeroVariable = "(?:-?(?:0|[1-9][0-9]*|[a-zA-Z]\\w*))"
        val correctExpression = "$zeroVariable(?:[+*/%-]$nonZeroVariable|[+*-]$zeroVariable)*"

        return Regex(correctExpression).matchEntire(expressionWithoutArrays) != null
    }

    private fun processArrays(expression: String): String {

        var newExpression = expression

        while (Regex("\\[[^\\[\\]]+]").containsMatchIn(newExpression)) {
            var matchResult = Regex("\\[[^\\[\\]]+]").findAll(newExpression)
            matchResult.forEach { f ->
                var value = f.value
                value = value.replace("[", "")
                value = value.replace("]", "")
                if(isCorrectExpression(value)) {
                    newExpression = newExpression.replace(f.value, "s__n")
                }
                else {
                    return newExpression
                }
            }
        }
        return newExpression
    }

    private fun processBrackets(expression: String): String {

        var newExpression = expression

        while (Regex("\\([^()]+\\)").containsMatchIn(newExpression)) {
            var matchResult = Regex("\\([^()]+\\)").findAll(newExpression)
            matchResult.forEach { f ->
                var value = f.value
                value = value.replace("(", "")
                value = value.replace(")", "")
                if (isCorrectExpression(value)) {
                    newExpression = newExpression.replace(f.value, "s__n")
                }
                else {
                    return newExpression
                }
            }
        }

        return newExpression
    }

    private fun getCorrectExpression(expression: String): String? {

        val expressionWithoutSpace = expression.replace(" ", "");

        val expressionWithoutBrackets = processBrackets(expressionWithoutSpace)

        if (!isCorrectExpression(expressionWithoutBrackets)) {
            this.errors.add("Cannot read expression '$expression'")
            this.success = false
            return null
        }

        var finalExpression = expressionWithoutSpace

        finalExpression = replaceArrayElements(finalExpression)
        if(this.success) {
            finalExpression = replaceVariables(finalExpression)
            if(this.success) {
                finalExpression = finalExpression.replace("(-", "(0-")
                finalExpression = finalExpression.replace("+-", "+0-")
                finalExpression = finalExpression.replace("--", "-0-")
                if(finalExpression[0] == '-') {
                    finalExpression = finalExpression.padStart(finalExpression.length + 1, '0')
                }
                return finalExpression
            }
        }
        return null
    }

    fun replaceArrayElements(text: String): String {
        var editedText = text
        while (Regex("\\[[^\\[\\]]+]").containsMatchIn(editedText) && this.success) {
            val variablesInExpression = Regex("([a-zA-Z]\\w*\\[[^\\[\\]]+])").findAll(editedText)
            variablesInExpression.forEach { f ->
                val currentVariable: String = f.value
                val nameOfArray = getNameOfArray(currentVariable)
                if (arrays.containsKey(nameOfArray)) {
                    val inBrackets = getInBracketsExpression(currentVariable)
                    val inBracketExpression = Expression(inBrackets)
                    if (arrays[nameOfArray]!![inBracketExpression.valueOfExpression.toInt()] != -92314123) {
                        editedText = editedText.replace(currentVariable, arrays[nameOfArray]!![inBracketExpression.valueOfExpression.toInt()].toString())
                    }
                    else {
                        this.success = false
                        this.errors.add("Variable $currentVariable has not been declared")
                    }
                }
                else {
                    this.errors.add("Array $nameOfArray has not been declared")
                    this.success = false
                }
            }
        }
        return editedText
    }

    fun replaceVariables(text: String): String {
        var editedText = text
        val variablesInExpression = Regex("([a-zA-Z]\\w*)").findAll(text)
        variablesInExpression.forEach { f ->
            val currentVariable: String = f.value
            if (variables.containsKey(currentVariable)) {
                editedText = editedText.replace(currentVariable, variables[currentVariable].toString())
            }
            else {
                this.errors.add("Variable $currentVariable has not been declared")
                this.success = false
            }
        }
        return editedText
    }

    fun getNameOfArray(text: String): String {
        var nameOfArray = ""
        for (i in text) {
            if(i != '[') {
                nameOfArray += i
            }
            else {
                break
            }
        }
        return nameOfArray
    }

    fun getInBracketsExpression(text: String): String {
        var inBracketsExpression = ""
        var flag = false
        for (i in text) {
            if(i == ']') {
                break
            }
            if(flag) {
                inBracketsExpression += i
            }
            if(i == '[') {
                flag = true
            }
        }
        return inBracketsExpression
    }

    fun getVal(expression: String): String {
        val res = calc(expression)
        return res
    }

    private fun invertStack(stack: Stack): Stack {
        val result = Stack()
        while (!stack.isEmpty()) result.push(stack.pop())
        return result
    }

    private fun toRPN(expr: String): Stack {
        val result = Stack()
        val stack = SymbolsStack()
        var buffer = ""

        for (ch: Char in expr) {
            if (ch.isDigit()) {
                buffer += ch.toString()
                continue
            }
            result.push(buffer)
            buffer = ""

            if (stack.isEmpty() || ch == '(') {
                stack.push(ch)
            }
            else if (ch == ')') {
                while (!stack.isEmpty()) {
                    val last = stack.pop()
                    when (last) {
                        '(' -> break
                        else -> result.push(last.toString())
                    }
                }
            }
            else {
                if (stack.comparePriority(ch)) {
                    stack.push(ch)
                }
                else {
                    while (!stack.isEmpty()) {
                        if (!stack.comparePriority(ch)) {
                            result.push(stack.pop().toString())
                        }
                        else {
                            break
                        }
                    }
                    stack.push(ch)
                }
            }
        }
        result.push(buffer)
        while (!stack.isEmpty()) {
            result.push(stack.pop().toString())
        }
        return result
    }

    private fun calc(input: String): String {
        val rpn = invertStack(toRPN(input))
        val result = Stack()
        var msgError = ""

        try {
            while (!rpn.isEmpty()) {
                val item = rpn.pop()
                if (!item.isEmpty()) {
                    if (item[0].isDigit()) {
                        result.push(item)
                        continue
                    }

                    val num2 = result.pop().toInt()
                    val num1 = result.pop().toInt()

                    if (num2 == 0) {
                        msgError = "error"
                        break
                    }

                    result.push(
                        when (item[0]) {
                            '+' -> num1 + num2
                            '-' -> num1 - num2
                            '*' -> num1 * num2
                            '/' -> num1 / num2
                            '%' -> num1 % num2
                            else -> 0
                        }.toString()
                    )
                }
            }
        } catch (e: Exception) {
            msgError = "error"
        }

        return if (msgError.isEmpty()) {
            result.pop()
        } else {
            msgError
        }
    }

}