package com.example.sergey.organizer.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;


public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    //прозрачность перетаскиваемого итема, после перетаскивания остается таже прозрачность
    public static final float ALPHA_FULL = 1.0f;

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
        //return Constants.ITEM_SORT;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
       // return Constants.ITEM_DELETE;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager

        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //ItemTouchHelper.END;<- разрешение на здвиг в право    ItemTouchHelper.START <-разрешение на  здвиг в лево
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        //swipeFlags   <-  если ноль то удалять нельзя
        return makeMovementFlags(dragFlags, swipeFlags);//
        // }
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // Notify the adapter of the move
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("tag", "onSwiped");
        // Notify the adapter of the dismissal
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override//на RecyclerListAdapter не влияет
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Log.d("tag", "dX " + dX + " dY " + dY + " actionState " + actionState + " (float) viewHolder.itemView.getWidth() " + (float) viewHolder.itemView.getWidth());

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            //без этого при удалении нет анимации
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            // viewHolder.itemView.setTranslationX(dX);
            viewHolder.itemView.setTranslationX(dX);
            Log.d("tag", " alpha " + alpha + " dx " + dX);
        } else {
            //эта строчка отображение премещения строки с анимацией на место которой переносится перемещаемая строчка
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(ALPHA_FULL);

        if (viewHolder instanceof ItemTouchHelperViewHolder) {
            // Tell the view holder it's time to restore the idle state
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }

}
