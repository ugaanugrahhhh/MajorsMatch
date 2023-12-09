package capstone.product.tim.majorsmatch.view.mycustom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import capstone.product.tim.majorsmatch.R


class CustomEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var personIcon: Drawable
    private lateinit var emailIcon: Drawable
    private lateinit var passwordIcon: Drawable


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        personIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_person_24) as Drawable
        emailIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable
        passwordIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24) as Drawable

        setOnTouchListener(this)

        when (id) {
            R.id.ed_register_name -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().isEmpty()) {
                            this@CustomEditText.error = resources.getString(R.string.error_name)
                        }
                    }
                })
            }

            R.id.ed_register_email-> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val email = s.toString().trim()
                        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        if (!isEmailValid) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_email_format)
                        }
                    }
                })
            }

            R.id.ed_register_password-> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (this@CustomEditText.text?.trim().toString().length < 8) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_password_length)
                        }
                    }
                })
            }

            R.id.ed_login_email -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val email = s.toString().trim()
                        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        if (!isEmailValid) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_email_format)
                        }
                    }

                })
            }

            R.id.ed_login_password -> {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (this@CustomEditText.text?.trim().toString().length < 8) {
                            this@CustomEditText.error =
                                resources.getString(R.string.error_password_length)
                        }
                    }
                })
            }
        }
    }

    private fun hideClearButton() {
        when (id) {
            R.id.ed_register_name -> {
                setButtonDrawable(startOfTheText = personIcon)
            }

            R.id.ed_register_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_register_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }

            R.id.ed_login_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_login_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }
        }
    }

    private fun showClearButton() {
        when (id) {
            R.id.ed_register_name -> {
                setButtonDrawable(startOfTheText = personIcon)
            }

            R.id.ed_register_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_register_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }

            R.id.ed_login_email -> {
                setButtonDrawable(startOfTheText = emailIcon)
            }

            R.id.ed_login_password -> {
                setButtonDrawable(startOfTheText = passwordIcon)
            }
        }
    }

    private fun setButtonDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        return false
    }
}