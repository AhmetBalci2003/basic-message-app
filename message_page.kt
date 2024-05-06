package com.example.message_app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.message_app.ui.theme.Message_appTheme
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.Query
import com.google.firebase.Timestamp




class message_page : ComponentActivity() {


    val db = FirebaseFirestore.getInstance()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {

            Message_appTheme {
                Surface {
                    Message_oku()



                }
            }
        }
    }





@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Message_oku() {


    val messageText = remember { mutableStateOf("") }
    val chat = db.collection("chat")
    val messages = remember {
        mutableStateListOf<Message>()
    }

    LaunchedEffect(Unit)
    {

        chat.orderBy("zaman",Query.Direction.ASCENDING).addSnapshotListener{ querySnapShotListener, FirebaseFirestoreError ->
            if (FirebaseFirestoreError != null) {
                return@addSnapshotListener
            }
            messages.clear()
            for (document in querySnapShotListener!!) {
                val message = document.toObject(Message::class.java)!!
                messages.add(message)

            }


        }
    }
    Scaffold(

    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(messages) {message->
                        MessageItem(message = message)


                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(value = messageText.value,
                    onValueChange = { newValue ->
                        messageText.value = newValue
                    },
                    placeholder = { Text(text = "mesaj giriniz")},



                        )
                Button(
                    onClick = {mesaj_ekle(messageText.value.toString(),messageText)},
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("Send")
                }
            }
        }
    }






}


@Composable
fun MessageItem(message: Message) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "${message.gonderen}: ${message.mesaj}")
        }
    }

 }
    @RequiresApi(Build.VERSION_CODES.O)
    fun mesaj_ekle(a:String, messageText: MutableState<String>)
    { var kullaniciEmail = intent.getStringExtra("kullanici")



        if(a !=null )
        {
            val yenimesaj=Message(
                gonderen =kullaniciEmail,
                mesaj = a.toString(),
                zaman =Timestamp.now()

            )


            db.collection("chat")
                .add(yenimesaj)
                .addOnSuccessListener { documentReference ->
                    kullaniciEmail=""
                    messageText.value=""





                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "e", Toast.LENGTH_SHORT).show()


                }
        }


        else
        {
            Toast.makeText(this, "boş mesaj gonderilemez", Toast.LENGTH_SHORT).show()

        }



    }



}





