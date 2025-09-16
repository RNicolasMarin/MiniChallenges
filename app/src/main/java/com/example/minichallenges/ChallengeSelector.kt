package com.example.minichallenges

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.minichallenges.august_2025.SurfaceHigher
import com.example.minichallenges.august_2025.TextPrimary
import com.example.minichallenges.august_2025.TextSecondary
import com.example.minichallenges.ui.theme.HostGroteskMedium
import com.example.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.minichallenges.ui.theme.HostGroteskSemiBold

@Composable
fun ChallengeSelector(
    challenges: List<Challenge>,
    title: Int,
    onClick: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(8.dp)
    ) {
        item {
            ChallengeSelectorTitle(
                title = title
            )
        }
        items(
            items = challenges,
            key = { challenge -> challenge.number }
        ) { challenge ->
            ChallengeItem(
                challenge = challenge,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClick(challenge.screen)
                }
            )
        }
    }
}

@Composable
fun ChallengeSelectorTitle(
    title: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.mini_challenges),
            style = HostGroteskSemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stringResource(title),
            style = HostGroteskSemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ChallengeItem(
    challenge: Challenge,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = stringResource(challenge.nameRes),
                    style = HostGroteskSemiBold,
                    color = TextPrimary,
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(challenge.topicsRes),
                    style = HostGroteskMedium,
                    color = TextSecondary,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(challenge.descriptionRes),
                    style = HostGroteskNormalRegular,
                    color = TextSecondary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }

}