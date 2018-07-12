package com.example.karshsoni.inapppurchasedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.billingclient.api.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PurchasesUpdatedListener {

    val TAG = "MainActivity"
    lateinit private var billingClient: BillingClient
    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        Log.d(TAG, "onPurchasesUpdated: " + responseCode + "purchases" + purchases);
        if (purchases != null) {
            for (purchase in purchases){
                billingClient.consumeAsync(purchase.purchaseToken) { responseCode, purchaseToken
                    -> Toast.makeText(this@MainActivity,"NonConsumed", Toast.LENGTH_LONG).show() }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        billingClient=BillingClient.newBuilder(this).setListener(this).build()
        billingClient.startConnection(object:BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Log.i("Disconnected", "billing client")
            }

            override fun onBillingSetupFinished(responseCode: Int) {

//                billingClient.let { billingClient ->

//                    val skulist = ArrayList<String>()
//                    skulist.add("books")
//                    skulist.add("pens")
//                    skulist.add("keychains")
//
//                    val params = SkuDetailsParams.newBuilder()
//                    params.setSkusList(skulist).setType(BillingClient.SkuType.INAPP)
//                    billingClient.querySkuDetailsAsync(params.build(), { responseCode, skuDetailsList ->
//
//                        if (responseCode == BillingClient.BillingResponse.OK && skuDetailsList != null) {
//
//                            Log.d(TAG, "onBillingSetupFinished: " + responseCode);
//                        }
//
//                    })


//                }
            }
        })

        btnBuy.setOnClickListener {
            val flowParams = BillingFlowParams.newBuilder()
                    .setSku("android.test.purchased")
                    .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                    .build()
            val responseCode = billingClient.launchBillingFlow(this@MainActivity, flowParams)
            Log.d(TAG, "onBillingSetupFinished: " + responseCode);


        }
    }
}