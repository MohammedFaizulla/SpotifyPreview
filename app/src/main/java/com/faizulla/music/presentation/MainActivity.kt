package com.faizulla.music.presentation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.faizulla.music.R
import com.faizulla.music.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController
    private lateinit var mNavHostFragment: NavHostFragment
    private val mViewModel : MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        mNavController = mNavHostFragment.navController
        NavigationUI.setupWithNavController(mBinding.bottomNavView, mNavController)
        checkNotificationPermission()
    }

    private fun gotNotificationAccess(): Boolean {
        val contentResolver = contentResolver
        val enableNotificationListener: String =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val packageName = packageName
        return enableNotificationListener.contains(packageName)
    }

    private fun checkNotificationPermission() {
        if (!gotNotificationAccess()) {
            val builder =
                AlertDialog.Builder(this).setCancelable(false).setTitle("For Music Listener")
                    .setMessage("To enable control the music").setPositiveButton("OK") { _, _ -> startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                    }.setCancelable(false).setNegativeButton("cancel") { dialog, _ ->
                        dialog.cancel()
                    }
            builder.create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    companion object {
        private val TAG: String = MainActivity::class.java.simpleName
    }
}
