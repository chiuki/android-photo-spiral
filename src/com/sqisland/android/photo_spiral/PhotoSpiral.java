package com.sqisland.android.photo_spiral;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class PhotoSpiral extends ViewGroup {
  public PhotoSpiral(Context context) {
    super(context);
  }

  public PhotoSpiral(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public PhotoSpiral(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    measureChildren(widthSpec, heightSpec);
    View first = getChildAt(0);
    int size = first.getMeasuredWidth() + first.getMeasuredHeight();
    int width = ViewGroup.resolveSize(size, widthSpec);
    int height = ViewGroup.resolveSize(size, heightSpec);
    setMeasuredDimension(width, height);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    // Assume all children are of the same dimensions
    // Order: landscape, portrait, landscape, portrait

    View first = getChildAt(0);
    final int childWidth = first.getMeasuredWidth();
    final int childHeight = first.getMeasuredHeight();

    int x = getWidth() / 2 - (childWidth - childHeight) / 2;
    int y = getHeight() / 2 - (childWidth + childHeight) / 2;

    for (int i = 0; i < getChildCount(); ++i) {
      View child = getChildAt(i);
      x = adjustX(i, x, childWidth, childHeight);
      y = adjustY(i, y, childWidth, childHeight);
      child.layout(x, y,
          x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
    }
  }

  private int adjustX(int pos, int x, int childWidth, int childHeight) {
    int delta = childWidth - childHeight;
    switch (pos) {
    case 1:
      return x + delta;
    case 2:
      return x - childWidth;
    }
    return x;
  }

  private int adjustY(int pos, int y, int childWidth, int childHeight) {
    int delta = childWidth - childHeight;
    switch (pos) {
    case 1:
      return y + childHeight;
    case 2:
      return y + delta;
    case 3:
      return y - childWidth;
    }
    return y;
  }
}
