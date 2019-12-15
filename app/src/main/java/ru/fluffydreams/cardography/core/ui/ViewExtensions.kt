package ru.fluffydreams.cardography.core.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.simplepass.loadingbutton.animatedDrawables.ProgressType
import br.com.simplepass.loadingbutton.customViews.ProgressButton
import ru.fluffydreams.cardography.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun SwipeRefreshLayout.startRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


//region ProgressButton
//==============================================================================================
fun ProgressButton.startIndeterminateProgress() {
    progressType = ProgressType.INDETERMINATE
    startAnimation()
}

fun ProgressButton.morphDone(
    context: Context,
    fillColor: Int = defaultColor(context),
    bitmap: Bitmap = defaultDoneImage(context)
) = doneLoadingAnimation(fillColor, bitmap)

private fun defaultColor(context: Context) = ContextCompat.getColor(context, R.color.colorAccent)

private fun defaultDoneImage(context: Context) =
    getBitmapFromVectorDrawable(context, R.drawable.ic_check_white_24dp)
//==============================================================================================
//endregion


//region Bitmap
//==============================================================================================
fun getBitmapFromVectorDrawable(context: Context, @DrawableRes drawableResId: Int): Bitmap {
    val drawable = AppCompatResources.getDrawable(context, drawableResId)?.let {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            (DrawableCompat.wrap(it)).mutate()
        }else {
            it
        }
    }
    return drawable?.let { getBitmap(drawable) } ?:
    throw IllegalArgumentException("Drawable not exists")
}

private fun getBitmap(drawable: Drawable): Bitmap {
    with(drawable) {
        val bitmap = Bitmap.createBitmap(
            intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        return bitmap
    }
}
//==============================================================================================
//endregion