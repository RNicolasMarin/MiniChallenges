package com.example.minichallenges.september_2025.ticket_builder

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R
import com.example.minichallenges.september_2025.Lime
import com.example.minichallenges.september_2025.ParkinsansMedium
import com.example.minichallenges.september_2025.ParkinsansNormalRegular
import com.example.minichallenges.september_2025.ParkinsansSemiBold
import com.example.minichallenges.september_2025.Surface
import com.example.minichallenges.september_2025.SurfaceHigher
import com.example.minichallenges.september_2025.TextDisabled
import com.example.minichallenges.september_2025.TextPrimary
import com.example.minichallenges.september_2025.TextSecondary
import com.example.minichallenges.september_2025.ticket_builder.TicketType.*
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
        TicketBuilderTitles()

        TicketBuilderModalAndButton()
    }
}

@Composable
fun ColumnScope.TicketBuilderTitles(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        VerticalSpace(8.dp, 32.dp)

        CustomText(
            R.string.ticket_builder_title,
            style = ParkinsansSemiBold,
            color = TextPrimary,
            min = 15.sp,
            max = 45.sp,
            lineHeight = 40.sp,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(4.dp, 8.dp)

        CustomText(
            R.string.ticket_builder_subtitle,
            style = ParkinsansNormalRegular,
            color = TextSecondary,
            min = 14.sp,
            max = 20.sp,
            lineHeight = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(8.dp, 32.dp)
    }
}

@Composable
fun ColumnScope.TicketBuilderModalAndButton(
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableIntStateOf(1) }
    var type by remember { mutableStateOf<TicketType?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .weight(3f)
            .padding(horizontal = 4.dp)
    ) {
        TicketBuilderModal(
            quantity = quantity,
            onSelected = {
                type = it
            },
            type = type,
            increaseQuantity = {
                quantity = quantity + 1
            },
            decreaseQuantity = {
                quantity = quantity - 1
            },
        )

        VerticalSpace(4.dp)

        TicketBuilderButton(
            enable = quantity > 0 && type != null
        )

        VerticalSpace(4.dp)
    }
}

