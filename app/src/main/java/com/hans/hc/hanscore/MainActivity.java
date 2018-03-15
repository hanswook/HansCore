package com.hans.hc.hanscore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hans.greendao.gen.UserDao;
import com.hans.hc.hanscore.libpack.BaseActivity;
import com.hans.hc.hanscore.libpack.DataBaseHelper;
import com.hans.hc.hanscore.libpack.RetrofitManager;
import com.hans.hc.hanscore.libpack.User;
import com.njqg.orchard.library_core.net.CommonSubscriber;
import com.njqg.orchard.library_core.utils.LogUtils;

import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {


    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;

    private UserDao userDao;
    @Override
    protected void init() {
//        requestGank();
        initDatas();
        initBtns();
    }

    private void initDatas() {
        userDao=DataBaseHelper.getInstance().getDaoSession().getUserDao();
    }

    List<User> aaa = new ArrayList<>();

    private void initBtns() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(null,"韩大",11,true);

                userDao.insert(user);
                User user2 = new User(null,"han er 楞",222,true);

                userDao.insert(user2);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaa = userDao.queryBuilder().list();
                LogUtils.e(TAG, "aaa:" + aaa.size());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aaa.size() > 0) {
                    LogUtils.e(TAG, "aaa:" + aaa.size());
                    for (User user : aaa) {
                        LogUtils.e(TAG, "user:" + user.toString());

                    }
                }
            }
        });
        
        
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.deleteByKey(userDao.getKey(userDao.queryBuilder().list().get(2)));
            }
        });


    }

    private void requestGank() {
        RetrofitManager.getInstance().create(ApiService.class).getGankData().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CommonSubscriber<GankB>(context) {
                    @Override
                    public void onNext(GankB gankB) {
                        LogUtils.e(TAG, "gankb:" + gankB.getResults().get(0).getDesc());
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


}
