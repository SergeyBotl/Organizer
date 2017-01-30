package com.example.sergey.organizer.helper;


 public interface ItemTouchHelperAdapter {
     boolean onItemMove(int fromPosition, int toPosition);

     void onItemDismiss(int position);
}
