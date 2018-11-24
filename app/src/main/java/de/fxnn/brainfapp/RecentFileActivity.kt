package de.fxnn.brainfapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recent_file.*
import kotlinx.android.synthetic.main.content_recent_file.*

class RecentFileActivity : AppCompatActivity() {

    private lateinit var recentFileDao: RecentFileDao
    private lateinit var viewModel: RecentFileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recentFileDao = BrainfappApplication
            .database(this)
            .recentFileDao()
        viewModel = getViewModel { RecentFileViewModel(recentFileDao) }

        setContentView(R.layout.activity_recent_file)
        setSupportActionBar(toolbar)

        configureRecentFileList()
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

    private fun configureRecentFileList() {
        val recentFileAdapter = RecentFileAdapter(this::onRecentFileSelected).apply {
            observe(this@RecentFileActivity, viewModel)
        }
        view_recentFileList.apply {
            layoutManager = LinearLayoutManager(this@RecentFileActivity)
            adapter = recentFileAdapter
        }
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
