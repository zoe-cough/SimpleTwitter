package com.codepath.apps.restclienttemplate

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TweetsAdapter(val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.item_tweet, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        val tweet: Tweet = tweets.get(position)

        holder.tvUserName.text = tweet.user?.name
        holder.tvTweetBody.text = tweet.body


        holder.tvRelativeTime.text = getRelativeTimeAgo(tweet.createdAt)

        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage)
    }

    fun getRelativeTimeAgo(rawJsonDate: String?): String? {
        val twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
        val sf = SimpleDateFormat(twitterFormat, Locale.ENGLISH)
        sf.setLenient(true)
        var relativeDate = ""
        try {
            val dateMillis: Long = sf.parse(rawJsonDate).getTime()
            relativeDate = DateUtils.getRelativeTimeSpanString(
                dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS
            ).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return relativeDate
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvRelativeTime = itemView.findViewById<TextView>(R.id.tvRelativeTime)

    }
}