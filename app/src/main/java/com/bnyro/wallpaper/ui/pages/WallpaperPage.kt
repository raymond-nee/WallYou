package com.bnyro.wallpaper.ui.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bnyro.wallpaper.ui.components.WallpaperGrid
import com.bnyro.wallpaper.ui.components.dialogs.FilterDialog
import com.bnyro.wallpaper.ui.models.MainModel

@Composable
fun WallpaperPage(
    viewModel: MainModel
) {
    val context = LocalContext.current
    var showFilterDialog by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (viewModel.wallpapers.isNotEmpty()) {
            WallpaperGrid(
                wallpapers = viewModel.wallpapers
            ) {
                viewModel.fetchWallpapers {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                showFilterDialog = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = null
            )
        }
        if (showFilterDialog) {
            FilterDialog(
                api = viewModel.api
            ) { changed ->
                showFilterDialog = false
                if (changed) {
                    viewModel.clearWallpapers()
                    viewModel.fetchWallpapers {
                        Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
