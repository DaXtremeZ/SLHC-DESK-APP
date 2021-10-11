package com.example.slhc_desk_app

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.PaymentConfiguration
import okhttp3.*
import org.json.JSONObject
import java.io.IOException



class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_card)
    }

    private fun displayAlert(title: String, message: String){
        runOnUiThread{
            val builder = AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)

            builder.setPositiveButton("OK",null)
            builder
                    .create()
                    .show()
        }
    }

    //Fetch publishable key from server and initialise the Stripe SDK

}