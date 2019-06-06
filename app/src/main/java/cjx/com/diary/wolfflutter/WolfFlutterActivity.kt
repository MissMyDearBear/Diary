package cjx.com.diary.wolfflutter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cjx.com.diary.R
import io.flutter.facade.Flutter

/**
 * description:
 * author: bear .
 * Created date:  2019-06-21.
 */
class WolfFlutterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wolf_flutter)
        supportFragmentManager.beginTransaction().replace(R.id.container, Flutter.createFragment("route1"))
                .commit()
    }

    companion object{
        @JvmStatic
        fun action(context: Context){
            val intent = Intent(context,WolfFlutterActivity::class.java)
            context.startActivity(intent)
        }
    }
}