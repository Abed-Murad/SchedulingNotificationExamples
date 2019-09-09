package com.am.remindernotificationexample

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AdapterImageSlider(private val act: Activity, private var items: List<Image>?) :
    PagerAdapter() {

    override fun getCount() = this.items!!.size

    private var onItemClickListener: AdapterImageSlider.OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Image)
    }

    fun setOnItemClickListener(onItemClickListener: AdapterImageSlider.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun getItem(pos: Int): Image {
        return items!![pos]
    }

    fun setItems(items: List<Image>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val o = items!![position]
        val inflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.item_slider_image, container, false)

        val image = v.findViewById(R.id.image) as ImageView
        val lyt_parent = v.findViewById(R.id.image) as ImageView
        Glide.with(act).load(o.image)
            .crossFade()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(image)
        image.setOnClickListener { v ->
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(v, o)
            }
        }

        (container as ViewPager).addView(v)

        return v
    }

    override  fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as RelativeLayout)

    }

}