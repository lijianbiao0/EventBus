package com.hofo.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hofo.eventbus.bean.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    EventSrc1 reg1 = new EventSrc1();
    EventSrc2 reg2 = new EventSrc2();
    private TextView tv;
    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.message);
    }

    public void reg1(View view) {
        EventBus.getDefault().register(reg1);
    }

    public void reg2(View view) {
        EventBus.getDefault().register(reg2);
    }

    public void unreg(View view) {
        cont = 0;
        EventBus.getDefault().unregister(reg1);
        EventBus.getDefault().unregister(reg2);
    }

    public void post(View view) {
        EventBus.getDefault().post(new EventMessage("我是即时消息，谁注册谁接收！"));
    }

    public void sticky(View view) {
        EventBus.getDefault().postSticky(new EventMessage("我是粘性消息，没注册也能接收！"));
    }


    class EventSrc1 {
        @Subscribe
        public void EventBusDef(EventMessage event) {
            cont++;
            tv.setText(event.msg + cont);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void EventBus(EventMessage event) {
            cont++;
            tv.setText(event.msg + cont);
        }
    }

    class EventSrc2 {

        @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
        public void StickyEventBus(EventMessage event) {
            cont++;
            tv.setText(event.msg + cont);
        }
    }
}