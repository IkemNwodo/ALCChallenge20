package nwodo.ikem.com.alcchallenge20

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_deal.*
import kotlinx.android.synthetic.main.activity_deal.toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var dealsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu_activity, menu)
        //val insertMenu = menu?.findItem(R.id.insert_menu)
        //insertMenu?.isVisible = FirebaseUtil.isAdmin
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.insert_menu->{
                val intent = Intent(this, DealActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.logout_menu->{
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener {
                            Log.d("LOGOUT", "user logged out")
                            FirebaseUtil.attachListener()
                        }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        FirebaseUtil.detachListener()
    }

    override fun onResume() {
        super.onResume()
        FirebaseUtil.openFbReference("traveldeals",this)
        dealsRecyclerView = travelDealsList
        dealsRecyclerView.adapter = TravelAdapter()
        dealsRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        dealsRecyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        FirebaseUtil.attachListener()
    }


    public fun showMenu() {
        invalidateOptionsMenu()
    }

}
