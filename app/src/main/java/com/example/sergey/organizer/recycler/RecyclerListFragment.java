package com.example.sergey.organizer.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.sergey.organizer.Controller;
import com.example.sergey.organizer.util.Util;
import com.example.sergey.organizer.entity.Event;
import com.example.sergey.organizer.helper.OnStartDragListener;
import com.example.sergey.organizer.helper.SimpleItemTouchHelperCallback;

import java.util.List;

public class RecyclerListFragment extends Fragment implements OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;
    private OnClickListener mOnClickListener;
    private Controller contr = new Controller();
    private List<Event> eventList;
    private long dateFilter;

    public RecyclerListFragment() {
    }

    public static RecyclerListFragment newInstance(long dateFilter) {//int index, List<Event> event,
        RecyclerListFragment rlf = new RecyclerListFragment();
        Bundle args = new Bundle();
        Log.d("tag", "newInstance  " + dateFilter);
        // args.putParcelableArrayList("event", (ArrayList<? extends Parcelable>) event);
        args.putLong("dateFilter", dateFilter);
        // args.putInt("index", index);
        rlf.setArguments(args);
        return rlf;
    }

    public interface OnClickListener {
        void onClickk(int index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mOnClickListener = (OnClickListener) getActivity();
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //убираю клавиатуру
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //TODO
        // eventList = contr.getSortedList();
        eventList = contr.getList();
        dateFilter = 0;
        if (getArguments() != null) {
            dateFilter = getArguments().getLong("dateFilter", 0);
        }
        RecyclerView recyclerView = (RecyclerView) view;
        //Log.d("tag", "onViewCreated  getArguments() " + eventList.toString());
        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, mOnClickListener, eventList, Util.getDateDMY(dateFilter));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public long getDateFilter() {
        return dateFilter;
    }
}
