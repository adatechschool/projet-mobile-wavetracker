package com.example.wavetrackercompose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row {
        (1..5).forEach { index ->
            StarIcon(
                filled = index <= rating,
                onToggle = { onRatingChanged(index) }
            )
        }
        Log.d("StarRating", "Rating: $rating")
    }
}

@Composable
fun StarIcon(
    filled: Boolean,
    onToggle: () -> Unit
) {
    val iconFilled = Icons.Default.Star
    val iconOutlined = Icons.Outlined.Star

    Log.d("StarIcon", "Filled: $filled, Icon: ${if (filled) "Filled.Star" else "Outlined.Star"}")

    Image(
        imageVector = if (filled) iconFilled else iconOutlined,
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
            .padding(2.dp)
            .clip(CircleShape)
            .toggleable(value = filled, onValueChange = { onToggle() })
    )
}

@Preview
@Composable
fun StarRatingPreview() {
    StarRating(rating = 3, onRatingChanged = {})
}
