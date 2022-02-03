package com.goyapp.limearts

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lime.arts.MainActivity
import com.lime.arts.ProfileImageData
import com.lime.arts.R
import com.lime.arts.databinding.ProfileItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest

class ProfileImgAdapter(val listImage : List<ProfileImageData>, val activity: AppCompatActivity) : RecyclerView.Adapter<ProfileImgAdapter.ItemHolder>() {
    class ItemHolder(val binding: ProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun setData(item : ProfileImageData, activity : AppCompatActivity) = with(binding){
        Picasso.get().load(item.image).centerCrop().resize(1280,720)
            .into(mainPhoto,object : Callback {
                override fun onSuccess() {
                    pbBar.visibility = View.INVISIBLE
                }

                override fun onError(e: Exception?) {

                }

            } )

           saveImage.setOnClickListener {
               if(ContextCompat.checkSelfPermission(activity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                   ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),100)
               } else {
                   val externalStoreState = Environment.getExternalStorageState()
                   if(externalStoreState.equals(Environment.MEDIA_MOUNTED)){
                       try{
                           var storeDirectory = Environment.getExternalStorageDirectory().absolutePath
                           val file = File(storeDirectory,"${UUID.randomUUID()}.jpg")
                           val stream : OutputStream = FileOutputStream(file)
                           val drawable  = ContextCompat.getDrawable(activity.applicationContext,item.image)
                           val bitmap = (drawable as BitmapDrawable).bitmap
                           bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
                           stream.flush()
                           stream.close()

                           val snackBar = Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),"Image is saving...",Snackbar.LENGTH_LONG)
                           snackBar.show()
                       }catch(e : Exception){
                           Toast.makeText(activity.applicationContext, "Error occured",Toast.LENGTH_LONG).show()
                       }


                   }
               }

           }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       val binding = ProfileItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(listImage[position],activity)
    }

    override fun getItemCount(): Int = listImage.size

}

