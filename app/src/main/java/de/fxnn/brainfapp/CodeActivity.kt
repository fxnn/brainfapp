package de.fxnn.brainfapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_code.*
import kotlinx.android.synthetic.main.content_code.*


class CodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
        setSupportActionBar(toolbar)

        fab_run.setOnClickListener { _ ->
            val code = editText_code.text.toString()
            val runIntent = Intent(this, RunActivity::class.java)
                .putExtra(CODE_EXTRA_KEY, code)
            startActivity(runIntent)
        }
    }

}
