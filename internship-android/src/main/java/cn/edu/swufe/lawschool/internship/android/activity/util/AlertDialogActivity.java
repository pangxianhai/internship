package cn.edu.swufe.lawschool.internship.android.activity.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.*;
import android.widget.*;
import cn.edu.swufe.lawschool.internship.android.R;

/**
 * Created on 2017年04月23
 * <p>Title:       弹框</p>
 * <p>Description: 弹框</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AlertDialogActivity {

    private static Dialog dialog;

    public static void dialog (Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void yesNoDialog (Context context, String title, String message) {
        yesNoDialog(context, title, message, null);
    }

    public static void yesNoDialog (Context context, String title, String message, final Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialogInterface, int i) {
                if (callback != null) {
                    callback.yesClick();
                }
            }
        }).setNegativeButton("否", null).show();
    }

    public static Dialog loadingDialog (Context context, String msg, boolean isCancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_view);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
        tipTextView.setText(msg);

        Dialog loadingDialog = new Dialog(context, R.style.LoadingDialogStyle);
        loadingDialog.setCancelable(isCancelable);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();
        dialog = loadingDialog;
        return loadingDialog;
    }

    public static void closeDialog (Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void closeDialog () {
        closeDialog(dialog);
    }

    public interface Callback {
        void yesClick ();
    }

}
