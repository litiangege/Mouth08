package bwie.com.mouth08;

import android.view.View;

/**
 * Created by Administrator on 2017/8/22.
 */

public class RecyclerViewListener {
    public interface onClickListener{
        void onClickListener(View v,int position);
    }
    public interface onClickLongListener {
        void onClickLongListener(View v, int position);
    }
}
