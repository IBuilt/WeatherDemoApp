package com.demo.weather.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.lib.utils.ContextAccessor
import com.demo.lib.utils.DegreeUtils
import com.demo.lib.utils.extensions.inflate
import com.demo.lib.utils.extensions.loadUrl
import com.demo.lib.utils.extensions.truncateDecimal
import com.demo.weather.BuildConfig
import com.demo.weather.R
import com.demo.weather.data.entities.current.CityAndWeather
import kotlinx.android.synthetic.main.item_city_and_weather.view.*

class CityAndWeatherAdapter :
    ListAdapter<CityAndWeather, CityAndWeatherAdapter.ViewHolder>(COMPARATOR) {

    val degreeText: String by lazy {
        ContextAccessor.context.getString(R.string.degree)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_city_and_weather))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        getItem(position)?.let { nsUser ->
            holder.bind(nsUser)
        }
    }


    fun getCityItem(position: Int): CityAndWeather =
        getItem(position)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cityAndWeather: CityAndWeather) {


            itemView.txtViewCityName.text = cityAndWeather.city!!.name


            cityAndWeather.currentWeather?.let { nsCurrentWeather ->

                if (nsCurrentWeather.weathers.isNotEmpty()) {

                    itemView.imgViewIcon.loadUrl(
                        "${BuildConfig.WEB_IMG_URL}${
                            nsCurrentWeather.weathers[0].icon
                        }@2x.png"
                    )
                }


                nsCurrentWeather.main?.let { nsMain ->

                    itemView.txtViewTemp.text = nsMain.temp.truncateDecimal() + degreeText

                    itemView.txtViewTempHigh.text = nsMain.tempMax.truncateDecimal() + degreeText

                    itemView.txtViewTempLow.text = nsMain.tempMin.truncateDecimal() + degreeText
                }


                nsCurrentWeather.wind?.let { nsWind ->

                    itemView.txtViewDesc.text =
                        "Wind ${DegreeUtils.degreeToWindDirection(nsWind.deg)} at ${nsWind.speed.truncateDecimal()} mps"
                }
            }
        }
    }


    companion object {

        val COMPARATOR = object : DiffUtil.ItemCallback<CityAndWeather>() {
            override fun areItemsTheSame(
                oldItem: CityAndWeather,
                newItem: CityAndWeather
            ): Boolean =
                oldItem.city!!.id == newItem.city!!.id

            override fun areContentsTheSame(
                oldItem: CityAndWeather,
                newItem: CityAndWeather
            ): Boolean =
                oldItem == newItem
        }
    }
}