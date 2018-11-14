package de.fxnn.brainfapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.text.Editable

import kotlinx.android.synthetic.main.activity_code.*
import kotlinx.android.synthetic.main.content_code.*

class CodeActivity : AppCompatActivity() {

    companion object {
        const val INTENT_EXTRA_CODE = "de.fxnn.brainfapp.Code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
        setSupportActionBar(toolbar)

        val code = getCodeFromIntent()
        if (code != null) {
            editText_code.setText(code)
        }

        fab_run.setOnClickListener { runCode() }
    }

    private fun runCode() {
        val code = editText_code.text.toString()
        val runIntent = Intent(this, RunActivity::class.java)
            .putExtra(RunActivity.INTENT_EXTRA_CODE, code)
        startActivity(runIntent)
    }

    private fun getCodeFromIntent(): String? {
        val extras = intent.extras ?: return null
        val codeObject = extras[RunActivity.INTENT_EXTRA_CODE] ?: return null
        return codeObject.toString()
    }

}
