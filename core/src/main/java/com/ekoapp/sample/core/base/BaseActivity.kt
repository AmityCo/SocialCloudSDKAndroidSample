package com.ekoapp.sample.core.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ekoapp.sample.core.R
import com.ekoapp.sample.core.base.components.toolbar.ToolbarMenu

abstract class BaseActivity : HasLayout, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    /****** Toolbar Menu *******/
    protected open fun getToolbar(): ToolbarMenu? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getToolbar()?.getMenu()?.apply { menuInflater.inflate(this, menu) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return getToolbar()?.onOptionsItemSelected(item) ?: false
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return getToolbar()?.onPrepareOptionsMenu(menu) ?: false
    }
}