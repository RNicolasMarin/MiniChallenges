package com.example.minichallenges.september_2025.expandable_lineup_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R
import com.example.minichallenges.september_2025.Lime
import com.example.minichallenges.september_2025.Orange
import com.example.minichallenges.september_2025.ParkinsansMedium
import com.example.minichallenges.september_2025.ParkinsansNormalRegular
import com.example.minichallenges.september_2025.ParkinsansSemiBold
import com.example.minichallenges.september_2025.Pink
import com.example.minichallenges.september_2025.Surface
import com.example.minichallenges.september_2025.TextPrimary
import com.example.minichallenges.september_2025.TextSecondary
import com.example.minichallenges.ui.statusBarHeight

@Composable
fun ExpandableLineupList() {

    val stages by remember {
        mutableStateOf(
            listOf(
                Stage(
                    stageName = "Stage A",
                    color = Lime,
                    performers = listOf(
                        Performer("Morning Bloom", "11:00"),
                        Performer("Synth River", "12:30")
                    )
                ),
                Stage(
                    stageName = "Stage B",
                    color = Orange,
                    performers = listOf(
                        Performer("The Suntones", "13:00"),
                        Performer("Blue Voltage", "14:15"),
                        Performer("Midnight Echo", "15:30")
                    )
                ),
                Stage(
                    stageName = "Stage C",
                    color = Pink,
                    performers = listOf(
                        Performer("Echo Machine", "16:00"),
                        Performer("The 1975", "17:15")
                    )
                )
            )
        )
    }

    var selected by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize().background(Surface).padding(horizontal = 4.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(statusBarHeight() + 28.dp))

                Text(
                    text = stringResource(R.string.expandable_lineup_list_title),
                    style = ParkinsansSemiBold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.expandable_lineup_list_subtitle),
                    style = ParkinsansNormalRegular,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        items(
            items = stages,
            key = { stage -> stage.stageName },
        ) {
            ExpandableLineupListStage(
                expanded = it.stageName == selected,
                onClick = {
                    selected = if (selected == it.stageName) "" else it.stageName
                },
                stage = it
            )
        }
    }
}

@Composable
fun ExpandableLineupListStage(
    expanded: Boolean,
    onClick: () -> Unit,
    stage: Stage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = stage.color),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 40.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stage.stageName,
                        style = ParkinsansSemiBold.copy(
                            fontSize = 38.sp,
                            lineHeight = 38.sp,
                        ),
                        color = TextPrimary
                    )
                    Icon(
                        painter = painterResource(if (expanded) R.drawable.ic_minus else R.drawable.ic_plus),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }

                if (expanded) {
                    Spacer(modifier = Modifier.height(40.dp))

                    stage.performers.onEachIndexed { position, performer ->
                        ExpandableLineupListPerformer(
                            performer = performer,
                            isLast = position == stage.performers.size - 1
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }

}

@Composable
fun ExpandableLineupListPerformer(
    performer: Performer,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = performer.performerName,
                style = ParkinsansMedium,
                color = TextPrimary
            )
            Text(
                text = performer.time,
                style = ParkinsansSemiBold.copy(
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                ),
                color = TextPrimary
            )
        }
        if (!isLast) {
            Spacer(modifier = Modifier.height(28.dp))

            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(TextPrimary)
            )

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}