package com.example.dodgingbunny

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.text.TextPaint
import android.util.AttributeSet
import android.view.Display
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.random.Random
import android.graphics.Point
import android.os.Looper
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
// look at MainActivity and see how the argument to the function is a context object, so initialize these variables after the scope ().

@RequiresApi(Build.VERSION_CODES.R)
open class GameView (
    context: Context,
    attrs: AttributeSet,
    private var background: Bitmap? = null,
    private var ground: Bitmap? = null,
    private var rabbit: Bitmap? = null,
    private var rectBackground: Rect? = null,
    private var rectGround: Rect? = null,
    var handler: Handler,
    private val UPDATE_MILLIS: Int = 30,
    var runnable: Runnable,
    var textPaint: TextPaint,
    var healthPaint: TextPaint,
    val TEXT_SIZE: Float = 120f,
    var points: Int = 0,
    var life: Int = 3,
    var dWidth: Int = 0,
    var dHeight: Int = 0,
    var random: Random = Random.Default,
    var rabbitX: Int = 0,
    var rabbitY: Float = 0f,
    var oldX: Float = 0f,
    var oldRabbitX: Float = 0f,
    var bombs: Array<Bomb>,
    var explosions: Array<Explosion>,
    var display: Display,
    var size: Point
    ): View(context, attrs) {

    init{
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background)
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground)
        rabbit = BitmapFactory.decodeResource(getResources(), R.drawable.bunny)
        display = (context as Activity).display
        size = Point()
        var bounds = (context as Activity).windowManager.currentWindowMetrics.bounds
        //(display as WindowManager).currentWindowMetrics.bounds
        dWidth = size.x
        dHeight = size.y
        rectBackground = Rect(0, 0, dWidth, dHeight)
        rectGround = Rect(0, dHeight- (ground?.height ?: 0), dWidth, dHeight)
        // without safe call: rectGround = Rect(0, dHeight- ground.height, dWidth, dHeight)
        Looper.myLooper()?.let { handler = Handler(it) }
        runnable = Runnable() {
            @Override
            fun run() {
                invalidate()
            }
        }
        textPaint.setColor(Color.rgb(255, 165, 0))
        textPaint.textSize = TEXT_SIZE
        textPaint.setTextAlign(Paint.Align.LEFT)
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.kenney_blocks))
        healthPaint.setColor(Color.GREEN)
        random = Random.Default
        rabbitX = dWidth / 2 - (rabbit?.width?.div(2) ?: 0)
        rabbitY = dHeight - ground.height - rabbit.height
        // bombs = new ArrayList<>()
        // explosions = new ArrayList<>()
        /* for(int i = 0; i < 3; i++) {
        Bomb bomb = new Bomb(context);
        bombs.add(bomb)
        }
         */

        onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawBitmap(background, null, rectBackground, null)
            canvas.drawBitmap(ground, null, rectGround, null)
            canvas.drawBitmap(rabbit, rabbitX, rabbitY, null)
            for (int i = 0; i <bombs.size; i++){
                canvas.drawBitmap(bombs.get(i).getBomb(bombs.get(i).bombFrame), bombs.get(i).bombX, bombs.get(i).bombY, null)
                bombs.get(i).bombFrame++
                if (bombs.get(i).bombFrame > 2){
                    bombs.get(i).bombFrame = 0
                }
                bombs.get(i).bombY += bombs.get(i).bombVelocity
                if(bombs.get(i).bombY + bombs.get(i).getBombHeight() >= dHeight - ground.height){
                    points += 10
                    Explosion explosion = new Explosion(context)
                    explosion.explosionX = bombs.get(i).SpikeX
                    explosion.explosionY = bombs.get(i).bombY
                    explosions.add(explosion)
                    bombs.get(i).resetPosition()
                }
            }


            for (int i = 0; i < bombs.size; i++){
                if(bombs.get(i).bombX + bombs.get(i).getBombWidth() >=rabbitX
                    && bombs.get(i).bombX <=rabbitX + rabbit.width
                    && bombs.get(i).bombY + bombs.get(i).getBombWidth() >= rabbitY
                    && bombs.get(i).bombY + bombs.get(i).getBombWidth() <= rabbitY + rabbit.height){
                    life--
                    bombs.get(i).resetPosition()
                    if(life == 0){
                        Intent intent = new Intent(context, GameOver.class)
                                intent.putExtra("points", points)
                        context.startActivity(intent)
                        ((Activity) context).finish()
                    }
                }


            for(int i = 0; i < explosions.size; i++){
                canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).explosionX,
                    explosions.get(i).explosionY, null)
            explosions.get(i).explosionFrame++
            if(explosions.get(i).explosionFrame > 3){
                explosions.remove(i)
            }
        }

            if(life ==2) {
                healthPaint.setColor(Color.YELLOW)
                } else if(life==1){
                    healthPaint.setColor(Color.RED)
            }
            canvas.drawRect(dWidth-200, 30, dWidth - 200 + 60*life, 80, healthPaint)
            canvas.drawText("" + points, 20, TEXT_SIZE, textPaint)
            handler.postDelayed(runnable, UPDATE_MILLIS)


        }

        }

        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX()
            float touchY = event.getY()
            if (touchY >= rabbitY){
                int action = event.getAction()
                if(action == motionEvent.ACTION_DOWN) {
                    oldX = event.getX()
                    oldRabbitX = rabbitX
                }
                if(action == MotionEvent.ACTION_MOVE){
                    float shift = oldX - touchX
                    float newRabbitX = oldRabbitX - shift
                    if(newRabbitX <= 0)
                        rabbitX = 0
                    else if(newRabbitX >= dWidth - rabbit.getWidth())
                        rabbitX = dWidth - rabbit.getWidth()
                    else
                        rabbitX = newRabbitX
                }
            }
            return true
        }





    }

}
