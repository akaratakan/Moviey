package com.aa.base.ui.root

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aa.model.generic.Magic
import timber.log.Timber

abstract class BaseFragment(layoutResId: Int) : Fragment(layoutResId) {


    open fun <T> submit(magic: Magic<T>?) {
        when (magic) {
            is Magic.Progress -> {
                Timber.e("Progress: $magic")
            }

            is Magic.Failure -> {
                Timber.e("Failure: $magic")
                magic.errorMessage.makeToast()
            }

            is Magic.Success -> {
                Timber.e("Success: $magic")
                "Succeed".makeToast()
            }

            else -> {
                Timber.e("init push for action use case")
            }
        }
    }

    fun String.makeToast() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
    }

}