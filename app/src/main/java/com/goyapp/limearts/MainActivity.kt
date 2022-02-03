package com.lime.arts

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.goyapp.limearts.ImageManager
import com.goyapp.limearts.ProfileImgAdapter
import com.lime.arts.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(){
    private lateinit var binding : ActivityMainBinding
    private var adapter : ProfileImgAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rcViewInit()
    }

    private fun rcViewInit() = with(binding){
        adapter = ProfileImgAdapter(ImageManager.arraylistImage,this@MainActivity)
        rcViewPhoto.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
        rcViewPhoto.adapter = adapter
    }

}