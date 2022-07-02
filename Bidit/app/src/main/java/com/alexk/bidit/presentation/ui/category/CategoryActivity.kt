package com.alexk.bidit.presentation.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexk.bidit.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initEvent()
    }

    private fun init() {
        binding.apply {
            tvCategoryTitle.text = intent.getStringExtra("category")
        }
    }

    private fun initEvent() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            btnFilter.setOnClickListener {
                //dialog show
            }
            tvListSort.setOnClickListener {
                //sort dialog show
            }
        }
    }
}