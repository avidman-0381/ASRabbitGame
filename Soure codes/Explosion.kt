package com.example.dodgingbunny

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Explosion (
    private val explosions: Array<Bitmap?> = arrayOfNulls(3),
    private var explosionX: Int = 0,
    private var explosionY: Int = 0,
    private var explosionFrame: Int = 0,
    context: Context
) {

    init {
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion0)
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1)
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion2)

    }

    fun getExplosion(explosionFrame: Int) : Bitmap? {
        return explosions[explosionFrame]
    }

}
