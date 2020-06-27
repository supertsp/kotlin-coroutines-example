package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    var contCall = 0
    var textResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            text.text = "Loading content..."

            // Contexts: IO (network, io, etc), Main (UI), Default (havy actions like filters operation)
            CoroutineScope(Dispatchers.Default).launch {
                fakeApiRequest()
            }

        }

    }

    private suspend fun setTextViewWithCoroutine(input: String){
        //Coroutine context to not crash UI
        withContext(Dispatchers.Main){
            textResult += input + "\n"
            text.text = textResult
        }
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        println("debug: $result1")
        setTextViewWithCoroutine(result1)
    }

    private suspend fun getResult1FromApi(): String{
        logThread("getResult1FromApi")
        delay(2000)
        contCall++
        return "Result #$contCall"
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

}