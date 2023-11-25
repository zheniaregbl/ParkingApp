package com.syndicate.parkingapp.ui.screens.splash_screen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.syndicate.parkingapp.R
import com.syndicate.parkingapp.ui.theme.GreenButton
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    isRegistered: Boolean = true,
    navigateToMap: () -> Unit = { },
    navigateToRegistration: () -> Unit = { }
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.logo)
    )
    var isPlaying by remember {
        mutableStateOf(true)
    }
    val showDevs = remember {
        mutableStateOf("")
    }
    val progress by animateLottieCompositionAsState(
        composition =  composition,
        isPlaying = isPlaying,
        iterations = 1,
        speed = 2.5f
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 1f) {
            isPlaying = false
            showDevs.value = "from syndicate"

            delay(1000L)

            if (!isRegistered) navigateToRegistration() else navigateToMap()
        }
    }

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(230.dp),
                composition = composition,
                progress = { progress }
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    50.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(
                targetState = showDevs.value,
                animationSpec = tween(200),
                label = ""
            ) { showDevs ->
                Text(
                    text = showDevs,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = GreenButton
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}