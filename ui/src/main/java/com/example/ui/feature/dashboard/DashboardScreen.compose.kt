package com.example.ui.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.match.model.MatchModel
import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.user.model.UserModel
import com.example.ui.R.drawable as RDrawable
import com.example.ui.feature.dashboard.provider.mockMatches
import com.example.ui.feature.dashboard.provider.mockPlayers
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenView(
    user: UserModel,
    club: ClubModel
) {
    val dashboardViewmodel = koinViewModel<DashboardViewModel>()
    val dashboardState by dashboardViewmodel.state.collectAsStateWithLifecycle()
    val clubId = club.id.toString()

    LaunchedEffect(Unit) { dashboardViewmodel.loadData(clubId = clubId) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "MATCHDAY") })
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "© Peña Deportiva",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    ) { innerPadding ->

        when (val state = dashboardState) {
            is DashboardState.Loading -> CustomLoadingView(padding = innerPadding)
            is DashboardState.Error -> ErrorView(padding = innerPadding, message = "ERROR")
            is DashboardState.Success -> {
                DashboardContent(innerPadding = innerPadding, user = user, club = club, state = state)
            }
            is DashboardState.Empty -> EmptyView(padding = innerPadding)
        }
    }
}

@Composable
fun DashboardContent(
    innerPadding: PaddingValues,
    user: UserModel,
    club: ClubModel,
    state: DashboardState.Success
) {
    val dashboardViewmodel = koinViewModel<DashboardViewModel>()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { ClubHeader(club) }
        item { SectionTitle("PARTIDOS JUGADOS") }
        item { MatchesSection(state.matches, dashboardViewmodel) }
        item { ActionButtons(user, club, state, dashboardViewmodel) }
        item { SectionTitle("JUGADORES") }
//        items(state.players) { player ->
        items(mockPlayers) { player ->
            PlayerListItem(player = player)
        }
        item { SuccessSnackbar(state.successMessage, dashboardViewmodel) }
    }
}

@Composable
fun CustomLoadingView(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(padding: PaddingValues, message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: $message")
    }
}

@Composable
fun EmptyView(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No hay datos disponibles")
    }
}

@Composable
fun ClubHeader(club: ClubModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        club.logoUrl?.let { logo ->
            AsyncImage(
                model = logo,
                contentDescription = "Logo del club",
                modifier = Modifier.size(64.dp).clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = club.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun MatchesSection(
    matches: List<MatchModel>,
    viewModel: DashboardViewModel
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        items(matches) { match ->
        items(mockMatches) { match ->
            MatchCardItem(
                match = match,
                onClick = { viewModel.selectMatch(match.id.toString()) }
            )
        }
    }
}

@Composable
fun MatchCardItem(
    match: MatchModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(220.dp)
            .height(120.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = match.date.toFormattedDate(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(id = RDrawable.ic_football_shirt_1),
                    contentDescription = "Equipo blanco",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )

                Text(
                    text = "${match.whiteTeamGoals} - ${match.blueTeamGoals}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Icon(
                    painter = painterResource(id = RDrawable.ic_football_shirt_1),
                    contentDescription = "Equipo azul",
                    tint = Color.Blue,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun PlayerListItem(player: PlayerModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!player.photoUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = player.photoUrl,
                    contentDescription = "Foto de ${player.name}",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Icono jugador",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = player.position.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ActionButtons(
    user: UserModel,
    club: ClubModel,
    state: DashboardState.Success,
    viewModel: DashboardViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (user.rol == "admin") {
            Button(
                onClick = {
                    viewModel.createMatch(
                        userId = user.id.toString(),
                        number = (state.matches.size + 1),
                        date = System.currentTimeMillis(),
                        clubId = club.id.toString()
                    )
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Crear partido")
            }
        }

        Button(
            onClick = {
                val matchId = state.selectedMatchId
                if (matchId != null) {
                    viewModel.addPlayerToMatch(
                        matchId = matchId,
                        playerId = user.id.toString(),
                        team = state.selectedTeam
                    )
                }
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Unirse al partido")
        }
    }
}

@Composable
fun SuccessSnackbar(message: String?, viewModel: DashboardViewModel) {
    message?.let { message ->
        LaunchedEffect(message) {
            delay(3000)
            viewModel.clearMessage()
        }
        Snackbar(modifier = Modifier.padding(16.dp)) {
            Text(text = message)
        }
    }
}

fun Long.toFormattedDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}