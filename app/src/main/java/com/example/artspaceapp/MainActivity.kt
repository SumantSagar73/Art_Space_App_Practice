package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.R
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpace()
                }
            }
        }
    }
}

@Composable
fun ArtSpace() {
    var currentArtworkIndex by remember { mutableStateOf(0) }
    val artworks = listOf(
        Artwork(R.drawable.android__berlin_wall_, R.string.berliner_android_title, R.string.author_dalle, R.string.twentytwentyone),
        Artwork(R.drawable.android__happy_party_animal_, R.string.party_animal_android_title, R.string.author_dalle, R.string.twentytwentyone),
        Artwork(R.drawable.android__humble_, R.string.humble_android_title, R.string.author_dalle, R.string.twentytwentytwo),
        Artwork(R.drawable.android__snowflake_, R.string.snowflake_android_title, R.string.author_dalle, R.string.twentytwentythree)
    )

    val currentArtwork = artworks[currentArtworkIndex]

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArtworkWall(painterResource(id = currentArtwork.imageRes))
        Spacer(modifier = Modifier.height(16.dp))
        ArtworkDescriptor(
            title = stringResource(id = currentArtwork.titleRes),
            artist = stringResource(id = currentArtwork.artistRes),
            year = stringResource(id = currentArtwork.yearRes)
        )
        Spacer(modifier = Modifier.height(16.dp))
        DisplayController(
            onPrevious = {
                currentArtworkIndex = (currentArtworkIndex - 1 + artworks.size) % artworks.size
            },
            onNext = {
                currentArtworkIndex = (currentArtworkIndex + 1) % artworks.size
            }
        )
    }
}

@Composable
fun ArtworkWall(image: Painter) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(15.dp, Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun ArtworkDescriptor(title: String, artist: String, year: String) {
    Card(

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Cyan),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = artist, fontSize = 20.sp)
            Text(text = year, fontSize = 16.sp)
        }
    }
}

@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ElevatedButton(
            onClick = onPrevious,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = colorResource(id = R.color.button_background_color),
                contentColor = colorResource(id = R.color.button_content_color)
            )
        ) {
            Text(text = stringResource(id = R.string.previous_button))
        }

        ElevatedButton(
            onClick = onNext,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = colorResource(id = R.color.button_background_color),
                contentColor = colorResource(id = R.color.button_content_color)
            )
        ) {
            Text(text = stringResource(id = R.string.next_button))
        }


    }
}

data class Artwork(
    val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val artistRes: Int,
    @StringRes val yearRes: Int
)

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceAppTheme {
        ArtSpace()
    }
}
