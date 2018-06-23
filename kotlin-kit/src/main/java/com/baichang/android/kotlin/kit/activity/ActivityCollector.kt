package com.baichang.android.kotlin.kit.activity

import android.app.Activity
import java.util.ArrayList

/**
 * Created by iCong on 05/01/2018.
 */
class ActivityCollector {
  companion object {
    private var activities: MutableList<Activity> = ArrayList()

    fun hasLife(): Boolean = activities.size > 0

    fun addActivity(activity: Activity) {
      if (!activities.contains(activity)) {
        activities.add(activity)
      }
    }

    fun removeActivity(activity: Activity) {
      activities.remove(activity)
    }

    fun finishAll() {
      activities.filterNot { it.isFinishing }
          .forEach { it.finish() }
    }

    fun exitApp() {
      finishAll()
      System.exit(0)
    }
  }
}
