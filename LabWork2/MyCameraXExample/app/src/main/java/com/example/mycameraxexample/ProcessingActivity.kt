package com.example.mycameraxexample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mycameraxexample.ui.theme.MyCameraXExampleTheme

class ProcessingActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detectedText = intent.getStringExtra("DETECTED_TEXT") ?: "No text detected"

        setContent {
            MyCameraXExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context -> ScrollView(context).apply {
                            addView(
                                TextView(context).apply {
                                    movementMethod = LinkMovementMethod.getInstance()
                                    text = createSpannable(detectedText, context)
                                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                                    val padding = TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        16f,
                                        context.resources.displayMetrics
                                    ).toInt()
                                    setPadding(padding, 0, padding, 0)
                                    isVerticalScrollBarEnabled = true
                                }
                            )
                        }
                        }
                    )
                }
            }
        }
    }
}
private fun createSpannable(detectedText: String, context: Context): SpannableString {
    val spannable = SpannableString(detectedText)
    val regex = Regex("""\S*\d+\S*""")
    val regex1 = Regex("""\S*\s?\d+\s?\S*""")

    regex.findAll(detectedText).forEach { matchResult ->
        val start = matchResult.range.first
        val end = matchResult.range.last + 1
        val expression = matchResult.value

        spannable.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(context, CalcActivity::class.java).apply {
                        putExtra("EXPRESSION", expression)
                    }
                    context.startActivity(intent)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.color = Color.BLUE
                }
            },
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    return spannable
}