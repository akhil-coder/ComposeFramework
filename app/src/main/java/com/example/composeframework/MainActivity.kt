package com.example.composeframework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.example.composeframework.ui.navigation.Screen
import com.example.composeframework.ui.theme.ComposeFrameWorkTheme
import com.example.core.util.Logger
import com.example.ui_herodetail.ui.HeroDetail
import com.example.ui_herodetail.ui.HeroDetailViewModel
import com.example.ui_herolist.ui.HeroList
import com.example.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logger = Logger("GetHerosTest", true)

        setContent {
            ComposeFrameWorkTheme {
                setNavigation()
            }
        }
    }

    @Composable
    private fun setNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController,
            startDestination = Screen.HeroList.route,
            builder = {
                addHeroList(
                    navController = navController,
                    imageLoader = imageLoader
                )
                addHeroDetail(imageLoader = imageLoader)
            })
    }
}

fun NavGraphBuilder.addHeroList(
    navController: NavController, imageLoader: ImageLoader
) {
    composable(
        route = Screen.HeroList.route
    ) {
        val viewModel: HeroListViewModel = hiltViewModel()
        HeroList(state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
            navigateToDetailScreen = { heroId -> navController.navigate("${Screen.HeroDetail.route}/$heroId") })
    }
}

fun NavGraphBuilder.addHeroDetail(
    imageLoader: ImageLoader,
) {
    composable(
        route = Screen.HeroDetail.route + "/{heroId}",
        arguments = Screen.HeroDetail.arguments
    ) {
        val viewModel: HeroDetailViewModel = hiltViewModel()
        HeroDetail(state = viewModel.state.value, imageLoader = imageLoader)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    ComposeFrameWorkTheme {
        Greeting(stringResource(R.string.androidstring))
    }
}