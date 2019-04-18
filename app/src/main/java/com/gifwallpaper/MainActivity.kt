package com.gifwallpaper

import android.app.Activity
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.view.View
import android.os.Bundle
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
//import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
                showWallpaperChooser()
    }

    private fun showWallpaperChooser() {
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            .putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(this, GifWallpaperService::class.java))
        startActivity(intent)
    }

    private val Context.isLiveWallpaperSupported: Boolean
        get() {
            val wallpaperManager = getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager
            val isSetWallpaperAllowed = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> wallpaperManager.isSetWallpaperAllowed
                else -> true
            }

            val isWallpaperSupportedForUser = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> wallpaperManager.isWallpaperSupported
                else -> true
            }
            val isLiveWallpaperSupportedBySystem = packageManager.hasSystemFeature(PackageManager.FEATURE_LIVE_WALLPAPER)

            return isLiveWallpaperSupportedBySystem && isWallpaperSupportedForUser && isSetWallpaperAllowed
        }
}