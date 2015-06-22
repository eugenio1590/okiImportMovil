package app.okiimport.com.okiimport;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import app.okiimport.com.okiimport.fragmentos.FrgRequerimiento;
import librerias.componentes.Fragmento;

public abstract class ActRequerimiento extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                    FrgRequerimiento.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    protected CharSequence mTitle;

    /**
     * Usado para contener los titulos de los fragmentos
     * */
    protected ArrayList<String> fragmentos;

    /**
     * Usados para los componentes graficos de usuario del navegador
     * */
    protected Integer navigationDrawer;
    protected Integer drawerLayout;
    protected Integer menuActiv;

    public ActRequerimiento(Integer navigationDrawer, Integer drawerLayout, Integer menuActiv){
        this.navigationDrawer = navigationDrawer;
        this.drawerLayout = drawerLayout;
        this.menuActiv = menuActiv;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, newInstanceFragment(position + 1))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment!=null && !mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(menuActiv, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    /**GETTERS Y SETTERS*/
    public CharSequence getmTitle() {
        return mTitle;
    }

    public void setmTitle(CharSequence mTitle) {
        this.mTitle = mTitle;
    }

    /**METODOS ABSTRACTOS DE LA CLASE*/
    public abstract Fragmento newInstanceFragment(Integer position);

    /**METODOS PROPIOS DE LA CLASE*/
    protected void createNavigation() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(navigationDrawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setTitles(fragmentos);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                navigationDrawer,
                (DrawerLayout) findViewById(drawerLayout));
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**INTERFACE DE COMUNICACION*/
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
