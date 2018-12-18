package nc.opt.sidomobile.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.RecognizerIntent.*
import android.speech.SpeechRecognizer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import nc.opt.sidomobile.R
import java.lang.ClassCastException
import java.util.*

class SpeechRecognizerFragment : Fragment() {

    lateinit var textWidget: TextView
    lateinit var textError: TextView
    lateinit var buttonWidget: Button
    lateinit var appCompatActivity: AppCompatActivity

    private lateinit var speechRecognizer: SpeechRecognizer
    var textCaptured: String = ""
    var isActive: Boolean = false

    companion object {
        fun newInstance(): SpeechRecognizerFragment {
            return SpeechRecognizerFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            appCompatActivity = context as AppCompatActivity
        } catch (exception: ClassCastException) {
            Log.e("ERREUR", "Le context doit être une AppCompatActivity")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_speech_recognizer, container, false)

        textWidget = rootView.findViewById(R.id.text_captured)
        buttonWidget = rootView.findViewById(R.id.button_start)
        textError = rootView.findViewById(R.id.text_error)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(appCompatActivity)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(EXTRA_LANGUAGE, Locale.getDefault())

        buttonWidget.setOnClickListener {
            changeActive(!isActive)
            if (isActive) {
                speechRecognizer.startListening(intent)
            } else {
                speechRecognizer.stopListening()
            }
        }


        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.w("onReadyForSpeech", "BLABLA" + params)
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.w("onRmsChanged", "BLABLA" + rmsdB)
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.w("onBufferReceived", "BLABLA" + buffer)
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.w("onPartialResults", "BLABLA" + partialResults)
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.w("onEvent", "BLABLA" + eventType + params)
            }

            override fun onBeginningOfSpeech() {
                Log.w("onBeginningOfSpeech", "BLABLA")
                textCaptured = ""
                textWidget.text = ""
            }

            override fun onEndOfSpeech() {
                Log.w("onEndOfSpeech", "BLABLA")
            }

            override fun onError(error: Int) {
                Log.e("onError", "BLABLA" + error)
                textWidget.text = ""
                textError.text = "Erreur"
                changeActive(false)
            }

            override fun onResults(results: Bundle?) {
                //getting all the matches
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                textCaptured = matches?.get(0) ?: ""
                textWidget.text = textCaptured
                changeActive(false)
            }
        })

        return rootView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val idItem = item.itemId
        if (idItem == android.R.id.home) {
            fragmentManager!!.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeActive(active: Boolean) {
        isActive = active
        textError.text = ""
        buttonWidget.isEnabled = !isActive
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                appCompatActivity,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${appCompatActivity.packageName}")
            )
            startActivity(intent)
        }
    }
}
