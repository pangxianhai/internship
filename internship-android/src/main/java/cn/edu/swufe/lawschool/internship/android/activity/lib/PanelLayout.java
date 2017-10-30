package cn.edu.swufe.lawschool.internship.android.activity.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import cn.edu.swufe.lawschool.internship.android.R;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.util.TranslateAnimationUtil;

/**
 * Created on 2017年05月09
 * <p>Title:       面板控件</p>
 * <p>Description: 面板控件</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class PanelLayout extends LinearLayout {

    private Context context;

    private View coverView;

    private ViewDragHelper mDragHelper;

    private Status status = Status.Close;

    private Direction direction;

    private int duration;

    public PanelLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PanelLayout);
        int positionCode = typedArray.getInteger(R.styleable.PanelLayout_position, Direction.RIGHT.code);
        direction = Direction.parse(positionCode);
        duration = typedArray.getInteger(R.styleable.PanelLayout_duration, 500);
        mDragHelper = ViewDragHelper.create(this, buildCallback());
    }

    protected void onFinishInflate () {
        super.onFinishInflate();
        coverView = this.findViewById(R.id.panel_layout_cover);
        close();
    }

    public void open () {
        Animation animation = null;
        switch (direction) {
        case LEFT:
            animation = TranslateAnimationUtil.makeInAnimationFromLeft(duration);
            break;
        case RIGHT:
            animation = TranslateAnimationUtil.makeInAnimationFromRight(duration);
            break;
        case UP:
            animation = TranslateAnimationUtil.makeInAnimationFromTop(duration);
            break;
        case DOWN:
            animation = TranslateAnimationUtil.makeInAnimationFromBottom(duration);
            break;
        }
        if (animation != null) {
            this.setVisibility(VISIBLE);
            this.status = Status.Open;

            this.setAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart (Animation animation) {

                }

                public void onAnimationEnd (Animation animation) {
                    if (coverView != null) {
                        coverView.setVisibility(VISIBLE);
                    }
                }

                public void onAnimationRepeat (Animation animation) {

                }
            });
        }
    }

    public void close () {
        close(false, null);
    }

    public void close (boolean swipe, Direction swipeDirection) {
        Animation animation = null;
        switch (direction) {
        case LEFT:
            if (!swipe || (swipe && swipeDirection.equals(Direction.LEFT))) {
                animation = TranslateAnimationUtil.makeOutAnimationToLeft(duration);
            }
            break;
        case RIGHT:
            if (!swipe || (swipe && swipeDirection.equals(Direction.RIGHT))) {
                animation = TranslateAnimationUtil.makeOutAnimationToRight(duration);
            }
            break;
        case UP:
            if (!swipe || (swipe && swipeDirection.equals(Direction.UP))) {
                animation = TranslateAnimationUtil.makeOutAnimationToTop(duration);
            }
            break;
        case DOWN:
            if (!swipe || (swipe && swipeDirection.equals(Direction.DOWN))) {
                animation = TranslateAnimationUtil.makeOutAnimationToBottom(duration);
            }
            break;
        }

        if (animation != null) {
            this.setVisibility(GONE);
            this.status = Status.Close;
            this.setAnimation(animation);
            if (coverView != null) {
                coverView.setVisibility(INVISIBLE);
            }
        }
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

    private ViewDragHelper.Callback buildCallback () {
        ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

            public boolean tryCaptureView (View child, int pointerId) {
                return true;
            }

            public int clampViewPositionHorizontal (View child, int left, int dx) {
                //只滑不动
                return (int) child.getX();
            }

            public void onViewReleased (View releasedChild, float xvel, float yvel) {
                if (status == Status.Close) {
                    return;
                }
                Direction swipeDirection;
                if (Math.abs(xvel) > Math.abs(yvel)) {
                    if (xvel < 0) {
                        swipeDirection = Direction.LEFT;
                    } else {
                        swipeDirection = Direction.RIGHT;
                    }
                } else {
                    if (yvel < 0) {
                        swipeDirection = Direction.UP;
                    } else {
                        swipeDirection = Direction.DOWN;
                    }
                }
                close(true, swipeDirection);
            }
        };
        return callback;
    }

    public enum Direction {
        LEFT(1),
        RIGHT(2),
        UP(3),
        DOWN(4);

        private int code;

        Direction (int code) {
            this.code = code;
        }

        public boolean equals (Direction d) {
            if (this == d) {
                return true;
            } else {
                return this.code == d.code;
            }
        }

        public static Direction parse (int code) {
            for (Direction d : Direction.values()) {
                if (d.code == code) {
                    return d;
                }
            }
            return null;
        }
    }

    public enum Status {
        Open,
        Close
    }
}
