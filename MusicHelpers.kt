package com.example.musicplayer

import android.content.Context
import android.provider.MediaStore

data class Song(val title: String, val uri: String)

fun getSongsFromDevice(context: Context): List<Song> {
    val songs = mutableListOf<Song>()
//    Log.d("MusicPlayer", "Fetching songs from device")
//    ...
//    Log.d("MusicPlayer", "Total songs found: ${songs.size}")
//    return songs
    val projection = arrayOf(MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA)
    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    val cursor = context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        null,
        null
    )

    cursor?.use {
        val titleIndex = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val dataIndex = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

        while (it.moveToNext()) {
            val title = it.getString(titleIndex)
            val data = it.getString(dataIndex)
            songs.add(Song(title, data))
        }
    }
    return songs
}
