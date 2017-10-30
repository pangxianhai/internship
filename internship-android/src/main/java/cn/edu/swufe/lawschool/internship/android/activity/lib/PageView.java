package cn.edu.swufe.lawschool.internship.android.activity.lib;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;
import cn.edu.swufe.lawschool.internship.android.activity.util.AccessServiceUtil;
import cn.edu.swufe.lawschool.internship.android.activity.util.AlertDialogActivity;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageUtil;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017年05月04
 * <p>Title:       分页控件</p>
 * <p>Description: 分页控件</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class PageView<T> {

    private Page page;

    private List<T> dataList;

    private PageInterface pageInterface;

    private ListView listView;

    public interface PageInterface<T> {

        /**
         * Created on 2017年05月04
         * 获取上下文
         */
        Context getContext ();

        /**
         * Created on 2017年05月04
         * 获取listView
         */
        ListView getListView ();

        /**
         * Created on 2017年05月04
         * 加载数据接口
         */
        <T> PageResult<List<T>> loadData (Page page);

        /**
         * Created on 2017年05月04
         * 构造view
         */
        View makeView (View view, T data);
    }

    public PageView (final PageInterface pageInterface) {
        this.pageInterface = pageInterface;

        listView = pageInterface.getListView();

        loadData(listView, pageInterface);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int visibleLastIndex = 0;

            public void onScrollStateChanged (AbsListView view, int scrollState) {
                ListAdapter adapter = listView.getAdapter();
                if (adapter == null) {
                    return;
                } else {
                    int lastIndex = adapter.getCount() - 1;
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                            visibleLastIndex == lastIndex) {
                        loadData(listView, pageInterface);
                    }
                }
            }

            public void onScroll (AbsListView view, int firstVisibleItem, int visibleItemCount,
                                  int totalItemCount) {
                visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    public List<T> getDataList () {
        return dataList;
    }

    public void changeListView () {
        ((BaseAdapter) pageInterface.getListView().getAdapter()).notifyDataSetChanged();
    }

    /**
     * 重新加载
     */
    public void reload () {
        dataList = null;
        page = null;
        loadData(listView, pageInterface);
    }

    private void loadData (final ListView listView, final PageInterface pageInterface) {
        if (page != null && page.getCurrentPage() >= page.getTotalPage()) {
            AlertDialogActivity.dialog(pageInterface.getContext(), "已经到底了!");
            return;
        }
        AlertDialogActivity.loadingDialog(pageInterface.getContext(), "加载中...", true);
        AccessServiceUtil.callService(pageInterface.getContext(), new AccessServiceUtil.AccessInterface() {
            public T call () {
                return (T) pageInterface.loadData(PageUtil.nextPage(page));
            }

            public void handler (Result result) {
                AlertDialogActivity.closeDialog();
                if (result.getRet()) {
                    try{
                        PageResult<List<T>> pageResult = (PageResult<List<T>>) result.getData();
                        if (page == null) {
                            page = pageResult.getPage();
                        }
                        if (dataList == null) {
                            dataList = pageResult.getData();
                        } else {
                            dataList.addAll(new ArrayList(pageResult.getData()));
                        }
                        if (listView.getAdapter() == null) {
                            listViewSetAdapter(listView, pageInterface);
                        } else {
                            changeListView();
                        }
                    } catch (Exception e) {
                        Log.e(SystemInfo.LOG_TAG, "", e);
                    }
                } else {
                    AlertDialogActivity.dialog(pageInterface.getContext(), result.getMessage());
                }
            }
        });
    }

    private void listViewSetAdapter (ListView listView, final PageInterface pageInterface) {

        listView.setAdapter(new BaseAdapter() {

            public int getCount () {
                return dataList.size();
            }

            public Object getItem (int position) {
                return dataList.get(position);
            }

            public long getItemId (int position) {
                return position;
            }

            public View getView (int i, View view, ViewGroup viewGroup) {
                View afterView = pageInterface.makeView(view, dataList.get(i));
                return afterView;
            }
        });
    }
}

