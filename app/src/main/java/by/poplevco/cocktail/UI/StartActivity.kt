package by.poplevco.cocktail.UI

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.poplevco.cocktail.R
import by.poplevco.cocktail.ViewModel.DrinkViewModel
import by.poplevco.cocktail.ViewModel.DrinkViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_start.*
import javax.inject.Inject
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

@AndroidEntryPoint
class StartActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var navController: NavController
    lateinit var viewModel: DrinkViewModel

    @Inject
    lateinit var drinkFactory: DrinkViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, drawerLayout)
        toolbar.setupWithNavController(navController, drawerLayout)
        nav_view.setupWithNavController(navController)

        viewModel = ViewModelProvider(this, drinkFactory).get(DrinkViewModel::class.java)
        getCocktails()
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    private fun getCocktails() {
        try {
            val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val infoAboutConnect = manager.getNetworkCapabilities(manager.activeNetwork)
            if (infoAboutConnect == null) {
                viewModel.getData()
                Toast.makeText(this, "Network disconnect", Toast.LENGTH_SHORT).show()
            } else if (infoAboutConnect.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                infoAboutConnect.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            ) {
                viewModel.getData()
            } else {
                Toast.makeText(this, "Error Network", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        val searchItem = menu?.findItem(R.id.search_item)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = "Название коктейля.."
        searchView?.isSubmitButtonEnabled = false
        searchView?.setOnQueryTextListener(this)
        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchName(newText)
        }
        return true
    }
    private fun searchName(query: String) {
        val searchQuery = "%${query}%"
        viewModel.searchDatabase(searchQuery)
        viewModel.drinkLiveData.value = viewModel.drinkLiveData.value
    }
}