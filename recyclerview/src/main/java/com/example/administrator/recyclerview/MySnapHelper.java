package com.example.administrator.recyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * @author lijunjie on 2018/6/4 0004.
 * @description
 */

public class MySnapHelper extends LinearSnapHelper {

    // 左对齐
    public static final int TYPE_SNAP_START = 2;

    // 右对齐
    public static final int TYPE_SNAP_END = 3;

    // default
    private int type = TYPE_SNAP_START;

    @Nullable
    private OrientationHelper mVerticalHelper;
    @Nullable
    private OrientationHelper mHorizontalHelper;


    public MySnapHelper(int type) {
        this.type = type;
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        if (type == TYPE_SNAP_START) {
            return calculateDisOnStart(layoutManager, targetView);
        } else if (type == TYPE_SNAP_END) {
            return calculateDisOnEnd(layoutManager, targetView);
        } else {
            return super.calculateDistanceToFinalSnap(layoutManager, targetView);
        }
    }

    private int[] calculateDisOnStart(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(layoutManager, targetView,
                    getmHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(layoutManager, targetView,
                    getmVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    private int distanceToStart(@NonNull RecyclerView.LayoutManager layoutManager,
                                @NonNull View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    private int[] calculateDisOnEnd(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToEnd(layoutManager, targetView,
                    getmHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToEnd(layoutManager, targetView,
                    getmVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    private int distanceToEnd(@NonNull RecyclerView.LayoutManager layoutManager,
                              @NonNull View targetView, OrientationHelper helper) {
        return helper.getDecoratedEnd(targetView) - helper.getEndAfterPadding();
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (type == TYPE_SNAP_START) {
            return findStartSnapView(layoutManager);
        } else if (type == TYPE_SNAP_END) {
            return findEndSnapView(layoutManager);
        } else {
            return super.findSnapView(layoutManager);
        }
    }

    private View findStartSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return findStartView(layoutManager, getmVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            return findStartView(layoutManager, getmHorizontalHelper(layoutManager));
        }
        return null;
    }

    private View findEndSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return findEndView(layoutManager, getmVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            return findEndView(layoutManager, getmHorizontalHelper(layoutManager));
        }
        return null;
    }

    private View findStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        if (!(layoutManager instanceof LinearLayoutManager)) { // only for LinearLayoutManager
            return null;
        }
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }
        View closestChild = null;
        final int start = helper.getStartAfterPadding();
        int absClosest = Integer.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedStart(child);
            int absDistance = Math.abs(childStart - start);
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        View firstVisibleChild = layoutManager.getChildAt(0);
        if (firstVisibleChild != closestChild) {
            return closestChild;
        }
        int firstChildStart = helper.getDecoratedStart(firstVisibleChild);
        int lastChildPos = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        View lastChild = layoutManager.getChildAt(childCount - 1);
        int lastChildCenter = helper.getDecoratedStart(lastChild) + (helper.getDecoratedMeasurement(lastChild) / 2);
        boolean isEndItem = lastChildPos == layoutManager.getItemCount() - 1;
        if (isEndItem && firstChildStart < 0 && lastChildCenter < helper.getEnd()) {
            return lastChild;
        }
        return closestChild;
    }

    private View findEndView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        if (!(layoutManager instanceof LinearLayoutManager)) { // only for LinearLayoutManager
            return null;
        }
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }
        if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == 0) {
            return null;
        }
        View closestChild = null;
        final int end = helper.getEndAfterPadding();
        int absClosest = Integer.MAX_VALUE;
        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedEnd(child);
            int absDistance = Math.abs(childStart - end);

            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        View lastVisibleChild = layoutManager.getChildAt(childCount - 1);
        if (lastVisibleChild != closestChild) {
            return closestChild;
        }
        if (layoutManager.getPosition(closestChild) == ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition()) {
            return closestChild;
        }
        View firstChild = layoutManager.getChildAt(0);
        int firstChildStart = helper.getDecoratedStart(firstChild);
        int firstChildPos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        boolean isFirstItem = firstChildPos == 0;
        int firstChildCenter = helper.getDecoratedStart(firstChild) + (helper.getDecoratedMeasurement(firstChild) / 2);
        if (isFirstItem && firstChildStart < 0 && firstChildCenter > helper.getStartAfterPadding()) {
            return firstChild;
        }
        return closestChild;
    }

    @Nullable
    public OrientationHelper getmVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    @Nullable
    public OrientationHelper getmHorizontalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }
}
