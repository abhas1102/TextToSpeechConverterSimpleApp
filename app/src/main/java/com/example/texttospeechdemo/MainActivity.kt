package com.example.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts:TextToSpeech?=null // Variable for Text to Speech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this,this) // Context is main activity and main activity is also the listener
        btnSpeak.setOnClickListener {
            if (inputText.text.isEmpty()){
                Toast.makeText(this,"Enter text to continue",Toast.LENGTH_SHORT).show()
            } else{
                speakOut(inputText.text.toString())
            }
        }


    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language specified is not suppported")
            }
        } else{
            Log.e("TTS","Initialisation failed")
        }
    }

    // We will check if tts object is empty or not, if not empty then stop it and shut it down

    public override fun onDestroy() {
       /* if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        } */
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null)
    }
}