package com.example.dodgingbunny

import android.graphics.Bitmap
import kotlin.random.Random
import android.graphics.BitmapFactory
import android.content.Context

class Bomb(
    private val bombs: Array<Bitmap?> = arrayOfNulls<Bitmap?>(3),
    private var bombFrame: Int = 0,
    private var bombX: Int = 0,
    private var bombY: Int = 0,
    private var bombVelocity: Int = 0,
    private val random: Random = Random.Default,
    context: Context
    ) {

    init {
        bombs[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb0)
        bombs[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb1)
        bombs[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb2)
        resetPosition()
    }

    fun getBomb(bombFrame: Int) : Bitmap? {
        return bombs[bombFrame]
    }

    fun getBombWidth(bombFrame: Int) : Int {
        return bombs[bombFrame]?.width ?: 0
    }

    fun getBombHeight(bombFrame: Int) : Int {
        return bombs[bombFrame]?.height ?: 0
    }


    fun resetPosition() : Unit {
        bombX = random.nextInt(GameView.dWidth - getBombWidth(bombFrame))
        bombY = -200 + random.nextInt(600 * -1)
        bombVelocity = 35 + random.nextInt(16)
    }

}
