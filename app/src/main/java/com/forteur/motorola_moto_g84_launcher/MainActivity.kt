package com.forteur.motorola_moto_g84_launcher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.forteur.motorola_moto_g84_launcher.ui.theme.Motorolamotog84launcherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Motorolamotog84launcherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppList(getInstalledApps(packageManager), packageManager) { appInfo ->
                        onAppClick(appInfo, this)
                    }
                }
            }
        }
    }
}

fun getInstalledApps(packageManager: PackageManager): List<ApplicationInfo> {
//    return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    val mainIntent = Intent(Intent.ACTION_MAIN, null)
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
    return packageManager.queryIntentActivities(mainIntent, 0).map { it.activityInfo.applicationInfo }
}

fun onAppClick(appInfo: ApplicationInfo, context: Context) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage(appInfo.packageName)
    context.startActivity(launchIntent)
}


@Composable
fun AppList(apps: List<ApplicationInfo>, packageManager: PackageManager, onAppClick: (ApplicationInfo) -> Unit) {
    LazyColumn {
        items(apps) { app ->
            AppItem(appInfo = app, packageManager = packageManager, onClick = { onAppClick(app) })
        }
    }
}

@Composable
fun AppItem(appInfo: ApplicationInfo, packageManager: PackageManager, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(16.dp)) {
        Image(bitmap = appInfo.loadIcon(packageManager).toBitmap().asImageBitmap(),
            contentDescription = null)
        Text(text = appInfo.loadLabel(packageManager).toString(), modifier = Modifier.padding(start = 8.dp))
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Motorolamotog84launcherTheme {
        Greeting("Android")
    }
}