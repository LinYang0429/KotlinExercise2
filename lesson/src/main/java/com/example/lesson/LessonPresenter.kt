package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient.get
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import toast
import java.util.ArrayList

class LessonPresenter {
    companion object {
        private const val LESSON_PATH = "lessons"
    }

    private var activity: LessonActivity? = null

    constructor(activity: LessonActivity?) {
        this.activity = activity
    }

    private var lessons: List<Lesson> = ArrayList<Lesson>()

    private val type = object : TypeToken<List<Lesson>>() {}.type

    fun fetchData() {
        get<List<Lesson>>(LESSON_PATH, type, object : EntityCallback<List<Lesson>> {
            override fun onSuccess(lessons: List<Lesson>) {
                this@LessonPresenter.lessons = lessons
                activity!!.runOnUiThread { activity!!.showResult(lessons) }
            }

            override fun onFailure(message: String?) {
                activity!!.runOnUiThread {
                    toast(message);
                }
            }
        })
    }

    fun showPlayback() {
        val playbackLessons: MutableList<Lesson> = ArrayList<Lesson>()
        for (lesson in lessons) {
            if (lesson?.state === Lesson.State.PLAYBACK) {
                playbackLessons.add(lesson)
            }
        }
        activity!!.showResult(playbackLessons)
    }
}