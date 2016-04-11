package com.liz.mj.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liz.mj.R;

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
        this.mContext = context;
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
            //TODO 得到LayoutInflater有很多中方法 ,一般怎么简单怎么写，这是我同事写的。其实直接用View.inflate()也可以;
            /**
                获得 LayoutInflater 实例的三种方式
                1.LayoutInflater inflater = getLayoutInflater();  //调用Activity的getLayoutInflater()

                2.LayoutInflater localinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                3. LayoutInflater inflater = LayoutInflater.from(context);
             */

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.alertdialog_diy, null);
            //等价下面一行
            //View layout = View.inflate(context,R.layout.alertdialog_diy,null);
            //设置Dialog的Title,哈哈，我这么美的对话框当然没有title了,以后再加
//            layout.findViewById(R.id.title)
            if (message != null){
                ((TextView)layout.findViewById(R.id.dialog_message)).setText(message);
            }else if (contentView != null){//如果外部设置了contentView，恩，这个就是用来addview的
                LinearLayout mContent = (LinearLayout) layout.findViewById(R.id.content);
                mContent.removeAllViews();//删掉里面我的message
                mContent.addView(contentView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
                posBtn.setVisibility(View.GONE);
            }

            //下面该取消按钮
            Button negBtn = (Button)layout.findViewById(R.id.negativeButton);
            if (negativeButtonText != null){
                negBtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null){
                    negBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            }else{
                negBtn.setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

}




