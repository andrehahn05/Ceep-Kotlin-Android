package br.com.hahn.ceep.extensions

import android.widget.ImageView
import br.com.hahn.ceep.R
import coil.load

fun ImageView.tryLoadImage(
    url: String? = null,
    fallback: Int = R.drawable.image_default
) {
    load(url) {
        placeholder(R.drawable.placeholder)
        error(R.drawable.err)
        fallback(fallback)
    }
}