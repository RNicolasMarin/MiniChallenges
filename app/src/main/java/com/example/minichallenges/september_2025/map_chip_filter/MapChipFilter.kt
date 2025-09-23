package com.example.minichallenges.september_2025.map_chip_filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R
import com.example.minichallenges.september_2025.ParkinsansMedium
import com.example.minichallenges.september_2025.TextPrimary
import com.example.minichallenges.september_2025.TextSecondary
import com.example.minichallenges.september_2025.ParkinsansSemiBold
import com.example.minichallenges.september_2025.Surface
import com.example.minichallenges.september_2025.map_chip_filter.ChipFilter.*
import com.example.minichallenges.ui.theme.MiniChallengesTheme

@Composable
fun MapChipFilter() {
    var stages by remember { mutableStateOf(Stages(false)) }
    var food by remember { mutableStateOf(Food(false)) }
    var wc by remember { mutableStateOf(Wc(false)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.map_chip_filter_title),
                style = ParkinsansSemiBold.copy(
                    fontSize = 30.sp,
                    lineHeight = 27.sp
                ),
                color = TextPrimary,
            )

            Spacer(Modifier.height(20.dp))

            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    CustomChip(
                        chipFilter = stages,
                        onClick = {
                            stages = Stages(!stages.checked)
                        }
                    )
                }

                item {
                    CustomChip(
                        chipFilter = food,
                        onClick = {
                            food = Food(!food.checked)
                        }
                    )
                }

                item {
                    CustomChip(
                        chipFilter = wc,
                        onClick = {
                            wc = Wc(!wc.checked)
                        }
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = TextPrimary
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomImage(
                imageRes = R.mipmap.background_map,
                descriptionRes = R.string.map_chip_filter_title
            )
            if (stages.checked) {
                CustomImage(
                    imageRes = R.mipmap.background_stages,
                    descriptionRes = R.string.map_chip_filter_stages
                )
            }
            if (food.checked) {
                CustomImage(
                    imageRes = R.mipmap.background_food,
                    descriptionRes = R.string.map_chip_filter_food
                )
            }
            if (wc.checked) {
                CustomImage(
                    imageRes = R.mipmap.background_wc,
                    descriptionRes = R.string.map_chip_filter_wc
                )
            }
        }
    }
}

@Composable
fun CustomImage(
    imageRes: Int,
    descriptionRes: Int
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = stringResource(descriptionRes),
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CustomChip(
    onClick: () -> Unit,
    chipFilter: ChipFilter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(if (chipFilter.checked) chipFilter.backgroundColor else Color.Transparent)
            .border(1.dp, if (chipFilter.checked) chipFilter.backgroundColor else TextSecondary, RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = chipFilter.iconRes),
                tint = if (chipFilter.checked) TextPrimary else TextSecondary,
                contentDescription = stringResource(chipFilter.textRes),
                modifier = Modifier
                    .size(16.dp)
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = stringResource(chipFilter.textRes),
                style = ParkinsansMedium.copy(
                    fontSize = 20.sp,
                    lineHeight = 20.sp
                ),
                color = if (chipFilter.checked) TextPrimary else TextSecondary
            )
        }
    }
}

@Preview(name = "PortraitSmall", widthDp = 300, heightDp = 672)
@Preview(name = "PortraitMedium", widthDp = 412, heightDp = 892)
@Preview(name = "PortraitLarge", widthDp = 600, heightDp = 892)
@Composable
fun MapChipFilterPreview() {
    MiniChallengesTheme {
        MapChipFilter()
    }
}