package com.cookandroid.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.cookandroid.todolist.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Join : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)


        // 뒤로 가기 버튼 클릭시 로그인 화면으로 이동
        binding.backImg.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val joinBtnClicked = findViewById<Button>(R.id.joinBtn)

        joinBtnClicked.setOnClickListener {
            val email = binding.idJoin
            val pwd = binding.pwJoin
            val pwd_check = binding.pwCheck

            // 비밀번호 확인
            if (pwd.text.toString() == pwd_check.text.toString()){
                auth.createUserWithEmailAndPassword(
                    email.text.toString(), pwd.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()

                        }
                    }
            }
            else{
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()

            }

        }
    }
}