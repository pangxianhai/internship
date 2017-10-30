package cn.edu.swufe.lawschool.internship.android.activity.util;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created on 2017年05月10
 * <p>Title:       view动画效果</p>
 * <p>Description: view动画效果</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class TranslateAnimationUtil {

    /**
     * 从右边滑入
     * @param duration 动画时间
     * @return
     */
    public static Animation makeInAnimationFromRight (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从右边滑出
     * @param duration 动画时间
     * @return
     */
    public static Animation makeOutAnimationToRight (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从左边滑入
     * @param duration 动画时间
     * @return
     */
    public static Animation makeInAnimationFromLeft (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从左边滑出
     * @param duration 动画时间
     * @return
     */
    public static Animation makeOutAnimationToLeft (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从上边滑入
     * @param duration 动画时间
     * @return
     */
    public static Animation makeInAnimationFromTop (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从上边滑出
     * @param duration 动画时间
     * @return
     */
    public static Animation makeOutAnimationToTop (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从下边滑入
     * @param duration 动画时间
     * @return
     */
    public static Animation makeInAnimationFromBottom (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 从下边滑出
     * @param duration 动画时间
     * @return
     */
    public static Animation makeOutAnimationToBottom (int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }
}
