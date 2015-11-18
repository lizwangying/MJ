package com.dongye.mj.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dongye.mj.R;

/**
 * description:一个自定义的透明的AlertDialog
 * author: lizwangying@icloud.com
 * date: 2015-11-16 15:02
 * version: 是
 */
public class TranslantAlertDialog extends Dialog {
    private Context mContext;

    public TranslantAlertDialog(Context context) {
        super(context);
        this.mContext = context;

    }


    public TranslantAlertDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = mContext;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.alertdialog_diy);
//    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message 消息
         * @return Builder实例
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title 就是个标题
         * @return Builder
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title 还是个标题
         * @return Builder
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText 取消按钮你想说啥？
         * @return Builder
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public TranslantAlertDialog create() {
            //初始化dialog，并且加上自定义的style
            final TranslantAlertDialog dialog = new TranslantAlertDialog(context, R.style.MyAlertDialog);
            //Layoutinflater 居然还要用到服务？
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.alertdialog_diy, null);
            //设置Dialog的Title,哈哈，我这么美的对话框当然没有title了,以后再加
//            layout.findViewById(R.id.title)
            layout.findViewById(R.id.dialog_message);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置message
            ((TextView)layout.findViewById(R.id.dialog_message)).setText(message);
            //设置确定button和他的监听事件！这个简直是绝了
            Button posBtn = (Button)layout.findViewById(R.id.positiveButton);
            if (positiveButtonText != null){
                posBtn.setText(positiveButtonText);
                if (positiveButtonClickListener != null){
                    posBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }else{
                //如果没有确定按钮就让它去死吧~GONE
                posBtn.setVisibility(View.GONE);
            }

            return dialog;
        }
    }

}




