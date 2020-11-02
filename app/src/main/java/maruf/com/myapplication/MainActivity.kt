package maruf.com.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
const val TOPIC="/topics/myTopic"
class MainActivity : AppCompatActivity() {
    val TAG="MainActifiy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        var simpan = findViewById(R.id.idsimpan) as Button
        val etTitle = findViewById<EditText>(R.id.idjudul)
        val etmessage = findViewById<EditText>(R.id.idpesan)

                simpan.setOnClickListener{
            val title = etTitle.text.toString()
            val message =etmessage.text.toString()
                    if(title.isNotEmpty()&&message.isNotEmpty()){
                        PushNotification(
                                NotificationData(title,message),
                                TOPIC
                        ).also {
                            sendnotification(it)
                        }

                    }
        }
    }

    private fun sendnotification (notification: PushNotification)= CoroutineScope(Dispatchers.IO).launch {
        try {
val response =RetrofitInstance.api.postnotification(notification)
            if(response.isSuccessful){
                Log.d(TAG, "Response: ${Gson().toJson(response)} ")
            } else{
                Log.e(TAG, response.errorBody().toString())
            }
        }catch (e:Exception){
            Log.e(TAG, e.toString())
        }
    }
}