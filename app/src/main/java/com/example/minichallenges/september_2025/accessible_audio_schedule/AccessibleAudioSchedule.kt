package com.example.minichallenges.september_2025.accessible_audio_schedule

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.september_2025.ParkinsansMedium
import com.example.minichallenges.september_2025.ParkinsansNormalRegular
import com.example.minichallenges.september_2025.ParkinsansSemiBold
import com.example.minichallenges.september_2025.Surface
import com.example.minichallenges.september_2025.SurfaceHigher
import com.example.minichallenges.september_2025.TextPrimary
import com.example.minichallenges.september_2025.TextSecondary
import com.example.minichallenges.september_2025.accessible_audio_schedule.ContainerState.*
import com.example.minichallenges.ui.theme.MiniChallengesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun AccessibleAudioSchedule() {
    val performances = listOf(
        Performance(
            "Bon Iver",
            //"15:30",
            "15:123456789012345678901234567890asdfggg",
            Venue.MAIN_STAGE,
            "Atmospheric folk-electronic set to start the day.",
            Genre.INDIE
        ),
        Performance(
            "Jamie xx",
            "16:00",
            Venue.ELECTRO_STAGE,
            "A genre-bending solo set with deep bass and light textures.",
            Genre.ELECTRONIC
        ),
        Performance(
            "Florence + The Machine",
            "17:00",
            Venue.MAIN_STAGE,
            "Special acoustic set this evening only.",
            Genre.HEADLINER
        ),
        Performance(
            "The National",
            "18:00",
            Venue.SUNSET_STAGE,
            "Known for their emotional rock anthems.",
            Genre.INDIE
        ),
        Performance(
            "Björk",
            "18:30",
            Venue.ELECTRO_STAGE,
            "Avant-garde visual and vocal performance.",
            Genre.EXPERIMENTAL
        ),
        Performance(
            "Tame Impala",
            "19:00",
            Venue.SUNSET_STAGE,
            "Celebrated psychedelic show from Australia.",
            Genre.INDIE
        ),
        Performance(
            "The Chemical Brothers",
            "20:15",
            Venue.ELECTRO_STAGE,
            "High-energy visuals with legendary electronica.",
            Genre.ELECTRONIC
        ),
        Performance(
            "Foo Fighters",
            "21:00",
            Venue.MAIN_STAGE,
            "Classic stadium rock at its finest.",
            Genre.HEADLINER
        ),
        Performance(
            "Arctic Monkeys",
            "22:00",
            Venue.SUNSET_STAGE,
            "Charismatic blend of indie rock and post-punk revival.",
            Genre.ALT_ROCK
        ),
        Performance(
            "Radiohead",
            "23:00",
            Venue.MAIN_STAGE,
            "Returning to the stage with a new album.",
            Genre.HEADLINER
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .padding(top = 28.dp)
    ) {
        item {
            Text(
                text = "Schedule",
                style = ParkinsansSemiBold.copy(
                    fontSize = 44.sp,
                    lineHeight = (44.sp * 0.9f),
                    color = TextPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(performances) { performance ->
            PerformanceCard(
                performance = performance,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun PerformanceCard(
    performance: Performance,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Text(
                    text = performance.genre.genreName,
                    modifier = Modifier
                        .background(
                            color = performance.genre.color,
                            shape = RoundedCornerShape(100.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    style = ParkinsansMedium.copy(
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalScrollableContainer(
                    modifier = Modifier.fillMaxWidth(),
                    artists = performance.artist,
                    timeAndStage = performance.time + " • " + performance.venue.venueName
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                Text(
                    text = performance.description,
                    style = ParkinsansNormalRegular.copy(
                        fontSize = 14.sp,
                        lineHeight = (14.sp * 1.05f),
                        color = TextSecondary
                    )
                )
            }
        }
    }
}

@Composable
fun HorizontalScrollableContainer(
    modifier: Modifier = Modifier,
    artists: String,
    timeAndStage: String,
    spacing: Dp = 24.dp,
    pauseMillis: Long = 1000L
) {
    val density = LocalDensity.current
    val scrollSpeedPxPerSecond = with(density) { 30.dp.toPx() }

    var containerState by remember { mutableStateOf(UNKNOWN) }
    var containerWidthPx by remember { mutableIntStateOf(0) }
    var contentWidthPx by remember { mutableIntStateOf(0) }

    val scrollState = rememberScrollState(initial = 0)
    var timeScrolled by remember { mutableIntStateOf(0) }

    LaunchedEffect(containerWidthPx, contentWidthPx) {
        if (containerWidthPx == 0 || contentWidthPx == 0) return@LaunchedEffect
        containerState = if (contentWidthPx < containerWidthPx) {
            FULL_SPACED_BETWEEN
        } else {
            SCROLLING
        }

        if (containerState != SCROLLING) return@LaunchedEffect

        scrollState.scrollTo(0)

        val overflowPx = contentWidthPx - containerWidthPx
        if (overflowPx > 0) {
            val duration = ((overflowPx / scrollSpeedPxPerSecond) * 1000f)
                .toInt()
                .coerceAtLeast(800)

            while (isActive && timeScrolled < 2) {
                delay(pauseMillis)
                // scroll to reveal the hidden tail
                scrollState.animateScrollTo(
                    value = overflowPx,
                    animationSpec = tween(durationMillis = duration, easing = LinearEasing)
                )
                delay(pauseMillis)
                // back to start
                scrollState.animateScrollTo(
                    value = 0,
                    animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
                )
                timeScrolled = timeScrolled + 1
            }
        } else {
            // no overflow -> ensure at start
            scrollState.scrollTo(0)
        }
    }

    when (containerState) {
        UNKNOWN -> {
            Box(
                modifier = modifier
                    .clipToBounds()
                    .onGloballyPositioned { containerWidthPx = it.size.width }
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.Start)
                        .onGloballyPositioned { contentWidthPx = it.size.width }
                ) {
                    TextArtist(artists)
                    Spacer(Modifier.width(spacing))
                    TextTimeAndStage(timeAndStage)
                }
            }
        }
        FULL_SPACED_BETWEEN -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextArtist(artists)
                Spacer(Modifier.weight(1f))
                TextTimeAndStage(timeAndStage)
            }
        }

        SCROLLING -> {
            Box(
                modifier = modifier
                    .clipToBounds()
                    .onGloballyPositioned { containerWidthPx = it.size.width }
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState, enabled = false) // programmatic only
                        .wrapContentWidth(align = Alignment.Start)
                        .onGloballyPositioned { contentWidthPx = it.size.width },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextArtist(artists)
                    Spacer(Modifier.width(spacing))
                    TextTimeAndStage(timeAndStage)
                }
            }
        }
    }
}

@Composable
fun TextArtist(
    artist: String
) {
    Text(
        text = artist,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        style = ParkinsansNormalRegular.copy(
            fontSize = 19.sp,
            lineHeight = (19.sp * 1f),
            color = TextPrimary
        )
    )
}

@Composable
fun TextTimeAndStage(
    timeAndStage: String
) {
    Text(
        text = timeAndStage,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        style = ParkinsansSemiBold.copy(
            fontSize = 19.sp,
            lineHeight = (19.sp * 1f),
            color = TextPrimary
        )
    )
}

enum class ContainerState {
    UNKNOWN,
    FULL_SPACED_BETWEEN,
    SCROLLING
}

@Preview(name = "PortraitSmall", widthDp = 300, heightDp = 672)
@Preview(name = "PortraitMedium", widthDp = 412, heightDp = 892)
@Preview(name = "PortraitLarge", widthDp = 600, heightDp = 892)
@Composable
fun MapChipFilterPreview() {
    MiniChallengesTheme {
        AccessibleAudioSchedule()
    }
}
