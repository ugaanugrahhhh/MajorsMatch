package capstone.product.tim.majorsmatch.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import capstone.product.tim.majorsmatch.R
import capstone.product.tim.majorsmatch.databinding.ActivityLoginBinding
import capstone.product.tim.majorsmatch.view.ViewModelFactory
import capstone.product.tim.majorsmatch.view.mycustom.CustomEditText
import capstone.product.tim.majorsmatch.view.story.ListStoryActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(context = this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var customEditText: CustomEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBarLogin.visibility
        binding.progressBarLogin.visibility = View.GONE
        customEditText = findViewById(R.id.ed_login_email)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            binding.progressBarLogin.visibility = View.VISIBLE
            binding.loginButton.isEnabled = false

            viewModel.login(email, password)
        }

        viewModel.getSession().observe(this@LoginActivity) { loginResponse ->
            loginResponse?.statusLoginUser?.let { userResult ->
                if (userResult) {
                    binding.progressBarLogin.visibility = View.GONE
                    val intent = Intent(this@LoginActivity, ListStoryActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            binding.loginButton.isEnabled = true
        }

        viewModel.errorLiveData.observe(this@LoginActivity) { errorMessage ->
            if (!errorMessage.isNullOrBlank()) {
                AlertDialog.Builder(this@LoginActivity).apply {
                    setTitle("Error")
                    setMessage(errorMessage)
                    setPositiveButton("OK", null)
                    create()
                    show()
                }
                binding.loginButton.isEnabled = true
            }
        }
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

}