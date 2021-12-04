package com.example.lesson


import androidx.appcompat.app.AppCompatActivity
import com.example.core.BaseView

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.os.Bundle
import android.view.MenuItem

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import android.widget.LinearLayout
import android.widget.Toolbar
import com.example.lesson.entity.Lesson

class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter>,
    Toolbar.OnMenuItemClickListener {
    override val p: LessonPresenter by lazy {
        LessonPresenter(this)
    }

    private val lessonAdapter = LessonAdapter()
    private lateinit var refreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lesson)
        toolbar.setOnMenuItemClickListener(this)
        findViewById<RecyclerView>(R.id.list).run {
            layoutManager = LinearLayoutManager(this@LessonActivity)
            adapter = lessonAdapter
            addItemDecoration(DividerItemDecoration(this@LessonActivity, LinearLayout.VERTICAL))
        }

        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout).run {
            refreshLayout = this
            setOnRefreshListener { p.fetchData() }
            setRefreshing(true)
        }

        p.fetchData()
    }

    internal fun showResult(lessons: List<Lesson>) {
        lessonAdapter.updateAndNotify(lessons)
        refreshLayout.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        p.showPlayback()
        return false
    }
}