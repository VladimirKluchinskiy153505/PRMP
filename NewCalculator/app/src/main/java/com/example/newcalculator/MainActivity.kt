package com.example.newcalculator

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import org.mariuszgromada.math.mxparser.Expression

//import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    private lateinit var vibrator: Vibrator
    private lateinit var previousCal: TextView
    private lateinit var display: EditText

    var isPortrait = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previousCal = findViewById(R.id.result_tv)
        display = findViewById(R.id.data_tv)
        display.showSoftInputOnFocus = false
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val cleanButton = findViewById<Button>(R.id.btn_clear)?: throw IllegalStateException("button_clear not found")

        cleanButton.setOnLongClickListener {
            Vibrate()
            display.setText("")
            previousCal.text = ""
            true
        }
    }

    private fun updateText(strToAdd:String){

        val oldStr = display.text.toString()
        val cursorPs = display.selectionStart
        val leftStr = oldStr.substring(0,cursorPs)
        val rightStr = oldStr.substring(cursorPs)
        display.setText(String.format("%s%s%s",leftStr,strToAdd,rightStr))
        display.setSelection(cursorPs + strToAdd.length)
    }
    fun Vibrate(){
        if (vibrator.hasVibrator()) {
            val vibrationEffect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
    }
    fun OnDigitClick(view:View){
        Vibrate()
        updateText((view as Button).text.toString())
    }
    fun OnOpenBracketClick(v: View){
        Vibrate()
        updateText(resources.getString(R.string.parenthesesOpenText))
    }
    fun OnCloseBracketClick(v: View){
        Vibrate()
        updateText(resources.getString(R.string.parenthesesCloseText))
    }
    fun OnDivideClick(v: View){
        Vibrate()
        updateText(resources.getString(R.string.divideText))
    }
    fun OnMultiplyClick(v: View){
        Vibrate()
        updateText(resources.getString(R.string.multiplyText))
    }
    fun OnSubtractClick(v: View){
        Vibrate()
        updateText(resources.getString(R.string.subtractText))
    }

    fun OnPointClick(v: View){
        Vibrate()
        updateText(resources.getString(R.string.decimalText))
    }
    fun OnAddClick(v: View){
        Vibrate()
        updateText(resources.getString(R.string.addText))
    }
    fun OnEqualClick1(v: View){
        Vibrate()
        var userExp = display.text.toString()
        previousCal.text = userExp
        userExp = userExp.replace(resources.getString(R.string.divideText).toRegex(), "/")
        userExp = userExp.replace(resources.getString(R.string.multiplyText).toRegex(), "*")
        val exp = Expression(userExp)
        val result = exp.calculate().toString()
        display.setText(result)
        display.setSelection(result.length)
    }
    fun OnEqualClick(v: View){
        Vibrate()
        var userExp = display.text.toString()
        //previousCal.text = userExp
        userExp = userExp.replace(resources.getString(R.string.divideText).toRegex(), "/")
        userExp = userExp.replace(resources.getString(R.string.multiplyText).toRegex(), "*")
        val exp = Expression(userExp)
        val result = exp.calculate().toString()
        previousCal.setText(result)
    }

    fun OnClearClick(v: View){
        Vibrate()
        display.setText("")
        previousCal.text = ""
    }
    fun OnSinClick(v: View){
        Vibrate()
        updateText("sin(")
    }
    fun OnCosClick(v: View){
        Vibrate()
        updateText("cos(")
    }
    fun OnTanClick(v: View){
        Vibrate()
        updateText("tan(")
    }
    fun OnArcSinClick(v: View){
        Vibrate()
        updateText("arcsin(")
    }
    fun OnArcCosClick(v: View){
        Vibrate()
        updateText("arccos(")
    }
    fun OnArcTanClick(v: View){
        Vibrate()
        updateText("arctan(")
    }
    fun OnLog2Click(v: View){
        Vibrate()
        updateText("log2(")
    }
    fun OnLnClick(v: View){
        Vibrate()
        updateText("ln(")
    }
    fun OnAbsClick(v: View){
        Vibrate()
        updateText("abs(")
    }
    fun OnPiClick(v: View){
        Vibrate()
        updateText("pi")
    }
    fun OnEClick(v: View){
        Vibrate()
        updateText("e")
    }
    fun OnSqrtClick(v: View){
        Vibrate()
        updateText("sqrt(")
    }
    fun OnXSquareClick(v: View){
        Vibrate()
        updateText("^(2)")
    }
    fun OnXPowerYClcik(v: View){
        Vibrate()
        updateText("^(")
    }
    fun OnPrimeClick(v: View){
        Vibrate()
        updateText("ispr(")
    }

    fun OnBackSpaceClick(v: View){
        Vibrate()
        val cursorPos = display.selectionStart
        val textLen = display.text.length
        if (cursorPos != 0 && textLen != 0){
            val selection = display.text as SpannableStringBuilder
            selection.replace(cursorPos -1,cursorPos,"")
            display.text = selection
            display.setSelection(cursorPos -1)
        }

    }
    fun rotateBtn(v: View){

        requestedOrientation = if (isPortrait){
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        }
        else{
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
        }
        isPortrait = !isPortrait
    }

}