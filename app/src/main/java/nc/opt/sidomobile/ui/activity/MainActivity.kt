package nc.opt.sidomobile.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.firebase.FirebaseApp
import nc.opt.sidomobile.R
import nc.opt.sidomobile.ui.fragment.CodeBarreScannerFragment
import nc.opt.sidomobile.ui.fragment.OCRFragment
import nc.opt.sidomobile.ui.fragment.SpeechRecognizerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonOcr: Button = findViewById(R.id.button_ocr)
        val buttonSpeechRecognizer: Button = findViewById(R.id.button_speech_recognizer)
        val buttonScanCodeBarre: Button = findViewById(R.id.button_scan_code_barre)


        FirebaseApp.initializeApp(this)

        // val intent: Intent = object : Intent(this, BarcodeCaptureActivity::class.java){}
        // startActivity(intent)

        buttonOcr.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, OCRFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
        buttonSpeechRecognizer.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, SpeechRecognizerFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
        buttonScanCodeBarre.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, CodeBarreScannerFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }
}
