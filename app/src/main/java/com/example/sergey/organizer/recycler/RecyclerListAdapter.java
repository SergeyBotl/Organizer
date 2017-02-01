
package com.example.sergey.organizer.recycler;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergey.organizer.Controller;
import com.example.sergey.organizer.R;
import com.example.sergey.organizer.constants.Constants;
import com.example.sergey.organizer.entity.Event;
import com.example.sergey.organizer.helper.ItemTouchHelperAdapter;
import com.example.sergey.organizer.helper.ItemTouchHelperViewHolder;
import com.example.sergey.organizer.helper.OnStartDragListener;
import com.example.sergey.organizer.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {
    private SimpleDateFormat dmy = new SimpleDateFormat(Constants.DATE_D_M_Y);
    private static List<Event> mItems = new ArrayList<>();
    private Context context;
    private String time, date;

    private Controller contr = new Controller();
    private final OnStartDragListener mDragStartListener;
    private RecyclerListFragment.OnClickListener mOnClickListener;
    private String dateFilter;

    public RecyclerListAdapter(Context context, OnStartDragListener dragStartListener
            , RecyclerListFragment.OnClickListener mOnClickListener, String dateFilter) {//, List<Event> mItems,
        mDragStartListener = dragStartListener;
        this.context = context;
        this.mItems =contr.getSortedList(); //mItems;
        this.mOnClickListener = mOnClickListener;
        this.dateFilter = dateFilter;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("tag",""+viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        // final Event event = cont.getSortedList().get(position);
        final Event event = mItems.get(position);

        long currentDate = new Date().getTime();

        if (dateFilter != null && !dmy.format(new Date(event.getDate())).equals(dateFilter)) {
            holder.itemRel.setVisibility(View.GONE);
            return;
        }
        if (!Constants.ITEM_EVENT_DONE_CHECK && event.isCheckDone()) {
            holder.itemRel.setVisibility(View.GONE);
            return;
        }

        if (event.getDate() == 0) {
            holder.textViewDate.setVisibility(View.GONE);
        }

        date = event.getDate() == 0 ? "" : Util.getEEDayMonth(event.getDate());
        time = event.getTime() == 0 ? "" : ", " + Util.getTime(event.getTime());

        holder.textViewDate.setText(date + " " + time);
        holder.chBox.setChecked(event.isCheckDone());

        final View checkB = holder.chBox;
        checkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.chBox.isChecked()) {
                    event.setCheckDone(true);
                    holder.textMsg.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    event.setCheckDone(false);
                    holder.textMsg.setPaintFlags(0);
                }
                contr.updateItemEvent(event, position);
       }

        });
        holder.textMsg.setText(event.getMsgEvent());

        if (event.isCheckDone()) {
            holder.textMsg.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (currentDate > event.getDate()) {
            holder.textViewDate.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }


        final int index = position;
        holder.textMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onClickk(index);
            }
        });

        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });


    }

    @Override//удаление
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        contr.saveList(mItems);
    }

    @Override //перемещение
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        contr.saveList(mItems);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        final LinearLayout itemRel;
        final CheckBox chBox;
        final TextView textMsg;
        final TextView textViewDate;
        // final TextView textViewTime;
        final ImageView handleView;
       // final ImageView handleViewDel;

        ItemViewHolder(View itemView) {
            super(itemView);

            itemRel = (LinearLayout) itemView.findViewById(R.id.lin_layout);
            textMsg = (TextView) itemView.findViewById(R.id.textMsg);
            chBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            textViewDate = (TextView) itemView.findViewById(R.id.textDate);
            // textViewTime = (TextView) itemView.findViewById(R.id.textTime);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
           // handleViewDel = (ImageView) itemView.findViewById(R.id.handle_del);
            if (Constants.ITEM_SORT || Constants.ITEM_DELETE) {
               // chBox.setVisibility(View.VISIBLE);
                handleView.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onItemSelected() {
            //изменяет цвет на серый
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            // изменяет цвет на белый
            itemView.setBackgroundColor(0);
        }
    }

    static class ViewHeader extends RecyclerView.ViewHolder {

        public ViewHeader(View itemView) {
            super(itemView);
        }
    }
}
