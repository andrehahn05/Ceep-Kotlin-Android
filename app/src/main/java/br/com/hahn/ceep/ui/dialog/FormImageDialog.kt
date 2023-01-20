package br.com.hahn.ceep.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog.Builder
import br.com.hahn.ceep.databinding.FormImageBinding

import br.com.hahn.ceep.extensions.tryLoadImage

class FormImageDialog(private val context: Context) {

    fun showOff(
        urlDefault: String? = null ,
        handleUploadedImage: (image: String) -> Unit
    ) {
        FormImageBinding.inflate(LayoutInflater.from(context)).apply {

                urlDefault?.let {
                    formImageImageview.tryLoadImage(it)
                    formImageUrl.setText(it)
                }

                formImageBtnLoad.setOnClickListener {
                    val url = formImageUrl.text.toString()
                    formImageImageview.tryLoadImage(url)
                }

                Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formImageUrl.text.toString()
                        handleUploadedImage(url)
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }


    }

}