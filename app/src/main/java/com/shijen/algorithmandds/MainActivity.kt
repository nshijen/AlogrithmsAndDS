package com.shijen.algorithmandds

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.stetho.Stetho
import com.shijen.algorithmandds.alogrithms.SortingAlgorithms
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.histogram_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private var popup: PopupMenu? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application)).get(MainViewModel::class.java)
        Stetho.initializeWithDefaults(this)
        setContentView(R.layout.activity_main)
        observeLiveDatas()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.initDb()
            viewModel.setUpGraph()
        }
    }

    private fun observeLiveDatas() {
        viewModel.sortingAlogrithmLiveData.observe(this, Observer { tv_alog_name.setText(it.alogName) })
        viewModel.progressLiveData.observe(this, Observer { progress_layout.visibility = if(it) View.VISIBLE else View.GONE })
        viewModel.outputLiveData.observe(this, Observer { tv_out_put.text = it })
        viewModel.inputVariablesLiveData.observe(this, Observer { tv_input_variables.text = it })
        viewModel.timeTakenLiveData.observe(this, Observer {
            tv_avg_time_taken.text = "Average time: ${it.first.toString()} seconds"
            tv_time_taken.text = "Time taken: ${it.second.toString()} seconds"
        })
        viewModel.graphLiveData.observe(this, Observer {
            CoroutineScope(Dispatchers.IO).launch {
                updateView(it)
            }
        })

    }

    private suspend fun updateView(it: HashMap<String, Double>) {
        var count = 1
        withContext(Dispatchers.Main){
            ll_graphLayout.removeAllViews()
        }
        for (item in it) {
            val inflate = layoutInflater.inflate(R.layout.histogram_item, null)
            inflate.v_histogram.layoutParams = LinearLayout.LayoutParams(
                convertDpToPixel(50.0.toFloat(), this).toInt()
                , (convertDpToPixel((5 * item.value).toFloat(), this)).toInt()
            )
            inflate.tv_histo_time_taken.text = item.value.toString()
            inflate.tv_algo.text = item.key
            val value = (360 / 7) * count
            val valueOf = Color.HSVToColor(floatArrayOf(value.toFloat(), 1.toFloat(), 0.5.toFloat()))
            inflate.tv_algo.setTextColor(valueOf)
            inflate.tv_histo_time_taken.setTextColor(valueOf)
            inflate.v_histogram.setBackgroundColor(valueOf)
            withContext(Dispatchers.Main) {
                (ll_graphLayout).addView(inflate)
            }
            count++
        }
    }

    override fun onStart() {
        super.onStart()

        tv_alog_name.setOnClickListener(View.OnClickListener {
            showPopup(it)
        })
    }

    fun showPopup(v: View?) {
        v?.let {
            popup = PopupMenu(this, v)
        }
        menuInflater.inflate(R.menu.sorting_menu, popup?.getMenu())
        popup?.show()
        popup?.setOnMenuItemClickListener(this@MainActivity)
    }

    fun sort(view: View) {
        viewModel.sort()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.insertion_sort -> viewModel.setSortingAlgo(SortingAlgorithms.INSERTION_SORT)
            R.id.bubble_sort -> viewModel.setSortingAlgo(SortingAlgorithms.BUBBLE_SORT)
            R.id.merge_sort -> viewModel.setSortingAlgo(SortingAlgorithms.MERGE_SORT)
            R.id.selection_sort -> viewModel.setSortingAlgo(SortingAlgorithms.SELECTION_SORT)
            R.id.quick_sort -> viewModel.setSortingAlgo(SortingAlgorithms.QUICK_SORT)
            R.id.heap_sort -> viewModel.setSortingAlgo(SortingAlgorithms.HEAP_SORT)
            R.id.radix_sort -> viewModel.setSortingAlgo(SortingAlgorithms.RADIX_SORT)
        }
        return true
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.getResources()
            .getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
