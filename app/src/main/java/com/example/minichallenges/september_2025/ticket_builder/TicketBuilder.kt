package com.example.minichallenges.september_2025.ticket_builder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R
import com.example.minichallenges.september_2025.ParkinsansMedium
import com.example.minichallenges.september_2025.ParkinsansNormalRegular
import com.example.minichallenges.september_2025.ParkinsansSemiBold
import com.example.minichallenges.september_2025.Surface
import com.example.minichallenges.september_2025.SurfaceHigher
import com.example.minichallenges.september_2025.TextPrimary
import com.example.minichallenges.september_2025.TextSecondary
import com.example.minichallenges.ui.bottomBarHeight
import com.example.minichallenges.ui.statusBarHeight
import com.example.minichallenges.ui.theme.MiniChallengesTheme

@Composable
fun TicketBuilder() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
            .padding(statusBarHeight(), bottom = bottomBarHeight())
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f).padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Spacer(Modifier.heightIn(8.dp, 32.dp))

            BasicText(
                text = stringResource(R.string.ticket_builder_title),
                modifier = Modifier.fillMaxWidth(),
                style = ParkinsansSemiBold.copy(
                    color = TextPrimary,
                    fontSize = 45.sp,
                    lineHeight = 40.sp,
                ),
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 15.sp,
                    maxFontSize = 45.sp
                )
            )

            Spacer(Modifier.heightIn(4.dp, 8.dp))

            BasicText(
                text = stringResource(R.string.ticket_builder_subtitle),
                modifier = Modifier.fillMaxWidth(),
                style = ParkinsansNormalRegular.copy(
                    color = TextSecondary
                ),
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 14.sp,
                    maxFontSize = 20.sp
                )
            )

            Spacer(Modifier.heightIn(8.dp, 32.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f).padding(horizontal = 4.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(SurfaceHigher, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp)
            ) {
                TicketBuilderTypes(
                    modifier = Modifier.fillMaxWidth()
                )

                //Spacer(Modifier.heightIn(3.dp,6.dp))

                TicketBuilderQuantity(
                    modifier = Modifier.fillMaxWidth()
                )

                //Spacer(Modifier.heightIn(3.dp,6.dp))

                TicketBuilderTotal(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
            ) {
                BasicText(
                    text = stringResource(R.string.ticket_builder_type_purchase),
                    modifier = Modifier.fillMaxWidth(),
                    style = ParkinsansSemiBold.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        lineHeight = 30.sp,
                        color = TextSecondary
                    ),
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 15.sp,
                        maxFontSize = 30.sp
                    )
                )
            }
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
fun TicketBuilderTotal(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = TextPrimary
        )

        Spacer(Modifier.heightIn(16.dp, 32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            BasicText(
                text = stringResource(R.string.ticket_builder_type_total),
                modifier = Modifier.fillMaxWidth(),
                style = ParkinsansMedium.copy(
                    fontSize = 28.sp,
                    lineHeight = 28.sp,
                    color = TextPrimary
                ),
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 14.sp,
                    maxFontSize = 28.sp
                )
            )
        }
    }
}

@Composable
fun TicketBuilderQuantity(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        BasicText(
            text = stringResource(R.string.ticket_builder_type_quantity),
            modifier = Modifier.fillMaxWidth(),
            style = ParkinsansMedium.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = TextSecondary
            ),
            autoSize = TextAutoSize.StepBased(
                minFontSize = 10.sp,
                maxFontSize = 20.sp
            )
        )

        Spacer(Modifier.heightIn(8.dp, 16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TicketBuilderQuantityButton()

            BasicText(
                text = "1",
                style = ParkinsansSemiBold.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    lineHeight = 32.sp,
                    color = TextPrimary
                ),
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 16.sp,
                    maxFontSize = 32.sp
                ),
                modifier = Modifier.weight(1f)
            )

            TicketBuilderQuantityButton()
        }
    }
}

@Composable
fun TicketBuilderQuantityButton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Surface, RoundedCornerShape(12.dp)).padding(14.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_minus_2),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun TicketBuilderTypes(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        BasicText(
            text = stringResource(R.string.ticket_builder_type),
            modifier = Modifier.fillMaxWidth(),
            style = ParkinsansMedium.copy(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = TextSecondary
            ),
            autoSize = TextAutoSize.StepBased(
                minFontSize = 14.sp,
                maxFontSize = 20.sp
            )
        )

        Spacer(Modifier.heightIn(8.dp, 16.dp))

        TicketBuilderType(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.heightIn(4.dp, 8.dp))

        TicketBuilderType(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.heightIn(4.dp, 8.dp))

        TicketBuilderType(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TicketBuilderType(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(0.dp)
    ) {
        RadioButton(
            selected = false,
            onClick = {  },
            Modifier.size(24.dp)
        )

        Spacer(Modifier.width(16.dp))

        BasicText(
            text = stringResource(R.string.ticket_builder_type),
            modifier = Modifier.fillMaxWidth(),
            style = ParkinsansMedium.copy(
                fontSize = 24.sp,
                lineHeight = 24.sp,
                color = TextPrimary
            ),
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp,
                maxFontSize = 24.sp
            )
        )
    }
}

@Preview(name = "PortraitSmall", widthDp = 300, heightDp = 672)
@Preview(name = "PortraitMedium", widthDp = 412, heightDp = 892)
@Preview(name = "PortraitLarge", widthDp = 600, heightDp = 892)
@Composable
fun TicketBuilderPreview() {
    MiniChallengesTheme {
        TicketBuilder()
    }
}