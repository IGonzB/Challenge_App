package com.hsbc.challenge.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.hsbc.challenge.App
import com.hsbc.challenge.R
import com.hsbc.challenge.model.Data
import com.hsbc.challenge.util.common.UiDataState
import com.hsbc.challenge.view.compose.ErrorView
import com.hsbc.challenge.view.compose.LoadingView
import com.hsbc.challenge.view.theme.AppTheme
import com.hsbc.challenge.viewmodel.HomeIntent
import com.hsbc.challenge.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        (this.application as App).appComponent.viewModelFactory()
    }

    private fun observeLiveData(){
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it){
                    is DashboardState.Idle -> {
                        Log.d("Worked", "Idle")
                    }
                    is DashboardState.Loading -> {
                        handleProgressbar(View.VISIBLE)
                        Log.d("Worked", "Loading")
                    }
                    is DashboardState.Books -> {
                        handleProgressbar(View.GONE)
                        Toast.makeText(applicationContext, "Books ${it.book.size}", Toast.LENGTH_LONG).show()
                    }
                    is DashboardState.Error -> {
                        handleProgressbar(View.GONE)
                        Log.d("Worked", "Error")
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }

    @Composable
    fun MainScreen(viewModel: MainViewModel) {
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

                when (uiDataState.value) {
                    is UiDataState.Initial -> MainContentInitial(viewModel = viewModel)
                    is UiDataState.Loading -> LoadingView()
                    is UiDataState.Loaded -> MainContent(viewModel, (uiDataState.value as UiDataState.Loaded).data)
                    is UiDataState.Error -> ErrorView((uiDataState.value as UiDataState.Error).error) {
                        viewModel.requestNextWeatherInfo()
                    }
                }
            }
        }
    }

    @Composable
    fun MainContent(viewModel: MainViewModel, data: Data) {
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
                value = data.place,
                onValueChange = {},
                modifier = Modifier
                    .width(200.dp)
                    .testTag("Location"),
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
                value = data.value.toString(),
                onValueChange = {},
                modifier = Modifier
                    .width(200.dp)
                    .testTag("Temperature"),
                singleLine = true
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 30.dp)
        ) {
            Button(
                onClick = {
                    lifecycleScope.launch{
                        viewModel.userIntent.send(HomeIntent.FetchNextWeather)
                    }
                }
            ) {
                Text("Next Random Location")
            }
        }
    }

    @Composable
    fun MainContentInitial(viewModel: MainViewModel) {
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
                value = "",
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
                value = "",
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
                onClick = {
                    lifecycleScope.launch{
                        viewModel.userIntent.send(HomeIntent.FetchNextWeather)
                    }
                }
            ) {
                Text("Next Random Location")
            }
        }
    }
}


