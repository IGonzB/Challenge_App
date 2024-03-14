package com.hsbc.challenge.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hsbc.challenge.R
import com.hsbc.challenge.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {

    val weatherInfo = viewModel.weatherInfo.collectAsState().value

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.location),
                    Modifier
                        .padding(top = 15.dp, start = 10.dp, end = 10.dp)
                        .width(95.dp),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = weatherInfo.place,
                    onValueChange = {},
                    modifier = Modifier.width(200.dp),
                    singleLine = true,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.temperature),
                    Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = weatherInfo.value.toString(),
                    onValueChange = {},
                    modifier = Modifier.width(200.dp),
                    singleLine = true
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
            ) {
                Button(
                    onClick = viewModel::requestNextWeatherInfo
                ) {
                    Text("Next Random Location")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun mainCardPreview() {
//    AppTheme {
//        mainCard()
//    }
//}