package de.fxnn.brainfapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        configureRecentFileList(createRecentFileArray())
        configureCreateFileFab()
    }

    private fun configureCreateFileFab() {
        fab_create_file.setOnClickListener { _ ->
            startActivity(createCodeActivityIntent())
        }
    }

    private fun createCodeActivityIntent(recentFile: RecentFile): Intent {
        val intent = createCodeActivityIntent()
        intent.putExtra(CodeActivity.INTENT_EXTRA_FILE_NAME, recentFile.fileName)
        return intent
    }

    private fun createCodeActivityIntent() = Intent(this, CodeActivity::class.java)

    private fun onRecentFileSelected(recentFile: RecentFile, view: View) {
        startActivity(createCodeActivityIntent(recentFile))
    }

    private fun configureRecentFileList(recentFileArray: Array<RecentFile>) {
        val recentFileAdapter = RecentFileAdapter(
            recentFileArray,
            this::onRecentFileSelected
        )

        view_recentFileList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recentFileAdapter
        }
    }

    private fun createRecentFileArray(): Array<RecentFile> {
        return BrainfappApplication
            .database(this)
            .recentFileDao()
            .getAll()
            .toTypedArray()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
