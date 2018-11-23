package de.fxnn.brainfapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_code.*
import kotlinx.android.synthetic.main.content_code.*
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class CodeActivity : AppCompatActivity() {

    companion object {
        const val INTENT_EXTRA_FILE_NAME = "de.fxnn.brainfapp.FileName"
        val fileNameDateFormat = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US)
        const val FILE_NAME_SUFFIX = ".bf"

    }

    var fileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
        setSupportActionBar(toolbar)

        fileName = getFileNameFromIntent()
        if (fileName == null) {
            fileName = generateFileName()
        } else {
            loadFromFile()
        }

        fab_run.setOnClickListener { runCode() }
    }

    private fun generateFileName(): String {
        return fileNameDateFormat.format(Date()) + FILE_NAME_SUFFIX;
    }

    override fun onStop() {
        saveToFile()
        updateRecentFile()
        super.onStop()
    }

    private fun updateRecentFile() {
        val recentFileDao = BrainfappApplication.database(this).recentFileDao()
        val byFileName = recentFileDao.findByFileName(fileName!!)
        if (byFileName.isEmpty()) {
            val recentFile = RecentFile(fileName!!)
            recentFileDao.insert(recentFile)
        } else {
            val recentFile = byFileName.get(0)
            recentFileDao.update(recentFile)
        }
    }

    private fun loadFromFile() {
        try {
            applicationContext.openFileInput(fileName).use {
                editText_code.setText(it.bufferedReader(CHARSET).readText())
            }
        } catch (ignored: FileNotFoundException) {
            // HINT: that's okay, the file will be created later
        }
    }

    private fun saveToFile() {
        applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(getCodeFromEdit().toByteArray(CHARSET))
        }
    }

    private fun runCode() {
        val code = getCodeFromEdit()
        val runIntent = Intent(this, RunActivity::class.java)
            .putExtra(RunActivity.INTENT_EXTRA_CODE, code)
        startActivity(runIntent)
    }

    private fun getCodeFromEdit() = editText_code.text.toString()

    private fun getFileNameFromIntent(): String? {
        val extras = intent.extras ?: return null
        val codeObject = extras[INTENT_EXTRA_FILE_NAME] ?: return null
        return codeObject.toString()
    }

}
