package com.example.hellojnicallback;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.example.hellojnicallback.recyclerview.CommonRecyclerViewAdapter;
import com.example.hellojnicallback.recyclerview.base.CommonRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    public static final float DEFAULT_TOUCH_DRAG_MOVE_RATIO_FWD = 3f;
    public static final float DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK = 1f;
    public static final float DEFAULT_DECELERATE_FACTOR = -2f;
     RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


         recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        List<String> list= new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add("i="+i);
        }
        CommonRecyclerViewAdapter<String> adapter=new CommonRecyclerViewAdapter<String>(this,android.R.layout.simple_list_item_1,list) {
            @Override
            protected void convert(CommonRecyclerViewHolder holder, String s, int position) {
                holder.setText(android.R.id.text1,s);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setOnTouchListener(new MyOnTouchListener());
    }
    class MyOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ((isInAbsoluteStart() && mMoveAttr.mDir) ||
                    (isInAbsoluteEnd() && !mMoveAttr.mDir)) {
                // Save initial over-scroll attributes for future reference.
                mStartAttr.mPointerId = event.getPointerId(0);
                mStartAttr.mAbsOffset = mMoveAttr.mAbsOffset;
                mStartAttr.mDir = mMoveAttr.mDir;

//                issueStateTransition(mOverScrollingState);
                return handleMoveTouchEvent(event);
            }
//        }
            return false;
        }
    };
    private boolean handleMoveTouchEvent(MotionEvent event){
        if (mStartAttr.mPointerId != event.getPointerId(0)) {
//            issueStateTransition(mBounceBackState);
            return true;
        }

        final View view = recyclerView;
        if (!mMoveAttr.init(view, event)) {
            // Keep intercepting the touch event as long as we're still over-scrolling...
            return true;
        }

        float deltaOffset = mMoveAttr.mDeltaOffset / (mMoveAttr.mDir == mStartAttr.mDir ? DEFAULT_TOUCH_DRAG_MOVE_RATIO_FWD : DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK);
        float newOffset = mMoveAttr.mAbsOffset + deltaOffset;

        // If moved in counter direction onto a potential under-scroll state -- don't. Instead, abort
        // over-scrolling abruptly, thus returning control to which-ever touch handlers there
        // are waiting (e.g. regular scroller handlers).
        if ( (mStartAttr.mDir && !mMoveAttr.mDir && (newOffset <= mStartAttr.mAbsOffset)) ||
                (!mStartAttr.mDir && mMoveAttr.mDir && (newOffset >= mStartAttr.mAbsOffset)) ) {
            translateViewAndEvent(view, mStartAttr.mAbsOffset, event);
//            mUpdateListener.onOverScrollUpdate(OverScrollBounceEffectDecoratorBase.this, mCurrDragState, 0);

//            issueStateTransition(mIdleState);
            return true;
        }

        if (view.getParent() != null) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }

        long dt = event.getEventTime() - event.getHistoricalEventTime(0);
        if (dt > 0) { // Sometimes (though rarely) dt==0 cause originally timing is in nanos, but is presented in millis.
//            mVelocity = deltaOffset / dt;
        }

        translateView(view, newOffset);
//        mUpdateListener.onOverScrollUpdate(OverScrollBounceEffectDecoratorBase.this, mCurrDragState, newOffset);

        return true;
    }
    protected void translateView(View view, float offset) {
        view.setTranslationY(offset);
    }
    protected void translateViewAndEvent(View view, float offset, MotionEvent event) {
        view.setTranslationY(offset);
        event.offsetLocation(offset - event.getY(0), 0f);
    }
    OverScrollStartAttributes mStartAttr = new OverScrollStartAttributes();
    MotionAttributes mMoveAttr=new MotionAttributesVertical();

    public boolean isInAbsoluteStart() {
        return !recyclerView.canScrollVertically(-1);
    }

    public boolean isInAbsoluteEnd() {
        return !recyclerView.canScrollVertically(1);
    }

    protected static class OverScrollStartAttributes {
        protected int mPointerId;
        protected float mAbsOffset;
        protected boolean mDir; // True = 'forward', false = 'backwards'.
    }
    protected abstract static class MotionAttributes {
        public float mAbsOffset;
        public float mDeltaOffset;
        public boolean mDir; // True = 'forward', false = 'backwards'.

        protected abstract boolean init(View view, MotionEvent event);
    }
    protected static class MotionAttributesVertical extends MotionAttributes {

        public boolean init(View view, MotionEvent event) {

            // We must have history available to calc the dx. Normally it's there - if it isn't temporarily,
            // we declare the event 'invalid' and expect it in consequent events.
            if (event.getHistorySize() == 0) {
                return false;
            }

            // Allow for counter-orientation-direction operations (e.g. item swiping) to run fluently.
            final float dy = event.getY(0) - event.getHistoricalY(0, 0);
            final float dx = event.getX(0) - event.getHistoricalX(0, 0);
            if (Math.abs(dx) > Math.abs(dy)) {
                return false;
            }

            mAbsOffset = view.getTranslationY();
            mDeltaOffset = dy;
            mDir = mDeltaOffset > 0;

            return true;
        }
    }
}
