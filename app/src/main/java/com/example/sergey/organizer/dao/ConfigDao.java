package com.example.sergey.organizer.dao;

import com.example.sergey.organizer.constants.Const;
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
        itemMenu.add(new ChooseMenu(8, R.id.action_event_check_done));


        Map<Integer, String> paramsMenu = new HashMap<>();
        // SparseBooleanArray paramsMenu = new SparseBooleanArray();

        paramsMenu.put(3, "");
        listConfig.add(new Config(Const.CONFIG_CALENDAR, "calendar", R.string.title_calendar, new CalendarFragment(), false, paramsMenu, 0));

        paramsMenu = new HashMap<>();
        paramsMenu.put(8, "");
        paramsMenu.put(4, "");

        // paramsMenu.put(Constants.MENU_TITLE,"");
        paramsMenu.put(Const.ACTION_CONNOT_CLICK, "");
        listConfig.add(new Config(Const.CONFIG_LIST, "list", 0, new RecyclerListFragment(), true, paramsMenu, 0));

        paramsMenu = new HashMap<>();
        paramsMenu.put(0, "");
        paramsMenu.put(1, "");
        // paramsMenu.put(4, true);
        listConfig.add(new Config(Const.CONFIG_EDIT_SORT_DELETE, "edit_sort_delete", R.string.title_list, new RecyclerListFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(6, "");
        paramsMenu.put(Const.ACTION_ITEM_SORT, "");
        listConfig.add(new Config(Const.CONFIG_SORT, "sort", R.string.title_sort, new RecyclerListFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(6, "");
        paramsMenu.put(Const.ACTION_ITEM_DELETE, "");
        listConfig.add(new Config(Const.CONFIG_DELETE, "delete", R.string.title_delete, new RecyclerListFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(5, "");
        listConfig.add(new Config(Const.CONFIG_EDIT_EVENT, "edit event", R.string.title_edit, new EditEntryFragment(), true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(6, "");
        paramsMenu.put(Const.ACTION_ITEM_DELETE, "");
        paramsMenu.put(Const.ACTION_ITEM_SORT, "");
        listConfig.add(new Config(Const.CONFIG_DELETE_2, "delete", R.string.title_edit, null, true, paramsMenu, 1));

        paramsMenu = new HashMap<>();
        paramsMenu.put(5, "");
        listConfig.add(new Config(Const.CONFIG_NEW_EWENT, "new event", R.string.title_new_event, new EditEntryFragment(), true, paramsMenu, 1));

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
