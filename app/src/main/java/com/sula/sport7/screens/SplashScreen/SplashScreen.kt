package com.sula.sport7.screens.SplashScreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sula.sport7.navigation.Screens
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.sula.sport7.FakeScreenViewModel
import com.sula.sport7.R
import com.sula.sport7.data.Resource

@SuppressLint("ProduceStateDoesNotAssignValue")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashScreen(navController: NavController, fakeScreenViewModel: FakeScreenViewModel = hiltViewModel()) {
    val firebaseAnalytics = Firebase.analytics
    if (!fakeScreenViewModel.isFirstOpen) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param(FirebaseAnalytics.Param.ITEM_ID, "com.example.sportprediction")
            param(FirebaseAnalytics.Param.ITEM_NAME, "CasinoASO")
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "application")
        }
    }
    fakeScreenViewModel.isFirstOpen = false

    val link= produceState<Resource<String>>(initialValue = Resource.Loading() ){
        value=fakeScreenViewModel.getLink()
    }.value

    val hasNavigated = rememberSaveable { mutableStateOf(false) }

    when(link) {
        is Resource.Success -> {
            if (!hasNavigated.value) {
                Log.d("SplashScreen",link.data.toString())
               val exactLink = removeSubstring(link.data.toString())
                navController.navigate("${Screens.MainScreen.name}/${exactLink}") {
                    popUpTo(0)
                    launchSingleTop = true
                }
                hasNavigated.value = true
            }
        }

        is Resource.Loading -> {
            Surface(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.hock),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(0.9f))
                    DotsFlashing()
                    Spacer(modifier = Modifier.weight(0.1f))
                }
            }
        }

        else -> {

        }
    }
}
fun removeSubstring(inputString: String): String {
    val substring = "https://1xbet.kz/"
    return inputString.removePrefix(substring)
}

@Composable
fun DotsFlashing() {
    val minAlpha = 0.1f
    val delayUnit=300
    @Composable
    fun Dot(
        alpha: Float
    ) = Spacer(
        Modifier
            .size(20.dp)
            .alpha(alpha)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    )
    val infiniteTransition = rememberInfiniteTransition()


    @Composable
    fun animateAlphaWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = minAlpha,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                minAlpha at delay with LinearEasing
                1f at delay + delayUnit with LinearEasing
                minAlpha at delay + delayUnit * 2
            }
        )
    )
    val alpha1 by animateAlphaWithDelay(0)
    val alpha2 by animateAlphaWithDelay(delayUnit)
    val alpha3 by animateAlphaWithDelay(delayUnit * 2)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val spaceSize = 2.dp
        Dot(alpha1)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha2)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha3)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun SplashPreview(){
    SplashScreen(navController = rememberNavController())
}