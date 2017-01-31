package com.example.sergey.organizer.dao;

import com.example.sergey.organizer.constants.Constants;
import com.example.sergey.organizer.R;
import com.example.sergey.organizer.entity.ChooseMenu;
import com.example.sergey.organizer.entity.Config;
import com.example.sergey.organizer.calendar.CalendarFragment;
import com.example.sergey.organizer.fragment.EditEntryFragment;
import com.example.sergey.organizer.recycler.RecyclerListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigDao {

    private static List<Config> listConfig = new ArrayList<>();
    //private static Map<Integer, Integer> itemMenu;
    private static List<ChooseMenu> itemMenu;


    public ConfigDao() {
        itemMenu = new ArrayList<>();
        itemMenu.add(new ChooseMenu(0, R.id.action_sort));
        itemMenu.add(new ChooseMenu(1, R.id.action_delete));
        itemMenu.add(new ChooseMenu(2, R.id.action_new_event));
        itemMenu.add(new ChooseMenu(3, R.id.action_events_list));
        itemMenu.add(new ChooseMenu(4, R.id.action_calendar));
        itemMenu.add(new ChooseMenu(5, R.id.action_save_event));
        itemMenu.add(new ChooseMenu(6, R.id.action_done));
        itemMenu.add(new ChooseMenu(7, R.id.action_edit_sort_delete));


        Map<Integer, String> paramsMenu = new HashMap<>();
        // SparseBooleanArray paramsMenu = new SparseBooleanArray();

        paramsMenu.put(3, "");
   listConfig.add(new Config(0, "start page", R.string.title_calendar, new CalendarFragment(), false, paramsMenu, 0));

        paramsMenu = new HashMap<>();

//        paramsMenu.put(7, "");
        paramsMenu.put(2, "");
        paramsMenu.put(4, "");

       // paramsMenu.put(Constants.MENU_TITLE,"");

        paramsMenu.put(Constants.ACTION_CONNOT_CLICK, "");
        listConfig.add(new Config(1, "list", 0, new RecyclerListFragment(), true, paramsMenu, 0));

        paramsMenu = new HashMap<>();
        paramsMenu.put(0, "");
        paramsMenu.put(1, "");
        // paramsMenu.put(4, true);
        listConfig.add(new Config(2, "edit_sort_delete", R.string.title_list, new RecyclerListFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(6, "");
        paramsMenu.put(Constants.ACTION_ITEM_SORT, "");
        listConfig.add(new Config(3, "sort", R.string.title_sort, new RecyclerListFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(6, "");
        paramsMenu.put(Constants.ACTION_ITEM_DELETE, "");
        listConfig.add(new Config(5, "delete", R.string.title_delete, new RecyclerListFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(5, "");
        listConfig.add(new Config(6, "edit event", R.string.title_edit, new EditEntryFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(6, "");
        paramsMenu.put(Constants.ACTION_ITEM_DELETE, "");
        paramsMenu.put(Constants.ACTION_ITEM_SORT, "");
        listConfig.add(new Config(7, "delete", R.string.title_edit, null, true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(5, "");
        listConfig.add(new Config(8, "new event", R.string.title_new_event, new EditEntryFragment(), true, paramsMenu, 1));

    }


    public Config findConfig(int id) {
        for (Config c : listConfig) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<ChooseMenu> getItemMenu() {
        return itemMenu;
    }

    public List<Config> getListConfig() {
        return listConfig;
    }
}
