package org.wit.kid.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.kid.R
import org.wit.kid.adapters.KidAdapter
import org.wit.kid.adapters.KidListener
import org.wit.kid.databinding.ActivityKidListBinding
import org.wit.kid.main.MainApp
import org.wit.kid.models.KidModel

class KidListActivity : AppCompatActivity(), KidListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityKidListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKidListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadKids()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, KidActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKidClick(kid: KidModel) {
        val launcherIntent = Intent(this, KidActivity::class.java)
        launcherIntent.putExtra("kid_edit", kid)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadKids() }
    }

    private fun loadKids() {
        showKids(app.kids.findAll())
    }

    fun showKids (kids: List<KidModel>) {
        binding.recyclerView.adapter = KidAdapter(kids, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}


