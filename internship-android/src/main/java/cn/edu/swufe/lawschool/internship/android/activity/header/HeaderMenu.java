package cn.edu.swufe.lawschool.internship.android.activity.header;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.swufe.lawschool.internship.android.R;

/**
 * Created on 2017年04月28
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class HeaderMenu {
    /**
     * 顶部菜单左边按钮
     */
    Button topMenuLeft;

    /**
     * 顶部菜单右边
     */
    Button topMenuRight;

    /**
     * 顶部菜单文字
     */

    TextView topMenuTitle;

    public HeaderMenu (View v) {
        topMenuRight = (Button) v.findViewById(R.id.top_menu_right);
        topMenuLeft = (Button) v.findViewById(R.id.top_menu_left);
        topMenuTitle = (TextView) v.findViewById(R.id.top_menu_title);
    }

    public Button getTopMenuLeft () {
        return topMenuLeft;
    }

    public Button getTopMenuRight () {
        return topMenuRight;
    }

    public TextView getTopMenuTitle () {
        return topMenuTitle;
    }
}
