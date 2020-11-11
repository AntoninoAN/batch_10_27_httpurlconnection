package com.example.googlebooksapi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.*
import androidx.core.os.HandlerCompat

class MainActivity : AppCompatActivity(), UpdateData {

    private val handler = Handler()
//    private val h = HandlerCompat.createAsync(mainLooper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numPicker = findViewById<NumberPicker>(R.id.numb_picker)
        numPicker.minValue = 1
        numPicker.maxValue = 40
        val etInput = findViewById<TextView>(R.id.et_search_title)
        val button = findViewById<ImageButton>(R.id.btn_search)
        val spinner = findViewById<Spinner>(R.id.sp_book_types)

        button.setOnClickListener {
            checkNetworkStatus(etInput.text.toString(),
                numPicker.value.toString(),
                "5")
        }


    }

    fun checkNetworkStatus(bookTitle: String, maxResults: String, bookType: String) {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        networkInfo?.let {
            if (it.isConnected) {
                val network = Network(this, handler)
                network.setBookTitle(bookTitle)
                network.setBookMaxResult(maxResults)
                network.setBookType(bookType)

                Thread(Runnable {
                    val dataSet =
                        network.configureNetworkConnection()
                }).start()
            } else
                Toast.makeText(
                    this,
                    "No netowrk connection",
                    Toast.LENGTH_SHORT
                ).show()
        }
        println(BASE_URL)
    }

    override fun sendData(booksResponse: BooksResponse) {
        Toast.makeText(
            this, booksResponse.toString(),
            Toast.LENGTH_SHORT
        ).show();
    }

}


