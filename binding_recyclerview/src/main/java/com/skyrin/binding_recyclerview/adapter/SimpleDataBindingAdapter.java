/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skyrin.binding_recyclerview.adapter;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;

/**
 * @author KunMinX
 * Create at 2018/6/30
 */
public abstract class SimpleDataBindingAdapter<M, B extends ViewDataBinding> extends BaseDataBindingAdapter<M, B> {

    private final int layout;

    public SimpleDataBindingAdapter(Context context, int layout, @NonNull DiffUtil.ItemCallback<M> diffCallback) {
        super(context, diffCallback);
        this.layout = layout;
    }

    @Override
    public int getItemViewType(int position) {
        if (noEmptyView()){
            return super.getItemViewType(position);
        }
        if (getCurrentList().isEmpty()){
            return VIEW_TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (noEmptyView()){
            return super.getItemCount();
        }
        if (getCurrentList().isEmpty()){
            return 1; // return 1 to show empty view
        }
        return super.getItemCount();
    }

    @Override
    protected @LayoutRes
    int getLayoutResId(int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            return getEmptyView();
        }
        return this.layout;
    }

    @Override
    protected @LayoutRes
    int getEmptyView() {
        return 0;
    }

    boolean noEmptyView(){
        return getEmptyView() == 0;
    }
}