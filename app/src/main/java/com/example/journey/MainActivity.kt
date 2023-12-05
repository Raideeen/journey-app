package com.example.journey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.journey.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

// MainActivity sets up the navigation drawer and manages navigation.
class MainActivity : AppCompatActivity() {

    // DrawerLayout is the root layout for activity_main.xml. It allows a
    // navigation drawer to slide in and out.
    private lateinit var drawerLayout: DrawerLayout

    // ? AppBarConfiguration links the NavController with the DrawerLayout. It's
    // ? used to configure how the ActionBar behaves with the NavController,
    // ? including showing the hamburger menu icon and handling the "up"
    // ? navigation.
    private lateinit var appBarConfiguration: AppBarConfiguration

    // NavigationView represents the sidebar. It's where you define your
    // drawer's menu items.
    private lateinit var navigationView: NavigationView

    // NavController manages the app's navigation within the NavHostFragment.
    private lateinit var navController: NavController

    // Listener for navigation destination changes, used to update UI elements
    // like the ActionBar's title.
    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate and set the layout for this activity.
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ? The Toolbar is set as the ActionBar. The ActionBar is where you'll
        // ? see the hamburger menu icon and title. It acts as the primary toolbar
        // ? at the top of the screen.
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Initialize NavigationView and DrawerLayout. The DrawerLayout is
        // necessary for the NavigationView (sidebar) to slide in and out, while
        // the NavigationView holds the actual navigation items (menu).
        navigationView = findViewById(R.id.navigationView)
        drawerLayout = findViewById(R.id.drawer_layout)

        // Retrieve the NavController. It's essential for managing navigation
        // between different fragments in the app. The NavController is tied to
        // a NavHostFragment defined in the XML layout.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        // ? AppBarConfiguration ties together the drawer layout and the
        // ? NavController. It's used to determine when the hamburger menu should
        // ? be shown (indicating the navigation drawer can be opened) and handles
        // ? the up navigation within the ActionBar.
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Connect the NavigationView with the NavController. This allows for
        // the selection of items in the NavigationView to control the
        // navigation of the app.
        navigationView.setupWithNavController(navController)

        // Setup the ActionBar with NavController and AppBarConfiguration. This
        // makes the ActionBar aware of the navigation drawer and changes its
        // behavior (like showing the hamburger icon) based on the current
        // navigation destination.
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Listener for navigation destination changes. It's used here to change
        // the ActionBar's title when navigating to different fragments.
        listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.notebookFragment -> supportActionBar?.title = "Notebook"
                R.id.profileFragment -> supportActionBar?.title = "Profile"
                R.id.detailsFragment -> supportActionBar?.title = "Story Details"
                R.id.countryIdeasFragment -> supportActionBar?.title = "Country Ideas"
                // Additional cases can be added for other destinations.
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        // This method is called when the up button in the ActionBar is pressed.
        // It ensures that the NavController handles the up navigation,
        // considering the DrawerLayout configuration.
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        // Add the destination change listener to the NavController when the
        // activity resumes. This is important to ensure the UI is updated
        // correctly when navigating.
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        // Remove the destination change listener from the NavController when
        // the activity pauses. This prevents unnecessary updates when the
        // activity is not in the foreground.
        navController.removeOnDestinationChangedListener(listener)
    }
}
