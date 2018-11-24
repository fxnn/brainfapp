package de.fxnn.brainfapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class RecentFileViewModel(recentFileDao: RecentFileDao) : ViewModel() {

    var recentFiles: LiveData<List<RecentFile>> = recentFileDao.getAll()

}