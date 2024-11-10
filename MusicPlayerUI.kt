package com.example.musicplayer

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun MusicPlayerUI(onSongSelected: (String) -> Unit) {
    val songList = remember { mutableStateListOf<Song>() }
    val context = LocalContext.current

    // Fetch songs from device storage
    DisposableEffect(Unit) {
        val songs = getSongsFromDevice(context)
        songList.addAll(songs)
        onDispose { }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Music Player", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(songList.size) { index ->
                val song = songList[index]
                SongItem(song = song, onClick = { onSongSelected(song.uri) })
            }
        }
    }
}

@Composable
fun SongItem(song: Song, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(song.title, style = MaterialTheme.typography.bodyLarge)
    }
}
