package com.example.sergey.organizer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.sergey.organizer.constants.Constants;
import com.example.sergey.organizer.util.Util;
import com.example.sergey.organizer.dao.ConfigDao;
import com.example.sergey.organizer.dao.WorkingWithFiles;
import com.example.sergey.organizer.entity.ChooseMenu;
import com.example.sergey.organizer.entity.Config;
import com.example.sergey.organizer.calendar.CalendarFragment;
import com.example.sergey.organizer.fragment.DatePickerFragment;
import com.example.sergey.organizer.fragment.EditEntryFragment;
import com.example.sergey.organizer.fragment.MainFragment;
import com.example.sergey.organizer.fragment.TimePickerFragment;
import com.example.sergey.organizer.recycler.RecyclerListFragment;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnListItemClickListener,
        RecyclerListFragment.OnClickListener,
        EditEntryFragment.OnClickListenerEEF,
        CalendarFragment.OnClickListenerCF {

    private WorkingWithFiles wwf;
    private Controller cont = new Controller();
    private static Menu getMenu;
    private static int itemBeckHome;
    private static Config currentConfig;
    private EditEntryFragment eef;
  //  private RecyclerListFragment rlf;
    private ConfigDao configDao = new ConfigDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wwf = new WorkingWithFiles(this);
        // wwf.addData();
        Log.d("tag", "onCreate ");
        if (savedInstanceState != null) {
            int i = savedInstanceState.getInt("currentConfig");
            currentConfig = findConfig(i);
        } else {
            currentConfig = findConfig(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentConfig", currentConfig.getId());
    }


    private Config findConfig(int configId) {
        return configDao.findConfig(configId);
    }

    public void startConfig(Config config) {
        Log.d("TAG", "startConfig  " + config.getId());
        currentConfig = config;
        Map<Integer, String> paramsMenu = config.getParamsMenu();
        Constants.ITEM_SORT = (paramsMenu.containsKey(Constants.ACTION_ITEM_SORT));
        Constants.ITEM_DELETE = (paramsMenu.containsKey(Constants.ACTION_ITEM_DELETE));
        itemBeckHome = config.getActionHomeItem();
        startFragment(config.getFragment());
        createConfigMenu(config);
    }

    private void createConfigMenu(Config config) {
        List<ChooseMenu> allItemMenu = configDao.getItemMenu();
        Map<Integer, String> paramsMenu = config.getParamsMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(config.isItemHome());
        if (config.getTitle() != 0) {
            getSupportActionBar().setTitle(config.getTitle());
        }

        if (getMenu != null) {
            Log.d("tag", "createConfigMenu: " + config.getId() + " itemBeckHome: " + itemBeckHome);
            for (ChooseMenu c : allItemMenu) {
                boolean param = (paramsMenu.containsKey(c.getId()));
                getMenu.findItem(c.getItemID()).setVisible(param);
            }
        }
    }

    public void startFragment(Fragment fragment) {
        if (fragment != null) {
            fragment.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    //из адаптера onBindViewHolder
    //position позиция в листе
    @Override
    public void onClickk(int index) {
        Log.d("tag", "onClick: MainActivity position: " + index + " config: " + currentConfig.getId());
        if (!currentConfig.getParamsMenu().containsKey(Constants.ACTION_CONNOT_CLICK)) {
            return;
        }
        currentConfig = findConfig(6);
        eef = EditEntryFragment.newInstance(index, cont.getSortedList());
        currentConfig.setFragment(eef);
        startConfig(currentConfig);
    }

    @Override
    public void startCalendarDialog(String nameDialog) {
        DialogFragment newFragment;
        if ("date".equals(nameDialog)) {
            newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");

        }
        if ("time".equals(nameDialog)) {
            newFragment = new TimePickerFragment();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        }
    }

    @Override
    public void onListItemClick(int position) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (currentConfig.getId() != 0) {
            startConfig(findConfig(itemBeckHome));
        } else {
            startConfig(findConfig(0));
            new MaterialDialog.Builder(this)
                    .title(R.string.title_close_app)
                    .positiveText(R.string.item_ok)
                    .positiveColor(getResources().getColor(R.color.colorBlue))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .negativeText(R.string.item_no)
                    .negativeColor(getResources().getColor(R.color.colorBlue))
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    })
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d("tag", "onCreateOptionsMenu");
        getMenu = menu;
        startConfig(currentConfig);
        return super.onCreateOptionsMenu(menu);
    }

    public void sendDate(long date) {
        ((EditEntryFragment) currentConfig.getFragment()).setDate(date);

    }

    public void sendTime(long time) {
        ((EditEntryFragment) currentConfig.getFragment()).setTime(time);

    }

    public void createListByFilter(long dateFilter, int configImdex, int titleMenu) {
        RecyclerListFragment rlf = RecyclerListFragment.newInstance(dateFilter);
        if (configImdex > -1) {
            currentConfig = findConfig(configImdex);
            currentConfig.setFragment(rlf);
            if (titleMenu > 0) {
                currentConfig.setTitle(titleMenu);
            }
            startConfig(currentConfig);
        }
        String title= (dateFilter>0)?Util.getDateDMY(dateFilter):"Все записи";
            getSupportActionBar().setTitle(title);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                startConfig(findConfig(0));
                break;
            case R.id.action_sort:
                startConfig(findConfig(3));
                break;
            case R.id.action_delete:
                startConfig(findConfig(5));
                break;
            case R.id.action_new_event:
                currentConfig = findConfig(8);
                currentConfig.setFragment(new EditEntryFragment());
                startConfig(currentConfig);
                break;
            case R.id.action_events_list:
                Constants.ITEM_EVENT_DONE_CHECK = true;
                createListByFilter(0, 1, 0);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_done:
                createListByFilter(getRlfDateFilter(), 1, 0);
                break;
            case R.id.action_save_event:
                eef = (EditEntryFragment) currentConfig.getFragment();
                eef.saveEvent();
                startConfig(findConfig(1));
                getSupportActionBar().setTitle(Util.getDateDMY(getRlfDateFilter()));
                return true;
            case R.id.action_edit_sort_delete:
                Constants.ITEM_EVENT_DONE_CHECK = true;
                createListByFilter(getRlfDateFilter(), 7, 0);
                break;
            default:
                break;
        }
        return true;
    }

    private long getRlfDateFilter() {
        return ((RecyclerListFragment) currentConfig.getFragment()).getDateFilter();
    }

    @Override
    public void onClickCF(Calendar c) {

    }

    private Fragment getLastFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.findFragmentById(R.id.content);
     /* FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        return fragments.get(fragments.size() - 1);*/
    }
}
