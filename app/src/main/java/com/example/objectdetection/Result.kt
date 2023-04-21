package com.example.objectdetection

import android.graphics.Rect

data class Result(val classIndex: Int, val score: Float, val rect: Rect) : Comparable<Result> {
    override fun compareTo(other: Result): Int {
        return this.score.compareTo(other.score)
    }
}