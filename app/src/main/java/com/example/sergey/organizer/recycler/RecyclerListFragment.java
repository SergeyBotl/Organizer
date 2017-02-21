package com.example.sergey.organizer.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sergey.organizer.Controller;
import com.example.sergey.organizer.R;
import com.example.sergey.organizer.entity.Event;
import com.example.sergey.organizer.helper.OnStartDragListener;
import com.example.sergey.organizer.helper.SimpleItemTouchHelperCallback;
import com.example.sergey.organizer.util.Util;

import java.util.List;

public class RecyclerListFragment extends Fragment implements OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;
    private OnClickListener mOnClickListener;
    private Controller contr = new Controller();
    private List<Event> eventList;
    private long dateFilter;
    private EditText textAddEvent;
    private ImageView ivSengEvent;
    private RecyclerListAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);

        return view;
//         return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //убираю клавиатуру
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // eventList = contr.getSortedList();
        ivSengEvent = (ImageView) view.findViewById(R.id.imageButtonEntryNewEvent);
        textAddEvent = (EditText) view.findViewById(R.id.editTextAddEvent);
        dateFilter = 0;
        if (getArguments() != null) {
            dateFilter = getArguments().getLong("dateFilter", 0);
        }
        // RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewContainer);

        //Log.d("tag", "onViewCreated  getArguments() " + eventList.toString());
        adapter = new RecyclerListAdapter(getActivity(), this, mOnClickListener, Util.getDateDMY(dateFilter));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
       ivSengEvent.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (textAddEvent.getText().length()>0){
               // contr.saveNewEvent(new Event(0, textAddEvent.getText().toString()));
                adapter.addNewAventToAdapter(new Event(dateFilter, textAddEvent.getText().toString()));
                textAddEvent.setText("");
                adapter.notifyDataSetChanged();
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
          //возможность поменять меню из фрагмента
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null){
                // activity.getSupportActionBar().setTitle("hgjhgjhgj");
            }


        }
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
