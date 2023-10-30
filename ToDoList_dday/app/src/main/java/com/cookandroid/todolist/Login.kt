package com.cookandroid.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.cookandroid.todolist.category.activity.MainActivity
import com.cookandroid.todolist.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)


        // 가입하기 버튼 클릭시 수행
        binding.joinText.setOnClickListener {
            val intent = Intent(this, Join::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 클릭시 수행
        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput
            val pwd = binding.pwInput

            auth.signInWithEmailAndPassword( email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "로그인 실패, 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}