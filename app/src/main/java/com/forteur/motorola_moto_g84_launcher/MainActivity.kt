package com.forteur.motorola_moto_g84_launcher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        AppList2(
                            apps = getInstalledApps(packageManager),
                            packageManager = packageManager,
                            onAppClick = { onAppClick(it, this) }
                        )
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
fun AppList2(apps: List<ApplicationInfo>, packageManager: PackageManager, onAppClick: (ApplicationInfo) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // Riduci il numero di colonne
        contentPadding = PaddingValues(4.dp) // Mantiene il padding intorno alla griglia
    ) {
        items(apps) { app ->
            AppItem(appInfo = app, packageManager = packageManager, onClick = { onAppClick(app) })
        }
    }
}


// Modifica del composable AppItem2
@Composable
fun AppItem(appInfo: ApplicationInfo, packageManager: PackageManager, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp) // Aumenta il padding per ridurre il numero di icone visibili
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally, // Allinea al centro orizzontalmente
        verticalArrangement = Arrangement.Top // Allinea in alto verticalmente
    ) {
        Image(
            bitmap = appInfo.loadIcon(packageManager).toBitmap().asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp) // Imposta una dimensione fissa per le icone
                .align(Alignment.CenterHorizontally) // Allinea l'immagine al centro orizzontalmente
        )
        Text(
            text = appInfo.loadLabel(packageManager).toString(),
            fontSize = 12.sp, // Riduci la dimensione del testo se necessario
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Allinea il testo al centro orizzontalmente
                .padding(top = 4.dp) // Aggiungi padding sopra il testo
        )
    }
}



