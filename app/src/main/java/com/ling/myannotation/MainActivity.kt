package com.ling.myannotation

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.ling.lib_annotation.BindView
import com.ling.lib_bind.BindTool

@SuppressLint("NonConstantResourceId")
class MainActivity : AppCompatActivity() {

    @BindView(R.id.cl_container)
    lateinit var clContainer: ConstraintLayout

    @BindView(R.id.tv_text)
    lateinit var tvText: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BindTool.bind(this)
        clContainer.background = Color.GREEN.toDrawable()
        tvText.text = "hello annotation"
    }

}