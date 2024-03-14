import android.content.Context
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hsbc.challenge.model.WeatherRepositoryLocal
import com.hsbc.challenge.util.common.ErrorTypeToErrorTextConverter
import com.hsbc.challenge.util.common.ErrorTypeToErrorTextConverterImpl
import com.hsbc.challenge.view.compose.MainScreen
import com.hsbc.challenge.view.theme.AppTheme
import com.hsbc.challenge.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherAppTest {
    @get:Rule
    val rule = createComposeRule()

    lateinit var instrumentationContext: Context
    lateinit var converter: ErrorTypeToErrorTextConverter

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        converter = ErrorTypeToErrorTextConverterImpl()
    }

    @Test
    fun performClick_seeWeatherInfo() {

        rule.setContent {
            AppTheme {
                MainScreen(MainViewModel(WeatherRepositoryLocal(instrumentationContext), converter))
            }
        }

        rule.waitUntil {
            rule
                .onAllNodesWithText("Next Random Location")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // do something
        rule.onNodeWithText("Next Random Location").performClick()

        // Check something
        rule.waitUntil {
            rule
                .onAllNodesWithText("Next Random Location")
                .fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithTag("Location").assertExists()
        rule.onNodeWithTag("Temperature").assertExists()
        rule.onNodeWithText("Location").assert(hasText("abcdefghijklmnopqrstvwxyz"))
        rule.onNodeWithText("Temperature").assert(hasText("abcdefghijklmnopqrstvwxyz"))

    }
}