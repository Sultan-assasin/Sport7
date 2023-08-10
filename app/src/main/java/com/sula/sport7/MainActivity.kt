package com.sula.sport7

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.sula.sport7.navigation.AppNavigation
import com.sula.sport7.ui.theme.Sport7Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sport7Theme {
                AppNavigation()
            }
        }
    }
}

//suspend fun getData(): String {
//    val db = FirebaseFirestore.getInstance()
//
//    val docRef = db.collection("links").document("link")
//
//
//    val document = docRef.get()
//        .await() // Подразумевается, что await() - это функция расширения для Task<DocumentSnapshot>
//
//    val urlString = document.getString("project") ?: ""
//
//    return urlString
//
//}

