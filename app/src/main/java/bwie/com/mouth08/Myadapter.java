package bwie.com.mouth08;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/22.
 */

class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    List<Data.DataBean> list;
    Context context;
    View view;
    RecyclerViewListener.onClickListener listener;
    RecyclerViewListener.onClickLongListener longListener;
    private ViewHolder holder;

    public Myadapter(Context context,List<Data.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    public void onClickListener(RecyclerViewListener.onClickListener listener) {
        this.listener = listener;
    }

    public void onClickLongListener(RecyclerViewListener.onClickLongListener longListener) {
        this.longListener = longListener;
    }

    @Override
    public Myadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Myadapter.ViewHolder holder, int position) {
        Data.DataBean bean = list.get(position);
        Glide.with(context).load(bean.getImg()).into(holder.img);

        holder.age.setText(bean.getUserAge()+"岁");
        holder.name.setText(bean.getUserName());
        holder.des.setText(bean.getIntroduction());
        holder.work.setText(bean.getOccupation());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClickListener(view, (Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (longListener != null){
            longListener.onClickLongListener(view, (Integer) view.getTag());
        }
        return true;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.work)
        TextView work;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.des)
        TextView des;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            rotateyAnimRun(itemView);
        }
    }
    public void rotateyAnimRun(View view)
    {
        ObjectAnimator//
                .ofFloat(view, "alpha", 0.0F, 1.0F)//
                .setDuration(2000)//
                .start();
    }
}
