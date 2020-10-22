package com.example.fetchpoints

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.drawable.VectorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.fetchpoints.databinding.ActivityMainBinding
import kotlin.math.pow
import kotlin.math.sqrt

private lateinit var binding:ActivityMainBinding
private lateinit var viewLayout:RelativeLayout
class MainActivity : AppCompatActivity(), View.OnTouchListener, View.OnDragListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.dot.setOnTouchListener(this)
        binding.imageLayout.setOnDragListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val item = ClipData.Item(v.tag as? CharSequence)
                val mimeTypes =
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val dragData = ClipData(v.tag as? CharSequence, mimeTypes, item)
                val myShadow = View.DragShadowBuilder(v)
                v.startDrag(dragData, myShadow, v, 0)
            }
            MotionEvent.ACTION_MOVE -> {
                val item = ClipData.Item(v.tag as? CharSequence)
                val mimeTypes =
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val dragData = ClipData(v.tag as? CharSequence, mimeTypes, item)
                val myShadow = View.DragShadowBuilder(v)
                v.startDrag(dragData, myShadow, v, 0)
            }
        }
        return true

    }

    override fun onDrag(v: View?, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP-> {
                Toast.makeText(this,"Please take a screenshot now",Toast.LENGTH_LONG).show()

                val displayMetrics: DisplayMetrics = DisplayMetrics()
                val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.defaultDisplay.getMetrics(displayMetrics)
                val w=displayMetrics.widthPixels
                val h=displayMetrics.heightPixels
                val wi=(w/displayMetrics.xdpi).toDouble()
                val hi=(h/displayMetrics.xdpi).toDouble()
                val tw=event.x
                val th=event.y
                val twi: Double= (tw/displayMetrics.xdpi).toDouble()
                val thi: Double=(th/displayMetrics.ydpi).toDouble()
                val x:Double= wi.pow(2)
                val y:Double= hi.pow(2)
                val screenInches= sqrt(x+y)
                val TSW=2.67717
                val TAW=0.62467
                val expectedWidth:Double=(wi/TSW)*TAW

                val TSH=5.14363
                val TAH=0.85379
                val expectedHeight:Double=(hi/TSH)*TAH

                val data="ScreenResolution- Width: "+w+", Height- "+h+", \n Dragged Image coordinate(resolution/px): Width: "+tw+",  Height: "+th +", \n ScreenSize(inch): Width- $wi, Height- $hi,\n Density: Xdpi-${displayMetrics.xdpi}, Ydpi-${displayMetrics.ydpi}," +
                        "\n Dragged Image coordinate(inch): Width=$twi, Height=$thi,\n ScreenInches: $screenInches \n Model: ${Build.MANUFACTURER}-${Build.MODEL} \n \n \n Expected Coordinate(inch): Width-$expectedWidth Heigth-$expectedHeight"
                Log.i("autoGradePoints",data)
                binding.textView2.text = data



            }
        }
        return true
    }


}

