package com.example.lu.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lu.R;

public class LuDialog extends AlertDialog {
    public LuDialog(Context context) {
        super(context);
    }

    public LuDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String negativeButtonText;
        private String positiveButtonText;
        private boolean cancelable;
        private View contentView;
        private OnClickListener negativeButtonOnClickListener;
        private OnClickListener positiveButtonOnClickListener;
        private boolean isVertical = false;
        private int leftTextColor = -1;
        private int rightTextColor = -1;
        private int titleTextColor = -1;
        private int messageTextColor = -1;
        private float widthPrecent=0.8f;
        private float radius;
        private int bgColor;
        public void setRadius(int radius) {
            this.radius = radius;
        }
        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(int title) {
            this.title = context.getString(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = context.getString(message);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setLeftTextColor(int leftTextColor){
            this.leftTextColor = context.getResources().getColor(leftTextColor);
            return this;
        }

        public Builder setRightTextColor(int rightTextColor){
            this.rightTextColor = context.getResources().getColor(rightTextColor);
            return this;
        }

        public Builder setTitleTextColor(int titleTextColor){
            this.titleTextColor = context.getResources().getColor(titleTextColor);
            return this;
        }

        public Builder setMessageTextColor(int messageTextColor){
            this.messageTextColor = context.getResources().getColor(messageTextColor);
            return this;
        }

        public Builder isVertical(boolean isVertical) {
            this.isVertical = isVertical;
            return this;
        }

        /**
         * true:按返回键可dismiss
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        /**
         * 确定
         *
         * @param positiveButtonText
         * @param positiveOnClickListener
         * @return
         */
        public Builder setRightButton(String positiveButtonText, OnClickListener positiveOnClickListener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonOnClickListener = positiveOnClickListener;
            return this;
        }
        /**
         * 确定
         *
         * @param positiveOnClickListener
         * @return
         */
        public Builder setRightButton( OnClickListener positiveOnClickListener) {
            this.positiveButtonOnClickListener = positiveOnClickListener;
            return this;
        }

        public Builder setRightButton(int positiveButtonText, OnClickListener positiveOnClickListener) {
            this.positiveButtonText = context.getString(positiveButtonText);
            this.positiveButtonOnClickListener = positiveOnClickListener;
            return this;
        }

        public Builder setLeftButton(String negativeButtonText, OnClickListener negativeOnClickListener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonOnClickListener = negativeOnClickListener;
            return this;
        }
        public Builder setLeftButton(OnClickListener negativeOnClickListener) {
            this.negativeButtonOnClickListener = negativeOnClickListener;
            return this;
        }

        /**
         * set the cancel button  
         *
         * @param negativeButtonText
         * @param negativeOnClickListener
         * @return
         */
        public Builder setLeftButton(int negativeButtonText, OnClickListener negativeOnClickListener) {
            this.negativeButtonText = context.getString(negativeButtonText);
            this.negativeButtonOnClickListener = negativeOnClickListener;
            return this;
        }

        public Builder setView(View view) {
            this.contentView = view;
            return this;
        }
        public LuDialog create() {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            final LuDialog dialog = new LuDialog(context, R.style.dialog);
            View layout = layoutInflater.inflate(isVertical ? R.layout.common_dialog_vertical_layout : R.layout.common_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCancelable(cancelable); //true:按返回键可dismiss
            if (!isVertical) {
                dialog.getWindow().getAttributes().width = (int) (Utils.getScreenWidth(context) * widthPrecent);
                LuLinearLayout luLinearLayout= (LuLinearLayout) layout;
                luLinearLayout.setRadius(radius);
                luLinearLayout.setBgColor(bgColor);

            }
            if (!TextUtils.isEmpty(title)) {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
                if(titleTextColor!=-1) {
                    ((TextView) layout.findViewById(R.id.title)).setTextColor(titleTextColor);
                }
            } else {
                layout.findViewById(R.id.title).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(message)) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
                if(messageTextColor!=-1) {
                    ((TextView) layout.findViewById(R.id.message)).setTextColor(messageTextColor);
                }
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }
            if (!TextUtils.isEmpty(positiveButtonText)) {
                ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);

                (layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (positiveButtonOnClickListener != null) {
                            positiveButtonOnClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    }
                });
                if(rightTextColor!=-1){
                    ((Button) layout.findViewById(R.id.positiveButton)).setTextColor(rightTextColor);
                }

            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
                layout.findViewById(R.id.bottom_line).setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(negativeButtonText)) {
                ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);

                (layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (negativeButtonOnClickListener != null) {
                            negativeButtonOnClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    }
                });
                if(leftTextColor!=-1){
                    ((Button) layout.findViewById(R.id.negativeButton)).setTextColor(leftTextColor);
                }

            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
                layout.findViewById(R.id.bottom_line).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }
    }



}