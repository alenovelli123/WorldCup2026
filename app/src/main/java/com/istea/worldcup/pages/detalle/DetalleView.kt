package com.istea.worldcup.pages.detalle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.istea.worldcup.R
import com.istea.worldcup.domain.Group
import com.istea.worldcup.domain.Match


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleView (
    modifier: Modifier = Modifier,
    state : DetalleState,
    onAction: (DetalleIntencion)->Unit
) {

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(DetalleIntencion.CargarContenido)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = { Text(text = "Partidos") },
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(DetalleIntencion.IrParaAtras) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            BackgroundImage()
            when (state) {
                DetalleState.Cargando -> Cargando()
                is DetalleState.Error -> Text(text = state.mensaje)
                is DetalleState.Resultado -> Contenido(state.grupo)
                DetalleState.Vacio -> Text(text = "")
            }
        }
    }
}

@Composable
fun Cargando() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.size(160.dp),
            strokeWidth = 20.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Composable
fun Contenido(grupo: Group){
    Column {
        Text(
            grupo.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        MatchesListView(matches = grupo.matches)
    }
}

@Composable
fun MatchesListView(
    modifier: Modifier = Modifier,
    matches: List<Match>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(matches) { match ->
            MatchCard(match)
        }
    }
}

@Composable
fun MatchCard(match: Match) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = match.home,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Text("vs", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = match.away,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Fecha: ${match.date}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(R.drawable.logo_2026),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(vertical = 60.dp)
            .fillMaxSize()
    )
}