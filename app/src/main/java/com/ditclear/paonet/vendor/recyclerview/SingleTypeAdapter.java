/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ditclear.paonet.vendor.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.support.v7.util.DiffUtil;
import android.view.ViewGroup;

import com.ditclear.paonet.model.data.callback.DiffItemCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Super simple single-type adapter using data-binding.
 *
 * @author markzhai on 16/8/22
 */
public class SingleTypeAdapter<T extends DiffItemCallBack> extends BaseViewAdapter<T> {

    protected int mLayoutRes;
    private int dataVersion;

    public interface Presenter<T> extends BaseViewAdapter.Presenter {
        void onItemClick(T t);
    }

    public SingleTypeAdapter(Context context) {
        this(context, 0);
    }

    public SingleTypeAdapter(Context context, int layoutRes) {
        super(context);
        mCollection = new ArrayList<>();
        mLayoutRes = layoutRes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(
                DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(), parent, false));
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    protected boolean areItemsTheSame(T oldItem, T newItem) {
        return oldItem.areItemsTheSame(newItem);
    }

    protected boolean areContentsTheSame(T oldItem, T newItem) {
        return oldItem.areContentsTheSame(newItem);
    }

    public void add(T viewModel) {
        mCollection.add(viewModel);
        notifyItemInserted(mCollection.indexOf(viewModel));
    }



    public void add(int position, T viewModel) {
        mCollection.add(position, viewModel);
        notifyItemInserted(position);
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    public void replace(final List<T> update) {
        dataVersion ++;
        if (mCollection == null) {
            if (update == null) {
                return;
            }
            mCollection = update;
            notifyDataSetChanged();
        } else if (update == null) {
            int oldSize = mCollection.size();
            mCollection = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            final int startVersion = dataVersion;
            final List<T> oldItems = mCollection;
            new AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                @Override
                protected DiffUtil.DiffResult  doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldItems.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return update.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            T oldItem = oldItems.get(oldItemPosition);
                            T newItem = update.get(newItemPosition);
                            return SingleTypeAdapter.this.areItemsTheSame(oldItem, newItem);
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            T oldItem = oldItems.get(oldItemPosition);
                            T newItem = update.get(newItemPosition);
                            return SingleTypeAdapter.this.areContentsTheSame(oldItem, newItem);
                        }
                    });
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if (startVersion != dataVersion) {
                        // ignore update
                        return;
                    }
                    mCollection = update;
                    diffResult.dispatchUpdatesTo(SingleTypeAdapter.this);

                }
            }.execute();
        }
    }

    public void addAll(List<T> viewModels) {
        mCollection.addAll(viewModels);
        notifyDataSetChanged();
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }
}