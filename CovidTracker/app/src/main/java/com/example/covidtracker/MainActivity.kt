package com.example.covidtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.covidtracker.models.Country
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_cases.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCountryArray()

    }

    private fun getCountryArray() {
        val intent = Intent(this, showCases::class.java)
        val countryArray = ArrayList<Country>()
        val countryStringArray = ArrayList<String>()
        val url = "https://worldometers.p.rapidapi.com/api/coronavirus/all/?rapidapi-key=796b079dcfmshb9894376838705fp1abd43jsn3084ef440386"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                {
                    val jsonArray = it.getJSONArray("data")
                    for(i in 0 until jsonArray.length())
                    {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val country = Country(
                                jsonObject.getString("Country")
                        )
                        countryArray.add(country)
                    }
                    for(i in 0 until countryArray.size)
                    {
                        countryStringArray.add(countryArray[i].country)
                    }


                    val spinnerArrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,countryStringArray)
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = spinnerArrayAdapter
                    countryStringArray.add(0,"Select country")

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selectedCountry = parent!!.getItemAtPosition(position).toString()
                            if(selectedCountry != "Select country")
                            {
                                intent.putExtra("selected Country", selectedCountry)
                                startActivity(intent)
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }

                },
                {

                }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}