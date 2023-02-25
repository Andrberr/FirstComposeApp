package com.example.firstcomposeapp

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.ui.theme.FirstComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeAppTheme {
                //Greeting("Android")
                LoaderComponent(
                    Modifier.fillMaxSize(),
                    Color.Black.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@Preview
@Composable
fun LoaderComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    Surface(
        color = backgroundColor,
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
            Text(
                text = stringResource(id = R.string.loading),
                style = MaterialTheme.typography.caption,
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(all = 32.dp)
            )
        }
    }
}

@Composable
fun ErrorImage() {
    Image(
        painter = painterResource(id = R.drawable.cat),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .clip(RoundedCornerShape(percent = 25))
            .border(
                BorderStroke(
                    width = 8.dp,
                    color = MaterialTheme.colors.primary
                ),
                RoundedCornerShape(percent = 25)
            )
    )
}

@Composable
fun HeadingText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun TextWithBoldSuffix(
    prefix: String,
    suffix: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            append(prefix)
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(suffix)
            pop(0)
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ContactSupportText(
    onContactClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        append("Seeing this message too often?\n")
        append("Contact ")
        withStyle(
            SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = Color.Blue
            )
        ) {
            withAnnotation(
                tag = "help_tag",
                annotation = "https://support.google.com/sites/?hl=en"
            ) {
                append("tech support")
            }
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString
                .getStringAnnotations("help_tag", offset, offset)
                .firstOrNull()
                ?.let { annotation ->
                    annotation.item
                    onContactClicked()
                }
        }
    )
}
