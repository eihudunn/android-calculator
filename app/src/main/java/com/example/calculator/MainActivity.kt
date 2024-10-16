package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView
    lateinit var calculationString: TextView

    var state: Int = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0
    var calcString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout)

        textResult = findViewById(R.id.display)
        calculationString = findViewById(R.id.calculationString)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEqual, R.id.btnC, R.id.btnCE, R.id.btnBS, R.id.btnPlusMinus, R.id.btnDecimal
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {
            R.id.btn0 -> addDigit(0)
            R.id.btn1 -> addDigit(1)
            R.id.btn2 -> addDigit(2)
            R.id.btn3 -> addDigit(3)
            R.id.btn4 -> addDigit(4)
            R.id.btn5 -> addDigit(5)
            R.id.btn6 -> addDigit(6)
            R.id.btn7 -> addDigit(7)
            R.id.btn8 -> addDigit(8)
            R.id.btn9 -> addDigit(9)
            R.id.btnAdd -> handleOperation(1, " + ")
            R.id.btnSubtract -> handleOperation(2, " - ")
            R.id.btnMultiply -> handleOperation(3, " * ")
            R.id.btnDivide -> handleOperation(4, " / ")
            R.id.btnEqual -> {
                calculateResult()
                calcString += " = ${textResult.text}"
                calculationString.text = calcString
                state = 1
                op1 = textResult.text.toString().toInt()
                op2 = 0
                op = 0
                calcString = "${textResult.text}"
            }
            R.id.btnC, R.id.btnCE -> {
                state = 1
                op1 = 0
                op2 = 0
                op = 0
                textResult.text = "0"
                calcString = ""
                calculationString.text = ""
            }
            R.id.btnBS -> {
                if (state == 1 && op1 != 0) {
                    op1 /= 10
                    textResult.text = "$op1"
                } else if (state == 2 && op2 != 0) {
                    op2 /= 10
                    textResult.text = "$op2"
                }
            }
            R.id.btnPlusMinus -> {
                if (state == 1) {
                    op1 = -op1
                    textResult.text = "$op1"
                } else {
                    op2 = -op2
                    textResult.text = "$op2"
                }
            }
            R.id.btnDecimal -> {}
        }
    }

    private fun handleOperation(newOp: Int, symbol: String) {
        if (state == 2) {
            calculateResult()
            op1 = textResult.text.toString().toInt()
            op2 = 0
        }
        op = newOp
        state = 2
        calcString += symbol
        calculationString.text = calcString
    }

    private fun calculateResult() {
        val result = when (op) {
            1 -> op1 + op2
            2 -> op1 - op2
            3 -> op1 * op2
            4 -> op1 / op2
            else -> 0
        }
        textResult.text = "$result"
    }

    private fun addDigit(c: Int) {
        if (state == 1) {
            op1 = op1 * 10 + c
            textResult.text = "$op1"
            calcString += "$c"
        } else {
            op2 = op2 * 10 + c
            textResult.text = "$op2"
            calcString += "$c"
        }
        calculationString.text = calcString
    }
}