package nwodo.ikem.com.alcchallenge20

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.deal_item.view.*
import java.util.*

class TravelAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<TravelAdapter.DealViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.deal_item,parent,false)
        return DealViewHolder(v)
    }

    var travelDeals = ArrayList<TravelDeals>()
    private val firebaseDatabase: FirebaseDatabase
    private val firebaseReference: DatabaseReference

    init {
        travelDeals = FirebaseUtil.travelDeals
        firebaseDatabase = FirebaseUtil.firebaseDatabase
        firebaseReference = FirebaseUtil.databaseReference
        firebaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildRemoved(p0: DataSnapshot) {
                notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(data: DataSnapshot, p1: String?) {
                val travelDeal = data.getValue(TravelDeals::class.java)
                travelDeal?.id = data.key
                travelDeals.add(travelDeal!!)
            }

            override fun onChildAdded(data: DataSnapshot, p1: String?) {
                val travelDeal = data.getValue(TravelDeals::class.java)
                travelDeal?.id = data.key
                travelDeals.add(travelDeal!!)
                notifyItemInserted(travelDeals.size-1)
            }
        })
    }

    override fun getItemCount()=travelDeals.size

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        holder.bind(travelDeals[position])
    }

    inner class DealViewHolder(private val item: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(item), View.OnClickListener{
        override fun onClick(p0: View?) {
            val position = adapterPosition
            Log.d("Click",position.toString())
            val selectedDeal = travelDeals[position]
            val intent = Intent(item.context, DealActivity::class.java)
            intent.putExtra(FirebaseUtil.SELECTED_ITEM,selectedDeal)
            item.context.startActivity(intent)
        }


        val placeImageView = item.imageView
        val tvPlace = item.tvPlace
        val tvPrice = item.tvPrice
        val tvDescription = item.tvDescription


        fun bind(travelDeal:TravelDeals){
            if(!travelDeal.imageUrl.isNullOrEmpty()){
                Glide.with(item.context).load(travelDeal.imageUrl)
                        .centerCrop()
                        .apply(RequestOptions().override(160,160))
                        .into(placeImageView)            }
            tvPlace.text = travelDeal.title
            tvPrice.text = travelDeal.price
            tvDescription.text = travelDeal.description
            item.setOnClickListener(this)
        }
    }

}