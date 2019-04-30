package br.com.broscoder.tips.extensions

import android.support.design.widget.TextInputLayout
import android.text.InputType
import br.com.broscoder.tips.R
import java.util.regex.Pattern

fun TextInputLayout.validation():Boolean{

    val type = this.editText?.inputType?.minus(1)

    val result = when(type) {

        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {

            val emailPattern = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
            val pattern = Pattern.compile(emailPattern)
            if(pattern.matcher(this.editText?.text.toString()).matches()) {
                this.isErrorEnabled = false
                true
                } else {
                this.error = resources.getString(R.string.emailNotValid)
                false
                }
            }

        InputType.TYPE_TEXT_VARIATION_PASSWORD -> {

            if (this.editText?.text.toString().length < resources.getInteger(R.integer.sizePasswordMin)) {
                this.isErrorEnabled = true
                this.error = resources.getString(R.string.passwordSizeNotValid)
                false
            } else {
                this.isErrorEnabled = false
                true
            }
        }
        else -> {

            if (this.editText?.text.toString().isEmpty() || this.editText?.text.toString().isBlank()) {
                this.isErrorEnabled = true
                this.error = resources.getString(R.string.textEmptyOrBlank)
                false
            } else {
                this.isErrorEnabled = false
                true
            }

        }
    }

    return result
}
