package white.ball.todolist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import white.ball.todolist.R
import white.ball.todolist.databinding.ActivityMainBinding
import white.ball.todolist.presentation.screen.TasksListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_view,TasksListFragment())
                .commit()
        }
    }
}
