import android.content.Context
import android.os.Build
import android.view.ViewConfiguration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity

/**
 * Почему-то material bottom sheet 3 на версиях апи 30-
 * не видит нижний инсет
 * поэтому такой вот хак
 * */
fun Modifier.bottomSheetNavigationPadding(): Modifier = composed {
    if (Build.VERSION.SDK_INT >= 30) {
        this.navigationBarsPadding()
    } else {
        val insets = WindowInsets.navigationBars.asPaddingValues()
        val bottom = insets.calculateBottomPadding()
        if (bottom.value > 0) {
            this.navigationBarsPadding()
        } else {
            val bottomInset = getNavBarHeightPx(LocalContext.current)
            this.padding(bottom = bottomInset.pxToDp())
        }
    }
}

@Composable
private fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

private fun getNavBarHeightPx(context: Context): Int {
    val res = context.resources
    val hasMenuKey: Boolean = ViewConfiguration.get(context).hasPermanentMenuKey()
    val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0 && !hasMenuKey) {
        res.getDimensionPixelSize(resourceId)
    } else 0
}
