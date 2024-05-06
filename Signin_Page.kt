package com.example.message_app


import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable

import androidx.compose.runtime.MutableState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.message_app.ui.theme.Message_appTheme
import com.google.firebase.auth.FirebaseAuth





class Signin_Page:ComponentActivity() {
    


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent{
            Message_appTheme {
                Surface {
                    Gorunus()
                }


            }
        }
    }


@Composable
fun Gorunus() {



    val email = remember { mutableStateOf("") } // Initialize username state
    val password = remember { mutableStateOf("") }
    



    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black))
    {
        Text(
            text = "Kayıt Ol",
            fontSize = 50.sp,
            color = Color.White,
            modifier = Modifier
                .padding(top = 150.dp)
                .padding(all = 16.dp)

        )
        Column(modifier = Modifier
            .padding(all = 16.dp)
            .padding(top = 250.dp)

        )
        {
            TextField(value = email.value,
            onValueChange = {newvalue->email.value=newvalue},
            placeholder = { Text(text = "Eposta")},
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                ),
            leadingIcon = { // Leading icon
                IconButton(onClick = { /* Icon click action */ }) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = " ")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)

        )
            TextField(value = password.value,
                onValueChange = {newvalue->password.value=newvalue},
                placeholder = { Text(text = "Şifre")},
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation(),



                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),


                leadingIcon = { // Leading icon
                    IconButton(onClick = { /* Icon click action */ }) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = " ")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)

            )
            Button(onClick = {Kayitol(email,password)},
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),

                )
            {
                Text(text = "Kayıt Ol")
            }



        }

    }

}
   private fun Kayitol(a: MutableState<String>, b: MutableState<String>)
    {   var intent2= Intent( this@Signin_Page,MainActivity::class.java)
        val email =a.value
        val password=b.value
        val auth=FirebaseAuth.getInstance()

        try {
            if (email.isEmpty()||password.isEmpty())
            {

                Toast.makeText(this ,"lutfen alanları boş bırakmayınız", Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
                    if(task.isSuccessful)
                    {

                        Toast.makeText(this, "kayıt basarılı", Toast.LENGTH_SHORT).show()
                        startActivity(intent2)

                    }
                    else
                    {
                        Toast.makeText(this, "beklenmeyen bir hata olustu", Toast.LENGTH_SHORT).show()

                    }



                }
            }

        }
        catch (e:Exception)
        {
            Toast.makeText(this, "hata mesajı:${e.message}", Toast.LENGTH_SHORT).show()

        }
        finally
        {


        }



    }
}












