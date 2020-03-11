package com.smartlab.componentbase.adapter;



import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> implements Filterable {
    private List<T> mDatas;
    private List<T> mDatasFilter;

    public BaseAdapter(List<T> list) {
        this.mDatas = list;
        this.mDatasFilter = list;
    }

    /**
     * 创建ViewHolder实例
     */

    @NonNull
    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return ViewHolder.get(viewGroup, getLayoutId(i));
    }

    /**
     * 对RecyclerView子项的数据进行赋值
     */
    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.ViewHolder viewHplder, int i) {

        convert(viewHplder, mDatasFilter.get(i), i);
    }

    /**
     * 返回数据源的长度
     */
    @Override
    public int getItemCount() {
        return mDatasFilter.size();
    }

    public void appendList(List<T> list) {
        this.mDatas = list;
        this.mDatasFilter = list;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mDatasFilter = mDatas;
                } else {
                    List<T> filteredList = new ArrayList<>();
                    for (T itenvalue : mDatas) {
                        if (itenvalue.toString().contains(constraint)) {
                            filteredList.add(itenvalue);
                        }
                    }
                    mDatasFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDatasFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDatasFilter = (ArrayList<T>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * 设置无数据时视图
     */
    public abstract int getEmptyLayoutId(int viewType);

    /**
     * 获取Item视图
     */
    public abstract int getLayoutId(int viewType);

    /**
     * item数据绑定
     */
    public abstract void convert(ViewHolder holder, T data, int position);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;

        private ViewHolder(View v) {
            super(v);
            mConvertView = v;
            mViews = new SparseArray<>();
        }

        public static ViewHolder get(ViewGroup parent, int layoutId) {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new ViewHolder(convertView);
        }

        public <T extends View> T getView(int id) {
            View v = mViews.get(id);
            if (v == null) {
                v = mConvertView.findViewById(id);
                mViews.put(id, v);

            }
            return (T) v;
        }

        public void setText(int id, String value) {
            TextView view = getView(id);
            view.setText(value);
        }
    }
}
