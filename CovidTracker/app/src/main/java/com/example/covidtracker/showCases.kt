package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.covidtracker.models.CountryData
import kotlinx.android.synthetic.main.activity_show_cases.*

class showCases : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_cases)

        val url = "https://worldometers.p.rapidapi.com/api/coronavirus/all/?rapidapi-key=796b079dcfmshb9894376838705fp1abd43jsn3084ef440386"

        val countryName = intent.getStringExtra("selected Country")
        countryValue.text = countryName

        progressBar.visibility = View.VISIBLE
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                {
                    progressBar.visibility = View.GONE
                    val jsonArray = it.getJSONArray("data")
                    for(i in 0 until jsonArray.length())
                    {   val countryData: CountryData
                        val jsonObject = jsonArray.getJSONObject(i)
                        if(jsonObject.getString("Country")==countryName)
                        {
                            countryData = CountryData(
                                    jsonObject.getString("Active Cases"),
                                    jsonObject.getString("Critical"),
                                    jsonObject.getString("New Cases"),
                                    jsonObject.getString("New Deaths"),
                                    jsonObject.getString("New Recovered"),
                                    jsonObject.getString("Total Cases"),
                                    jsonObject.getString("Total Deaths"),
                                    jsonObject.getString("Total Recovered"),
                                    jsonObject.getString("Total Tests")
                            )
                            activeCasesValue.text = countryData.activeCases
                            criticalValue.text = countryData.critical
                            newCasesValue.text = countryData.newCases
                            newDeathsValue.text = countryData.newDeaths
                            newRecoveredValue.text = countryData.newRecovered
                            totalCasesValue.text = countryData.totalCases
                            totalDeathsValue.text = countryData.totalDeaths
                            totalRecoveredValue.text = countryData.totalRecovered
                            totalTestsValue.text = countryData.totalTests
                            break
                        }
                    }
                },
                {

                }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}