package com.dani.hyperosexperience

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF171923)
private val Muted = Color(0xFF777D8A)
private val Surface = Color(0xFFF4F5F8)
private val Accent = Color(0xFF3478F6)

data class LaunchableApp(val label: String, val packageName: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Accent,
                    background = Surface,
                    surface = Color.White
                )
            ) {
                LauncherHome(
                    apps = loadLaunchableApps(),
                    onLaunch = { packageName ->
                        packageManager.getLaunchIntentForPackage(packageName)?.let(::startActivity)
                    }
                )
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun loadLaunchableApps(): List<LaunchableApp> {
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        return packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
            .mapNotNull { info ->
                val name = info.loadLabel(packageManager)?.toString() ?: return@mapNotNull null
                LaunchableApp(name, info.activityInfo.packageName)
            }
            .distinctBy { it.packageName }
            .sortedBy { it.label.lowercase() }
    }
}

@Composable
private fun LauncherHome(
    apps: List<LaunchableApp>,
    onLaunch: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var drawerOpen by remember { mutableStateOf(false) }
    val visibleApps = remember(apps, query) {
        apps.filter { it.label.contains(query, ignoreCase = true) }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Surface)
    ) {
        // Minimal HyperOS-inspired first screen; replace wallpaper with user selection in a later milestone.
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 22.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Selasa, 24 November", color = Muted, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
                Text("09:41", color = Ink, fontSize = 58.sp, fontWeight = FontWeight.Light)
                Text("HyperOS Experience", color = Muted, fontSize = 13.sp)
                Spacer(Modifier.height(28.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickTile("Wi-Fi", "Settings")
                    QuickTile("Bluetooth", "Settings")
                    QuickTile("Mode", "Focus")
                }
            }
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.78f), RoundedCornerShape(28.dp))
                        .clickable { drawerOpen = true }
                        .padding(17.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Apps, contentDescription = null, tint = Ink)
                    Spacer(Modifier.width(12.dp))
                    Text("Semua aplikasi", color = Ink, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Search, contentDescription = null, tint = Muted)
                }
                Spacer(Modifier.height(14.dp))
                Text(
                    "Fondasi launcher v0.1.0",
                    color = Muted,
                    fontSize = 11.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        if (drawerOpen) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Surface
            ) {
                Column(Modifier.fillMaxSize().padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Aplikasi", fontSize = 26.sp, fontWeight = FontWeight.SemiBold, color = Ink,
                            modifier = Modifier.weight(1f))
                        TextButton(onClick = { drawerOpen = false }) { Text("Tutup") }
                    }
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("Cari aplikasi") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                    )
                    Spacer(Modifier.height(12.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(visibleApps) { app ->
                            Column(
                                modifier = Modifier.clickable { onLaunch(app.packageName) }
                                    .padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = Color.White,
                                    modifier = Modifier.size(54.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(app.label.take(1).uppercase(), color = Accent,
                                            fontSize = 23.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(Modifier.height(6.dp))
                                Text(app.label, color = Ink, fontSize = 11.sp, maxLines = 1)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun RowScope.QuickTile(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .weight(1f)
            .background(
                Color.White.copy(alpha = 0.82f),
                RoundedCornerShape(20.dp)
            )
            .padding(14.dp)
    ) {
        Text(title, color = Ink, fontWeight = FontWeight.SemiBold)
        Text(subtitle, color = Muted, fontSize = 11.sp)
    }
}
