package com.yenaly.han1meviewer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.chad.library.adapter4.BaseDifferAdapter
import com.chad.library.adapter4.viewholder.DataBindingHolder
import com.yenaly.han1meviewer.DATE_FORMAT
import com.yenaly.han1meviewer.DATE_TIME_FORMAT
import com.yenaly.han1meviewer.VIDEO_CODE
import com.yenaly.han1meviewer.databinding.ItemWatchHistoryBinding
import com.yenaly.han1meviewer.logic.entity.WatchHistoryEntity
import com.yenaly.han1meviewer.ui.activity.VideoActivity
import com.yenaly.han1meviewer.util.notNull
import com.yenaly.yenaly_libs.utils.TimeUtil
import com.yenaly.yenaly_libs.utils.activity
import com.yenaly.yenaly_libs.utils.startActivity

/**
 * @project Han1meViewer
 * @author Yenaly Liew
 * @time 2023/11/26 026 15:35
 */
class WatchHistoryRvAdapter :
    BaseDifferAdapter<WatchHistoryEntity, WatchHistoryRvAdapter.ViewHolder>(COMPARATOR) {

    init {
        isStateViewEnable = true
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<WatchHistoryEntity>() {
            override fun areItemsTheSame(
                oldItem: WatchHistoryEntity,
                newItem: WatchHistoryEntity,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WatchHistoryEntity,
                newItem: WatchHistoryEntity,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(view: View) : DataBindingHolder<ItemWatchHistoryBinding>(view)

    override fun onBindViewHolder(
        holder: WatchHistoryRvAdapter.ViewHolder,
        position: Int,
        item: WatchHistoryEntity?,
    ) {
        item.notNull()
        holder.binding.ivCover.load(item.coverUrl) {
            crossfade(true)
        }
        holder.binding.tvAddedTime.text = TimeUtil.millis2String(item.watchDate, DATE_TIME_FORMAT)
        holder.binding.tvReleaseDate.text = TimeUtil.millis2String(item.releaseDate, DATE_FORMAT)
        holder.binding.tvTitle.text = item.title
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int,
    ): WatchHistoryRvAdapter.ViewHolder {
        return ViewHolder(
            ItemWatchHistoryBinding.inflate(
                LayoutInflater.from(context), parent, false
            ).root
        ).also { viewHolder ->
            viewHolder.itemView.apply {
                setOnClickListener {
                    val position = viewHolder.bindingAdapterPosition
                    val item = getItem(position).notNull()
                    val videoCode = item.videoCode
                    context.activity?.startActivity<VideoActivity>(VIDEO_CODE to videoCode)
                }
                // setOnLongClickListener 由各自的 Fragment 实现
            }
        }
    }
}