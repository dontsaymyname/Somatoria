package com.example.somatoria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.somatoria.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var firstNumber: String = ""
    private var baseNumber: String = ""
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mViewModel.getBaseNumber()

        observe()

        button_calculate.setOnClickListener {
            setNumbers()
            mViewModel.calculate(baseNumber, firstNumber)
        }

        text_share.setOnClickListener {
            share()
        }

    }

    private fun observe() {
        mViewModel.baseNumber.observe(this, Observer {
            edit_number_base.setText(it)
        })

        mViewModel.luckyNumber.observe(this, Observer {
            if (it != "") {
                text_result.text = it
            } else {
                text_result.text = getString(R.string.fail)
            }

            text_share.visibility = View.VISIBLE

        })
    }

    private fun setNumbers() {
        firstNumber = edit_number.text.toString()
        baseNumber = edit_number_base.text.toString()
    }

    private fun share() {
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, text_result.text)
            this.type = "text/xml"
        }
        startActivity(shareIntent)
    }
}