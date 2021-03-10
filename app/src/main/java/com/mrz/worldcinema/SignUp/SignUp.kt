package com.mrz.worldcinema.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.gson.GsonBuilder
import com.mrz.worldcinema.R
import com.mrz.worldcinema.SignIn.SignIn
import com.mrz.worldcinema.api.ApiRequest
import com.mrz.worldcinema.constants.Constants.Companion.BASE_URL
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etSignUpEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etSignUpEmail.text.toString()).matches()) {
                    etSignInEmail.setError("Неверный e-mail")
                }
            }
        })

        if (etSignUpName.getText().toString().equals("")) { etSignUpName.error = "Заполните поле" }
        if (etSignUpSecondName.getText().toString().equals("")) { etSignUpSecondName.error = "Заполните поле" }
        if (etSignUpEmail.getText().toString().equals("")) { etSignUpEmail.error = "Заполните поле" }
        if (etSignUpPassword.getText().toString().equals("")) { etSignUpPassword.error = "Заполните поле" }
        if (etSignUpPasswordRepeat.getText().toString().equals("")) { etSignUpPasswordRepeat.error = "Заполните поле" }
        if (etSignUpPassword.text != etSignUpPasswordRepeat.text) { etSignUpPasswordRepeat.error = "Пароли не совпадают" }

        btSignUpHaveAccount.setOnClickListener {
            signUp()
            startActivity(Intent(this, SignIn::class.java))
        }

        btSignUpLogIn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
    }

    private fun signUp() {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.signup(etSignUpEmail.text.toString(), etSignUpPassword.text.toString(), etSignUpName.text.toString(), etSignUpSecondName.text.toString())
                Log.e("Main", "Response: ${response.body()}")
            }
            catch (e: java.lang.Exception){
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}