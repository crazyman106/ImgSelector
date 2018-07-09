package com.example.administrator.colorfilter

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.administrator.colorfilter.divider.DividerBuilder


class MainActivity : AppCompatActivity() {
    var headBg: Drawable? = null
    var iv1Drawable: Drawable? = null
    var iv2Drawable: Drawable? = null

    var rv: RecyclerView? = null
    var rlHead: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * 初始化工作
         */
        rv = findViewById(R.id.rv)
        rv?.setLayoutManager(LinearLayoutManager(this))
        rv?.setAdapter(SimpleAdapter())

        rlHead = findViewById(R.id.rl_head)

        headBg = rlHead?.getBackground()!!.mutate()//获取head的背景drawable
        iv1Drawable = (findViewById<View>(R.id.iv1) as ImageView).getDrawable().mutate()//获取图片的drawable
        iv2Drawable = (findViewById<View>(R.id.iv2) as ImageView).getDrawable().mutate()//获取图片的drawable


        //给recyclerView一个滚动监听
        rv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val fraction = calcFraction(dy)

                setUI(fraction)

            }


        })


        val builder = DividerBuilder()
                .divider(ResourcesCompat.getDrawable(resources, R.drawable.shape_divider_normal, null))
                .showBottom(false)
                .showTop(false)
        rv?.addItemDecoration(builder.build())
    }

    private var scrollY: Int = 0

    /**
     *
     * @param dy
     * @return   0~1 ,滑动距离越大，值越大
     */
    private fun calcFraction(dy: Int): Float {
        //这里的300是图片的高度，图片滚完后就是100%
        val imgHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300F, resources.displayMetrics)
        val toolbarHeight = rlHead?.getHeight()
        val maxHeight = imgHeight - toolbarHeight!!;//图片从头到尾移动的距离

        scrollY += dy //dy是这次移动的距离，每次移动的距离加起来就是总移动的距离，dy是有正有负的

        /**
         *
         */
        return if (scrollY >= maxHeight) {
            1.0f

        } else if (scrollY <= 0) {
            0f
        } else {
            //            float delta = Math.abs(scrollY - maxHeight) ;
            scrollY / maxHeight
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUI(fraction: Float) {
        //背景只需要设置透明度，255是全不透明
        headBg?.setAlpha((fraction * 255).toInt())

        //This evaluator can be used to perform type interpolation between integer values that represent ARGB colors.
        //这个求值器用来执行计算用整形表示的颜色的差值
        val argbEvaluator = ArgbEvaluator()
        val startColor = ActivityCompat.getColor(this@MainActivity, R.color.colorPrimary)
        val endColor = Color.WHITE

        //根据fraction计算出开始和结束中间的色值
        val calcColor = argbEvaluator.evaluate(fraction, startColor, endColor) as Int

        val colorFilter = PorterDuffColorFilter(calcColor, PorterDuff.Mode.SRC_IN)

        iv1Drawable?.setColorFilter(colorFilter)
        iv2Drawable?.setColorFilter(colorFilter)


    }


}
