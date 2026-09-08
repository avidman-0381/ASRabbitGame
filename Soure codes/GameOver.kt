package com.example.dodgingbunny

import android.widget.TextView
import android.content.SharedPreferences
import android.widget.ImageView
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.content.Intent
import android.view.View

open class GameOver(
    var tvPoints: TextView,
    var tvHighest: TextView,
    var sharedPreferences: SharedPreferences,
    var ivNewHighest: ImageView
) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)
        tvPoints = findViewById(R.id.tvPoints)
        tvHighest = findViewById(R.id.tvHighest)
        ivNewHighest = findViewById(R.id.ivNewHighest)
        var points: Int = intent.extras?.getInt("points") ?: 0
        tvPoints.setText("" + points)
        sharedPreferences = getSharedPreferences("my_pref", 0)
        var highest: Int = sharedPreferences.getInt("highest", 0)
        if(points > highest){
            ivNewHighest.visibility(View.VISIBLE)
            highest = points
            var editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("highest", highest)
            editor.commit()
        }
        tvHighest.setText("" + highest)

    }

    fun restart(view: View) {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        }

    fun exit(view: View) : Unit {
        finish()
    }

}
