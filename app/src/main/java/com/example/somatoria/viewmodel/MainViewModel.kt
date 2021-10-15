package com.example.somatoria.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.somatoria.service.Preference

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val mSharedPreferences = Preference(application)

    private val mLuckyNumber = MutableLiveData<String>()
    var luckyNumber: LiveData<String> = mLuckyNumber

    private val mBaseNumber = MutableLiveData<String>()
    var baseNumber: LiveData<String> = mBaseNumber

    fun calculate(baseNumber: String, selectedNumber: String) {

        if (baseNumber.length == 4 && selectedNumber.length == 4) {

            val digitList = arrayListOf<String>()
            var completeLuckyNumber: String
            val luckyNumberList = arrayListOf<String>()

            val preparedBaseNumbers = baseNumber.split("").filter { it != "" }.map {
                it.toInt()
            }

            var preparedNumbers = selectedNumber.split("").filter { it != "" }.map {
                it.toInt()
            }

            mSharedPreferences.saveString(Preference.NUMBER_KEY, baseNumber)

            if (baseNumber != "" && selectedNumber != "") {
                for (count in 1..10) {
                    val indexController = ((count * 4) - 4)

                    for (item in 0..3) {
                        digitList.add(
                            formatResults(
                                preparedBaseNumbers[item],
                                preparedNumbers[item]
                            )
                        )
                    }

                    completeLuckyNumber =
                        digitList[0 + indexController] + digitList[1 + indexController] + digitList[2 + indexController] + digitList[3 + indexController]

                    luckyNumberList.add(completeLuckyNumber)

                    preparedNumbers =
                        completeLuckyNumber.split("").filter { it != "" }.map { it.toInt() }

                }

                mLuckyNumber.value = luckyNumberList.joinToString(separator = "\n")
            }
        } else {
            mLuckyNumber.value = ""
        }

    }

    private fun formatResults(firstTerm: Int, secondTerm: Int): String {
        var formattedResult = firstTerm + secondTerm

        if (formattedResult >= 10) {
            formattedResult -= 10
        }
        return formattedResult.toString()
    }

    fun getBaseNumber() {
        mBaseNumber.value = mSharedPreferences.getString(Preference.NUMBER_KEY)
    }
}