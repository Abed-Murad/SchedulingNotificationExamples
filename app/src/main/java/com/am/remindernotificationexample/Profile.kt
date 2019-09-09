package com.am.remindernotificationexample

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.ArrayList

class Profile : AppCompatActivity() {

    private val array_image_product = intArrayOf(
        R.drawable.image_shop_9,
        R.drawable.image_shop_10,
        R.drawable.image_shop_11,
        R.drawable.image_shop_12,
        R.drawable.image_shop_13
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initComponent()
        showImageSlider(5)
    }


    private fun showImageSlider(position: Int) {
        val IMAGES_LIST = "imagesList"
        val POSITION = "position"
        val SLIDE_SHOW = "slideshow"
        val bundle = Bundle()

        bundle.putInt(POSITION, position)
        val ft = getSupportFragmentManager().beginTransaction()
        val newFragment = ImagesSliderFragment.newInstance()
        newFragment.arguments = bundle
        newFragment.show(ft, SLIDE_SHOW)
    }

    private fun initComponent() {
       val adapterImageSlider = AdapterImageSlider(this, ArrayList())

        val items = ArrayList<Image>()
        for (i in array_image_product) {
            val obj = Image()
            obj.image = i
            obj.imageDrw = resources.getDrawable(obj.image)
            items.add(obj)
        }

        adapterImageSlider.setItems(items)
        pager.adapter = adapterImageSlider

        pager.setCurrentItem(0)
        addBottomDots(layout_dots, adapterImageSlider.count, 0)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                addBottomDots(layout_dots, adapterImageSlider.count, position)
            }

        })
    }
    private fun addBottomDots(layout_dots: LinearLayout, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)

        layout_dots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val width_height = 15
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(
                ContextCompat.getColor(this, R.color.overlay_dark_10),
                PorterDuff.Mode.SRC_ATOP
            )
            layout_dots.addView(dots[i])
        }

        if (dots.size > 0) {
            dots[current]!!.setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimaryLight),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }
}
