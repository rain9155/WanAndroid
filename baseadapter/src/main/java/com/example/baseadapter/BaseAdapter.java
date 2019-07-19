package com.example.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter基类
 * Created by 陈健宇 at 2019/5/30
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{


    private static final int TYPE_NORMAL = 0x000;
    private static final int TYPE_HANDER_VIEW = 0x001;

    private MutiItemDelegateManager<T> adapterDelegateManager;
    private LinearLayout mHeaderView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongListener mOnItemChildLongListener;

    protected List<T> mDatas;
    protected int mLayoutId;

    public BaseAdapter(List<T> datas) {
        this(datas, -1);
    }

    public BaseAdapter(List<T> datas, int layoutId) {
        mDatas = datas;
        mLayoutId = layoutId;
        adapterDelegateManager = new MutiItemDelegateManager<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        if(viewType == TYPE_HANDER_VIEW){
            holder = new BaseViewHolder(mHeaderView);
        }else if(viewType == TYPE_NORMAL){
            holder = new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false));
        }else {
            holder = adapterDelegateManager.onCreateViewHolder(parent, viewType);
        }
        bindItemClickLisitener(holder);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if(viewType == TYPE_NORMAL){
            onBindView(holder, mDatas.get(position));
        }else {
            adapterDelegateManager.onBindViewHolder(holder, mDatas.get(position), position);
        }
    }

    protected void onBindView(BaseViewHolder holder, T item){}

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HANDER_VIEW;
        }else if(adapterDelegateManager.getItemDelegateCount() == 0){
            return TYPE_NORMAL;
        }else {
            int dataPosition = position - getHeaderViewCount();
            return adapterDelegateManager.getItemViewType(mDatas.get(position), dataPosition);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? getHeaderViewCount() : mDatas.size() + getHeaderViewCount();
    }

    private void bindItemClickLisitener(final BaseViewHolder holder) {
        final View itemView = holder.getItemView();
        if(itemView == null) return;
        holder.setAdapter(this);
        if(mOnItemClickListener != null){
            itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(this, itemView, holder.getLayoutPosition()));
        }
        if(mOnItemLongClickListener != null){
            itemView.setOnLongClickListener(v -> mOnItemLongClickListener.onItemLongClick(this, itemView, holder.getLayoutPosition()));
        }
    }

    public void addHeaderView(View view){
        if(mHeaderView == null){
            mHeaderView = new LinearLayout(view.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mHeaderView.setLayoutParams(params);
            mHeaderView.setOrientation(LinearLayout.VERTICAL);
        }
        int index = mHeaderView.getChildCount();
        mHeaderView.addView(view, index);
        notifyItemInserted(0);
    }

    public int getHeaderViewCount(){
        if(mHeaderView == null || mHeaderView.getChildCount() == 0) return 0;
        return 1;
    }

    /**
     * 添加item的AdapterDelegte
     * @param delegate item的AdapterDelegte
     */
    public BaseAdapter<T> addItemAdapterDelegte(MutiItemDelegate<T> delegate){
        adapterDelegateManager.addDelegte(delegate);
        return this;
    }

    /**
     * 设置item的单击监听
     * @param onItemClickListener 单击监听接口实例
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置item的长按监听
     * @param onItemLongClickListener 长按监听接口实例
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 获得item子控件的单击监听接口实例
     */
    public OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    /**
     * 设置item的子控件单击监听
     * @param onItemChildClickListener 单击监听接口实例
     */
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 获得item子控件的长按监听接口实例
     */
    public OnItemChildLongListener getOnItemChildLongListener() {
        return mOnItemChildLongListener;
    }

    /**
     * 设置item的子控件长按监听
     * @param onItemChildLongListener 长按监听接口实例
     */
    public void setOnItemChildLongListener(OnItemChildLongListener onItemChildLongListener) {
        mOnItemChildLongListener = onItemChildLongListener;
    }

    /**
     * item单击事件监听接口
     */
    public interface OnItemClickListener{
        /**
         * item单击事件监听接口回调方法
         * @param adapter 适配器
         * @param view position对应的itemView
         * @param position itemView在源数据中的索引
         */
        void onItemClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * item长按事件监听接口
     */
    public interface OnItemLongClickListener{
        /**
         * item长按事件监听接口回调方法
         * @param adapter 适配器
         * @param view position对应的itemView
         * @param position itemView在源数据中的索引
         * @return true表示itemView消费这个长按事件
         */
        boolean onItemLongClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * item的子控件的单击接口
     */
    public interface OnItemChildClickListener{
        /**
         *  item的子控件的单击接口回调方法
         * @param adapter 适配器
         * @param view position对应的item的子控件
         * @param position itemView在源数据中的索引
         */
        void onItemChildClickListener(BaseAdapter adapter, View view, int position);
    }

    /**
     * item的子控件的长按接口
     */
    public interface OnItemChildLongListener{
        /**
         *  item的子控件的长按接口回调方法
         * @param adapter 适配器
         * @param view position对应的item的子控件
         * @param position itemView在源数据中的索引
         * @return true表示item的子控件消费这个长按事件
         */
        boolean onItemChildLongListener(BaseAdapter adapter, View view, int position);
    }

}
