package com.am.remindernotificationexample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

class ImagesSliderFragment : DialogFragment() {

    private val TAG = ImagesSliderFragment::class.java.simpleName
    private var images: ArrayList<String>? = null
    private var viewPager: ViewPager? = null
    private var imagesCount: TextView? = null
    private var selectedPosition = 0


    //	page change listener
    private val viewPagerPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            displayMetaInfo(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_images_slider_dialog, container, false)
        viewPager = v.findViewById(R.id.viewpager)
        imagesCount = v.findViewById(R.id.imagesCount)
        val closeButton = v.findViewById(R.id.closeButton) as ImageView

        closeButton.setOnClickListener({ v1 -> dismiss() })
        if (arguments != null) {
            images = arguments!!.getStringArrayList(IMAGES_LIST)
        }
        if (arguments != null) {
            selectedPosition = arguments!!.getInt(POSITION)
        }

        Log.e(TAG, "position: $selectedPosition")
        Log.e(TAG, "images size: " + images!!.size)

        val myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter

        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)

        setCurrentItem(selectedPosition)

        return v
    }

    private fun setCurrentItem(position: Int) {
        viewPager!!.setCurrentItem(position, false)
        displayMetaInfo(selectedPosition)
    }

    @SuppressLint("SetTextI18n")
    private fun displayMetaInfo(position: Int) {
        imagesCount!!.text = (position + 1).toString() + " of " + images!!.k
        val image = images!![position]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    inner class MyViewPagerAdapter internal constructor() : PagerAdapter() {

        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            layoutInflater = Objects.requireNonNull(activity).getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater
            val view = layoutInflater!!.inflate(
                R.layout.image_fullscreen_preview, container, false
            )

            val imageViewPreview = view.findViewById(R.id.image_preview) as ImageView

            val image = images!![position]
            Glide.with(activity).load(R.drawable.image_shop_9)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewPreview)

            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return 5
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        private val IMAGES_LIST = "imagesList"
        private val POSITION = "position"

        internal fun newInstance(): ImagesSliderFragment {
            return ImagesSliderFragment()
        }
    }
}