@Composable
fun ColumnScope.TicketBuilderModal(
    quantity: Int,
    type: TicketType?,
    onSelected: (TicketType) -> Unit,
    increaseQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
            .background(SurfaceHigher, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
    ) {
        TicketBuilderTypes(
            type = type,
            onSelected = onSelected,
            modifier = Modifier.fillMaxWidth()
        )

        TicketBuilderQuantity(
            quantity = quantity,
            increaseQuantity = increaseQuantity,
            decreaseQuantity = decreaseQuantity,
            modifier = Modifier.fillMaxWidth()
        )

        TicketBuilderTotal(
            quantity = quantity,
            type = type,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TicketBuilderTypes(
    type: TicketType?,
    onSelected: (TicketType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CustomText(
            R.string.ticket_builder_type,
            style = ParkinsansMedium,
            color = TextSecondary,
            min = 14.sp,
            max = 20.sp,
            lineHeight = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(8.dp, 16.dp)

        TicketBuilderType(
            selected = type,
            onSelected = onSelected,
            ticketType = STANDARD,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(4.dp, 8.dp)

        TicketBuilderType(
            selected = type,
            onSelected = onSelected,
            ticketType = VIP,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(4.dp, 8.dp)

        TicketBuilderType(
            selected = type,
            onSelected = onSelected,
            ticketType = BACKSTAGE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TicketBuilderType(
    selected: TicketType?,
    onSelected: (TicketType) -> Unit,
    ticketType: TicketType,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = TextPrimary,
                unselectedColor = TextPrimary
            ),
            selected = selected == ticketType,
            onClick = {
                onSelected(ticketType)
            },
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(8.dp))

        CustomText(
            ticketType.textRes,
            style = ParkinsansMedium,
            color = TextPrimary,
            min = 12.sp,
            max = 24.sp,
            lineHeight = 24.sp,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(8.dp))

        CustomText(
            "$${ticketType.price}",
            style = ParkinsansSemiBold,
            color = TextPrimary,
            min = 12.sp,
            max = 24.sp,
            lineHeight = 24.sp,
            modifier = Modifier
        )
    }
}

@Composable
fun TicketBuilderQuantity(
    quantity: Int,
    increaseQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CustomText(
            R.string.ticket_builder_type_quantity,
            style = ParkinsansMedium,
            color = TextSecondary,
            min = 10.sp,
            max = 20.sp,
            lineHeight = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpace(8.dp, 16.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TicketBuilderQuantityButton(
                enable = quantity > 1,
                iconRes = R.drawable.ic_minus_2,
                onClick = decreaseQuantity
            )

            CustomText(
                quantity.toString(),
                style = ParkinsansSemiBold.copy(
                    textAlign = TextAlign.Center
                ),
                color = TextPrimary,
                min = 16.sp,
                max = 32.sp,
                lineHeight = 32.sp,
                modifier = Modifier.weight(1f)
            )

            TicketBuilderQuantityButton(
                enable = true,
                iconRes = R.drawable.ic_plus,
                onClick = increaseQuantity
            )
        }
    }
}

@Composable
fun TicketBuilderQuantityButton(
    enable: Boolean,
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(if (enable) Surface else SurfaceHigher, RoundedCornerShape(12.dp))
            .padding(14.dp)
            .clickable(
                enabled = enable,
                onClick = onClick
            )
    ) {
        Icon(
            painter = painterResource(iconRes),
            tint = if (enable) TextPrimary else TextDisabled,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun TicketBuilderTotal(
    quantity: Int,
    type: TicketType?,
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

        VerticalSpace(16.dp, 32.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            CustomText(
                R.string.ticket_builder_type_total,
                style = ParkinsansMedium,
                color = TextPrimary,
                min = 14.sp,
                max = 28.sp,
                lineHeight = 28.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            val total = type?.let {
                it.price * quantity
            } ?: "0"

            CustomText(
                "$$total",
                style = ParkinsansSemiBold,
                color = TextPrimary,
                min = 14.sp,
                max = 28.sp,
                lineHeight = 28.sp,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun TicketBuilderButton(
    enable: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = enable,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Lime,
            disabledContainerColor = SurfaceHigher,
            contentColor = TextPrimary,
            disabledContentColor = TextDisabled
        ),
        modifier = modifier.fillMaxWidth(),
        onClick = {},
    ) {
        CustomText(
            R.string.ticket_builder_type_purchase,
            style = ParkinsansSemiBold.copy(
                textAlign = TextAlign.Center
            ),
            color = if (enable) TextPrimary else TextDisabled,
            min = 15.sp,
            max = 30.sp,
            lineHeight = 30.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}





@Composable
fun VerticalSpace(min: Dp, max: Dp = min) {
    Spacer(Modifier.heightIn(min, max))
}

@Composable
fun CustomText(
    textRes: Int,
    style: TextStyle,
    color: Color,
    min: TextUnit,
    max: TextUnit,
    lineHeight: TextUnit,
    modifier : Modifier = Modifier
) {
    BasicText(
        text = stringResource(textRes),
        modifier = modifier,
        style = style.copy(
            color = color,
            fontSize = max,
            lineHeight = lineHeight,
        ),
        autoSize = TextAutoSize.StepBased(
            minFontSize = min,
            maxFontSize = max
        )
    )
}

@Composable
fun CustomText(
    text: String,
    style: TextStyle,
    color: Color,
    min: TextUnit,
    max: TextUnit,
    lineHeight: TextUnit,
    modifier : Modifier = Modifier
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(
            color = color,
            fontSize = max,
            lineHeight = lineHeight,
        ),
        autoSize = TextAutoSize.StepBased(
            minFontSize = min,
            maxFontSize = max
        )
    )
}

enum class TicketType(val textRes: Int, val price: Int){
    STANDARD(R.string.ticket_builder_type_standard, 40),
    VIP(R.string.ticket_builder_type_vip, 70),
    BACKSTAGE(R.string.ticket_builder_type_backstage, 120)
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