package com.knotworking.numbers.counter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knotworking.numbers.R
import com.knotworking.numbers.database.DatabaseContract

class CounterFragment : Fragment(), LoaderManager.LoaderCallbacks<List<CounterItem>> {

    private val COUNTER_LOADER = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CounterAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.fragment_counter, container, false)
        recyclerView = root.findViewById<View>(R.id.fragment_counter_recyclerView) as RecyclerView
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator?.changeDuration = 0
        adapter = CounterAdapter(activity)
        recyclerView.adapter = adapter

        loaderManager.initLoader(COUNTER_LOADER, null, this@CounterFragment)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<CounterItem>> {
        val uri = DatabaseContract.Counters.CONTENT_URI
        val projection = arrayOf(DatabaseContract.Counters.COL_ID,
                DatabaseContract.Counters.COL_NAME,
                DatabaseContract.Counters.COL_COUNT)
        return CounterListLoader(activity, uri, projection, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<List<CounterItem>>?, data: List<CounterItem>?) {
        adapter.setData(data)
    }

    override fun onLoaderReset(loader: Loader<List<CounterItem>>?) {
        adapter.setData(null)
    }
}
