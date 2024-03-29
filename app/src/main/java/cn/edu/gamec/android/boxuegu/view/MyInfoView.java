package cn.edu.gamec.android.boxuegu.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gamec.android.boxuegu.R;
import cn.edu.gamec.android.boxuegu.activity.LoginActivity;
import cn.edu.gamec.android.boxuegu.activity.PlayHistoryActivity;
import cn.edu.gamec.android.boxuegu.activity.SettingActivity;
import cn.edu.gamec.android.boxuegu.activity.UserInfoActivity;
import cn.edu.gamec.android.boxuegu.utils.AnalysisUtils;

/**
 * Created by student on 17/12/27.
 */

public class MyInfoView {
    private Activity mContext;

    private final LayoutInflater mInflater;

    private View mCurrentView;
    private LinearLayout ll_head;
    public ImageView iv_head_icon;

    private RelativeLayout rl_course_history;
    private RelativeLayout rl_setting;
    private TextView tv_user_name;

    public MyInfoView(Activity context) {
        mContext=context;
        //为之后将Layout转换为view时用
        mInflater = LayoutInflater.from ( mContext );
    }
    //获取当前在导航栏上方显示对应的View
    public View getView(){
        if (mCurrentView == null){
            createView();
        }
        return mCurrentView;
    }
    private void createView(){
        initView ();
    }
    //设置布局文件
    private void initView(){
        mCurrentView = mInflater.inflate ( R.layout.main_view_myinfo, null );
        ll_head = (LinearLayout) mCurrentView.findViewById ( R.id.ll_head );
        iv_head_icon = (ImageView ) mCurrentView.findViewById ( R.id.iv_head_icon );

        rl_course_history = (RelativeLayout) mCurrentView.findViewById ( R.id.rl_course_history );
        rl_setting = (RelativeLayout) mCurrentView.findViewById ( R.id.rl_setting );
        tv_user_name = (TextView) mCurrentView.findViewById ( R.id.tv_user_name );

        mCurrentView.setVisibility ( View.VISIBLE );
        setLoginParams(readLoginStatus ());//设置登录时界面控件的状态

        ll_head.setOnClickListener ( new View.OnClickListener (){
            @Override
            public void onClick(View v){
                //判断是否已经登录
                if (readLoginStatus ()){
                    //跳转到个人资料界面
                    Intent intent = new Intent(mContext,UserInfoActivity.class);
                    mContext.startActivity(intent);
                }else {
                    //跳转到登录界面
                    Intent intent = new Intent ( mContext, LoginActivity.class );
                    ((Activity) mContext).startActivityForResult ( intent, 1 );
                    //mContext.startActivityForResult(intent,1);
                }
            }
        } );
        rl_course_history.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (readLoginStatus ()){
                    //跳转到播放记录界面
                    Intent intent = new Intent ( mContext, PlayHistoryActivity.class );
                    mContext.startActivity ( intent );

                }else {
                    Toast.makeText ( mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT ).show ();
                }
            }
        } );

        rl_setting.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (readLoginStatus ()){

                    //跳转到设置界面
                    Intent intent = new Intent ( mContext, SettingActivity.class );
                    ((Activity) mContext).startActivityForResult ( intent, 1 );
                    //mContext.startActivityForResult(intent,1);

                }else {
                    Toast.makeText ( mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT ).show ();
                }
            }
        } );
    }

    //可见性  该private 为 public 获取登录名
    public void setLoginParams(boolean isLogin){
        if (isLogin){
            tv_user_name.setText ( AnalysisUtils.readLoginUserName ( mContext ) );
        }else {
            tv_user_name.setText ( "点击登录" );
        }
    }
    //从SharedPreferences中读取登录状态
    private boolean readLoginStatus(){
        SharedPreferences sp = mContext.getSharedPreferences ( "loginInfo", Context.MODE_PRIVATE );
        boolean isLogin = sp.getBoolean ( "isLogin", false );
        return isLogin;
    }

    public void showView() {
        if (mCurrentView == null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}
