package com.example.doggoapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.doggoapp.ApiService.ApiDog
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service = retrofit.create(ApiDog::class.java)
        getImage(service)

        var button: Button = findViewById(R.id.btn_Randomize)
        button.setOnClickListener {
            getImage(service)

        }


    }

    fun getImage(service: ApiDog) {

        lifecycleScope.launch {
            try {
                val doggo = service.randomDogImages()
                Log.v("Ciao", "Response: ${doggo.body()!!.message}")
                val url: String = doggo.body()!!.message
                val doggoImage: ImageView = findViewById(R.id.imgURL)
                Picasso.get().load(url).into(doggoImage)

            } catch (e: Exception) {
                Log.v("Ciao", "Error: $e")
                Snackbar.make(
                        findViewById(R.id.mainViewConstraint),
                        "Connection lost. Try again",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Retry") {
                        getImage(service)
                    }.show()


            }


        }
    }
}


