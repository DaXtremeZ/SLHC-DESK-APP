package com.example.slhc_desk_app

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.gson.GsonBuilder
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import okhttp3.*
import org.json.JSONObject
import java.io.IOException



class CardActivity : AppCompatActivity() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    private lateinit var payButton: Button;
    private lateinit var cardInputWidget: CardInputWidget


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        payButton = findViewById(R.id.payButton)
        PaymentConfiguration.init(applicationContext, "pk_test_jQp0XjsS8Te2xRQ7hhUEJK9100jkEnNQSu")
        stripe = Stripe(this, PaymentConfiguration.getInstance(applicationContext).publishableKey)
        startCheckout()
    }

    private fun displayAlert(
            title: String,
            message: String
    ){
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




    private fun startCheckout(){
        ApiClient().createPaymentIntent("card", "myr", completion = {
            paymentIntentClientSecret, error ->
            run{
                paymentIntentClientSecret.let {
                    this.paymentIntentClientSecret = it
                }
                error?.let {
                    displayAlert("Failed to load PaymentIntent", "Error: $error")
                }
            }
        })
        //Confirm the paymentIntent with the card widge
        payButton.setOnClickListener {
        cardInputWidget.paymentMethodCreateParams?.let {params ->
            val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params,paymentIntentClientSecret)
            stripe.confirmPayment(this,confirmParams)
        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Handle the result of stripe.confirmpayment
        stripe.onPaymentResult(requestCode,data, object:ApiResultCallback<PaymentIntentResult>{
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                if (paymentIntent.status == StripeIntent.Status.Succeeded){
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    displayAlert("Payment Succeded", gson.toJson(paymentIntent))
                }else if (paymentIntent.status == StripeIntent.Status.RequiresPaymentMethod){
                    displayAlert("Payment Failed", paymentIntent.lastPaymentError?.message.orEmpty())
                }
            }

            override fun onError(e: Exception) {
                displayAlert("Error", e.toString())
            }
        })
    }
}