package cn.edu.swufe.lawschool.internship.android.activity.footer;

import android.view.View;
import android.widget.LinearLayout;
import cn.edu.swufe.lawschool.internship.android.R;

/**
 * Created on 2017年04月28
 * <p>Title:       底部菜单</p>
 * <p>Description: 底部菜单</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class FooterMenu implements View.OnClickListener {

    /**
     * 首页菜单
     */
    LinearLayout home;

    /**
     * 公告菜单
     */
    LinearLayout bulletin;

    /**
     * 个人中心菜单
     */
    LinearLayout me;

    public FooterMenu (View view) {
        this.home = (LinearLayout) view.findViewById(R.id.footer_menu_home);
        this.bulletin = (LinearLayout) view.findViewById(R.id.footer_menu_bulletin);
        this.me = (LinearLayout) view.findViewById(R.id.footer_menu_me);
        this.bindFooterMenuClickAction();
    }

    private void bindFooterMenuClickAction () {
        this.home.setOnClickListener(this);
        this.bulletin.setOnClickListener(this);
        this.me.setOnClickListener(this);
    }

    public void onClick (View v) {
        switch (v.getId()) {
        case R.id.footer_menu_home:
            setMenuSelect();
            home.setSelected(true);
            break;
        case R.id.footer_menu_bulletin:
            setMenuSelect();
            bulletin.setSelected(true);
            break;
        case R.id.footer_menu_me:
            setMenuSelect();
            me.setSelected(true);
            break;
        default:
            break;
        }
    }

    private void setMenuSelect () {
        this.home.setSelected(false);
        this.bulletin.setSelected(false);
        this.me.setSelected(false);
    }

    public LinearLayout getHome () {
        return home;
    }

    public LinearLayout getBulletin () {
        return bulletin;
    }

    public LinearLayout getMe () {
        return me;
    }
}
