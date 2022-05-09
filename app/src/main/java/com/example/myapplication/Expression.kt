package com.example.myapplication

import kotlin.math.pow

class Expression(expression: String): MainActivity() {

    open var expression = ""
    var correctExpression: String? = null
    var valueOfExpression = ""
    var success = true
    var errors: MutableList<String> = mutableListOf()

    init {
        this.expression = expression
        var newExpression = getCorrectExpression(this.expression)
        if (newExpression != null && this.success) {
            this.correctExpression = newExpression
            this.valueOfExpression = getVal(this.correctExpression.toString())
        }
    }

    private fun isCorrectExpression(expression: String): Boolean {

        var isGoodExpression = processArrays(expression)
        isGoodExpression = processBrackets(isGoodExpression)

        val nonZeroVariable = "(?:-?(?:[1-9][0-9]*|[a-zA-Z]\\w*))"
        val zeroVariable = "(?:-?(?:0|[1-9][0-9]*|[a-zA-Z]\\w*))"
        val correctExpression = "$zeroVariable(?:[+*/%^-]$nonZeroVariable|[+*^-]$zeroVariable)*"

        return Regex(correctExpression).matchEntire(isGoodExpression) != null
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
                    newExpression = replaceElement(f.value, newExpression, "s__n")
                    //newExpression = newExpression.replace(f.value, "s__n")
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
                    newExpression = replaceElement(f.value, newExpression, "s__n")
                    //newExpression = newExpression.replace(f.value, "s__n")
                }
                else {
                    return newExpression
                }
            }
        }

        return newExpression
    }

    private fun getCorrectExpression(expression: String): String? {

        val expressionWithoutSpace = expression.replace(" ", "")

        var finalExpression = expressionWithoutSpace

        finalExpression = replaceFunctions(finalExpression)
        if(this.success) {
            finalExpression = replaceArrayElements(finalExpression)
            if(this.success) {
                finalExpression = replaceIntVariables(finalExpression)
                if(this.success) {
                    finalExpression = finalExpression.replace("(-", "(0-")
                    finalExpression = finalExpression.replace("+-", "+0-")
                    finalExpression = finalExpression.replace("--", "-0-")
                    finalExpression = finalExpression.replace("*-", "*(0-1)*")
                    finalExpression = finalExpression.replace("/-", "*(0-1)/")
                    if(finalExpression[0] == '-') {
                        finalExpression = finalExpression.padStart(finalExpression.length + 1, '0')
                    }

                    if (!isCorrectExpression(finalExpression)) {
                        if(finalExpression.contains("/0")) {
                            this.errors.add("Expression ${this.expression} contains division by zero")
                            this.success = false
                        }
                        else {
                            this.errors.add("Cannot read expression '$expression'")
                            this.success = false
                        }
                        return null
                    }
                }
            }
        }

        return finalExpression
    }

    fun replaceFunctions(text: String): String {

        var editedText = text
        val functionsInExpression = Regex("([a-zA-Z]\\w*\\(.+\\))").findAll(text)
        functionsInExpression.forEach { f ->
            val currentFunction: String = f.value
            val functionsExpression = getFunctionExpressions(editedText)
            for (i in functionsExpression) {
                if(Regex("[a-zA-Z]\\w*\\(.+\\)").matchEntire(i) != null) {
                    if (functions.containsKey(getElementName(i, '('))) {
                        var func = FunctionBlock(getElementName(i, '('), getInBracketsExpression(i, '(', ')'))
                        if (func.success) {
                            editedText = replaceElement(i, editedText, func.returnValue.toString())
                        }
                        else {
                            this.success = false
                            for (i in func.errors) {
                                this.errors.add(i)
                            }
                        }
                        // Замена функции её числовым значением
                    }
                    else {
                        this.errors.add("Function $i has not been declared")
                        this.success = false
                    }
                }
            }
        }

        return editedText
    }

    fun replaceArrayElements(text: String): String {

        var editedText = text
        while (Regex("\\[[^\\[\\]]+]").containsMatchIn(editedText) && this.success) {
            val intVariablesInExpression = Regex("([a-zA-Z]\\w*\\[[^\\[\\]]+])").findAll(editedText)
            intVariablesInExpression.forEach { f ->
                val currentVariable: String = f.value
                val nameOfArray = getElementName(currentVariable, '[')
                if (intArrays.containsKey(nameOfArray)) {
                    val inBrackets = getInBracketsExpression(currentVariable, '[', ']')
                    val inBracketExpression = Expression(inBrackets)
                    try {
                        if (intArrays[nameOfArray]!![inBracketExpression.valueOfExpression.toInt()] != -92314123) {
                            editedText = replaceElement(currentVariable, editedText, intArrays[nameOfArray]!![inBracketExpression.valueOfExpression.toInt()].toString())
                        }
                        else {
                            if(inBracketExpression.valueOfExpression.toInt() > intArrays[nameOfArray]!!.size) {
                                this.success = false
                                this.errors.add("Index $inBrackets = ${inBracketExpression.valueOfExpression} is out of array's $nameOfArray range")
                            }
                            else {
                                this.success = false
                                this.errors.add("Variable $currentVariable has not been declared")
                            }
                        }
                    }
                    catch (e: Exception) {
                        this.success = false
                        this.errors.add("Index $inBrackets = ${inBracketExpression.valueOfExpression} is out of array's $nameOfArray range")
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

    fun replaceIntVariables(text: String): String {
        var editedText = text
        val intVariablesInExpression = Regex("([a-zA-Z]\\w*)").findAll(text)
        intVariablesInExpression.forEach { f ->
            val currentVariable: String = f.value
            if (intVariables.containsKey(currentVariable)) {
                // Замена переменной её числовым значением
                editedText = replaceElement(currentVariable, editedText, intVariables[currentVariable].toString())
            }
            else {
                this.errors.add("Variable $currentVariable has not been declared")
                this.success = false
            }
        }
        return editedText
    }

    fun getFunctionExpressions(text: String): MutableList<String> {
        var list: MutableList<String> = mutableListOf()
        var currentExpression = ""
        var counter = 0
        var operators = "+-/%^*"
        var i = 0
        while (i < text.length) {
            if(text[i] != '(') {
                if(text[i] !in operators) {
                    currentExpression += text[i]
                }
                else {
                    list.add(currentExpression)
                    currentExpression = ""
                }
            }
            else {
                var j = i
                do {
                    if(text[j] == '(') {
                        counter++
                    }
                    else if(text[j] == ')') {
                        counter--
                    }
                    currentExpression += text[j]
                    j++
                } while (counter != 0 && j < text.length)
                if(j > i) {
                    i = j - 1
                }
                else {
                    i = j
                }
            }
            i++
        }
        if(currentExpression.length != 0) {
            list.add(currentExpression)
        }
        return list
    }

    fun getElementName(text: String, openBracket: Char): String {
        var nameOfArray = ""
        for (i in text) {
            if(i != openBracket) {
                nameOfArray += i
            }
            else {
                break
            }
        }
        return nameOfArray
    }

    fun getInBracketsExpression(text: String, openBracket: Char, closeBracket: Char): String {
        var inBracketsExpression = ""
        var bracketsCounter = 0
        var flag = false
        var i = 0
        while (true) {
            if(text[i] == openBracket) {
                bracketsCounter++
                flag = true
            }
            else if(text[i] == closeBracket) {
                bracketsCounter--
            }
            if(bracketsCounter == 0 && flag) {
                break
            }
            if(flag) {
                inBracketsExpression += text[i]
            }
            i++
        }
        inBracketsExpression = inBracketsExpression.removeRange(0, 1)
        return inBracketsExpression
    }

    fun replaceElement(element: String, text: String, replacingValue: String): String {

        var editedText = text

        var regexElement = element
        if(element.contains('(')) {
            regexElement = regexElement.replace("(", "\\(")
        }
        if(element.contains(')')) {
            regexElement = regexElement.replace(")", "\\)")
        }
        if(element.contains('[')) {
            regexElement = regexElement.replace("[", "\\[")
        }
        if(element.contains(']')) {
            regexElement = regexElement.replace("]", "\\]")
        }
        if(element.contains('+')) {
            regexElement = regexElement.replace("+", "\\+")
        }
        if(element.contains('-')) {
            regexElement = regexElement.replace("-", "\\-")
        }

        var indexes = regexElement.toRegex().findAll(text).map { it.range.first }.toList()

        for (i in indexes.indices) {
            val currentBeginIndex = indexes[i]
            val currentEndIndex = currentBeginIndex + element.length
            if(currentBeginIndex > 0 && currentEndIndex < editedText.length) {
                // если предыдуших символ - часть этой же переменной, то это вхождение заменять не будем
                if(Regex("\\w").matchEntire(editedText[currentBeginIndex - 1].toString()) == null && Regex("\\w").matchEntire(editedText[currentEndIndex].toString()) == null) {
                    editedText = editedText.replaceRange(currentBeginIndex, currentEndIndex, replacingValue)
                    break
                }
            }
            else if(currentBeginIndex == 0 && currentEndIndex < editedText.length) {
                if(Regex("\\w").matchEntire(editedText[currentEndIndex].toString()) == null) {
                    editedText = editedText.replaceRange(currentBeginIndex, currentEndIndex, replacingValue)
                    break
                }
            }
            else if(currentBeginIndex > 0 && currentEndIndex >= editedText.length) {
                if(Regex("\\w").matchEntire(editedText[currentBeginIndex - 1].toString()) == null) {
                    editedText = editedText.replaceRange(currentBeginIndex, currentEndIndex, replacingValue)
                    break
                }
            }
            else {
                editedText = editedText.replace(element, replacingValue)
            }
        }

        return editedText
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

                    if(num2 == 0 && item[0]=='/') {
                        this.success = false
                        errors.add("Expression $input contains division by zero")
                        break
                    }

                    result.push(
                        when (item[0]) {
                            '+' -> num1 + num2
                            '-' -> num1 - num2
                            '*' -> num1 * num2
                            '/' -> num1 / num2
                            '%' -> num1 % num2
                            '^' -> (num1.toDouble().pow(num2)).toInt()
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