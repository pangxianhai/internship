package cn.edu.swufe.lawschool.internship.android.activity.lib;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.util.AlertDialogActivity;

/**
 * Created on 2017年05月04
 * <p>Title:       滑动layout</p>
 * <p>Description: 滑动layout</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class SwipeLayout extends FrameLayout {

    /**
     * 影藏的view
     */
    private View mHiddenView;

    /**
     * 影藏的view 宽度
     */
    private int mHiddenViewWidth;

    /**
     * 影藏的view 高度
     */
    private int mHiddenViewHeight;

    /**
     * 最上层的view 即显示的view
     */
    private View mItemView;

    /**
     * 最上层的view宽度
     */
    private int mItemViewWidth;

    /**
     * 最上层的view高度
     */
    private int mItemViewHeight;

    /**
     * 是否过度效果
     */
    private boolean smooth = true;

    private boolean isScroll = true;

    private ViewDragHelper mDragHelper;

    private Status preStatus = Status.Close;

    private Status status = Status.Close;

    private OnSwipeStatusListener listener;

    private Context context;

    // 状态
    public enum Status {
        Open,
        Close
    }

    public enum Position {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public interface OnSwipeStatusListener {

        /**
         * 当状态改变时回调
         * @param status
         */
        void onStatusChanged (Status status);

        /**
         * 开始执行Open动画
         */
        void onStartCloseAnimation ();

        /**
         * 开始执行Close动画
         */
        void onStartOpenAnimation ();

    }

    public SwipeLayout (Context context) {
        this(context, null);
    }

    public SwipeLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mDragHelper = ViewDragHelper.create(this, buildCallback());
    }

    public void setOnSwipeStatusListener (OnSwipeStatusListener listener) {
        this.listener = listener;
    }

    public void setIsScroll (boolean scroll) {
        isScroll = scroll;
    }

    public View getItemView () {
        return mItemView;
    }

    @Override
    protected void onFinishInflate () {
        super.onFinishInflate();
        mHiddenView = getChildAt(0);
        mItemView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mItemViewWidth = mItemView.getMeasuredWidth();
        mItemViewHeight = mItemView.getMeasuredHeight();
        mHiddenViewWidth = mHiddenView.getMeasuredWidth();
        mHiddenViewHeight = mHiddenView.getMeasuredHeight();
    }

    private ViewDragHelper.Callback buildCallback () {
        ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

            private Position position;

            public boolean tryCaptureView (View child, int pointerId) {
                return child == mItemView && isScroll;
            }

            public int clampViewPositionHorizontal (View child, int left, int dx) {
                if (child != mItemView) {
                    return 0;
                }
                if (left > 0) {
                    left = 0;
                } else {
                    left = Math.max(left, -mHiddenViewWidth);
                }
                return left;
            }

            @Override
            public int getViewHorizontalDragRange (View child) {
                return mHiddenViewWidth;
            }

            @Override
            public void onViewPositionChanged (View changedView, int left, int top, int dx, int dy) {
                if (mItemView == changedView) {
                    mHiddenView.offsetLeftAndRight(dx);
                }
                if (dx < -20) {
                    position = Position.LEFT;
                } else if (dx > 20) {
                    position = Position.RIGHT;
                }
                invalidate();
            }

            public void onViewReleased (View releasedChild, float xvel, float yvel) {
                if (releasedChild == mItemView) {
                    if (xvel == 0) {
                        if (position == Position.LEFT) {
                            open(smooth);
                        } else {
                            close(smooth);
                        }
                    } else if (xvel < 0) {
                        open(smooth);
                    } else {
                        close(smooth);
                    }
                }
            }
        };
        return callback;
    }

    public void setSmooth (boolean smooth) {
        this.smooth = smooth;
    }

    public boolean onInterceptTouchEvent (MotionEvent event) {
        final int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    public boolean onTouchEvent (MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        layout(Status.Close);
    }

    private void layout (Status status) {
        if (status == Status.Close) {
            mHiddenView.layout(mItemViewWidth, 0, mItemViewWidth + mHiddenViewWidth, mItemViewHeight);
            mItemView.layout(0, 0, mItemViewWidth, mItemViewHeight);
        } else {
            mHiddenView.layout(mItemViewWidth - mHiddenViewWidth, 0, mItemViewWidth, mItemViewHeight);
            mItemView.layout(-mHiddenViewWidth, 0, mItemViewWidth - mHiddenViewWidth, mItemViewHeight);
        }
    }

    /**
     * 侧滑打开
     * @param smooth 为true则有平滑的过渡动画
     */
    public void open (boolean smooth) {
        preStatus = status;
        status = Status.Open;
        if (smooth) {
            if (mDragHelper.smoothSlideViewTo(mItemView, -mHiddenViewWidth, 0)) {
                if (listener != null) {
                    listener.onStartOpenAnimation();
                }
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layout(status);
        }
        if (listener != null && preStatus == Status.Close) {
            listener.onStatusChanged(status);
        }
    }

    /**
     * 侧滑关闭
     * @param smooth 为true则有平滑的过渡动画
     */
    public void close (boolean smooth) {
        preStatus = status;
        status = Status.Close;
        if (smooth) {
            if (mDragHelper.smoothSlideViewTo(mItemView, 0, 0)) {
                if (listener != null) {
                    listener.onStartCloseAnimation();
                }
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layout(status);
        }
        if (listener != null && preStatus == Status.Open) {
            listener.onStatusChanged(status);
        }
    }

    @Override
    public void computeScroll () {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
