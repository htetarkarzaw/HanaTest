package com.htetarkarzaw.hanatest.persentation.screen

import android.Manifest
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.htetarkarzaw.hanatest.R
import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.databinding.ActivityMainBinding
import com.htetarkarzaw.hanatest.persentation.base.BaseActivity
import com.htetarkarzaw.hanatest.persentation.component.SuccessDialog
import com.htetarkarzaw.hanatest.persentation.component.UploadUserDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainViewModel by viewModels()
    private val successDialog: SuccessDialog by lazy { SuccessDialog(this) }
    private val permissions =
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val permissionForJsonFile =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val readPermission = it[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
            val writePermission = it[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: false
            if (readPermission && writePermission) {
                createJsonFile()
            }
        }

    private fun createJsonFile() {
        val filesDir = this.filesDir
        val userJsonFile = File(filesDir, "user.json").apply {
            createNewFile()
            deleteOnExit()
        }
        viewModel.createJson(userJsonFile)
    }

    override fun initUi() {
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            UploadUserDialog(this, viewModel).show()
        }
    }

    override fun observe() {
        lifecycleScope.launch {
            viewModel.uploadUser.collectLatest {
                hideLoadingDialog()
                when (it) {
                    is Resource.Error -> {
                        errorDialog.setUpDialog("Upload Fail:${it.message}", isRetry = false) {
                            errorDialog.dismiss()
                        }
                    }

                    is Resource.Loading -> {
                        showLoadingDialog("Uploading...")
                    }

                    is Resource.Nothing -> {

                    }

                    is Resource.Success -> {
                        it.data?.let { it1 -> successDialog.setUpDialog(it1) }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.createJson.collectLatest {
                hideLoadingDialog()
                when(it){
                    is Resource.Error -> {
                        errorDialog.setUpDialog("Create Json:${it.message}", isRetry = false) {
                            errorDialog.dismiss()
                        }
                    }

                    is Resource.Loading -> {
                        showLoadingDialog("Creating json file...")
                    }

                    is Resource.Nothing -> {

                    }

                    is Resource.Success -> {
                        it.data?.let { it1 -> successDialog.setUpDialog(it1) }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                permissionForJsonFile.launch(permissions)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}