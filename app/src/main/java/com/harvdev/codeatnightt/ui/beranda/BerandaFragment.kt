package com.harvdev.codeatnightt.ui.beranda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.facebook.shimmer.ShimmerFrameLayout
import com.harvdev.codeatnightt.R
import com.harvdev.codeatnightt.adapter.BarangAdapter
import com.harvdev.codeatnightt.models.Barang
import com.harvdev.codeatnightt.network.BarangApi
import com.harvdev.codeatnightt.ui.barang.SemuaBarangActivity
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_beranda.view.*
import kotlinx.android.synthetic.main.fragment_slider.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaFragment : Fragment() {
    private lateinit var root: View
    private lateinit var shimmerLayout: ShimmerFrameLayout

    private val mPager: ViewPager? = null
    private val currentPage = 0
    private val img = arrayOf<Int>(
        R.drawable.codetee,
        R.drawable.codetee,
        R.drawable.codetee
    )
    private val ImgArray = ArrayList<Int>()

    private lateinit var berandaViewModel: BerandaViewModel
    private  var mSectionPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        berandaViewModel =
            ViewModelProviders.of(this).get(BerandaViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_beranda, container, false)

        mSectionPagerAdapter = fragmentManager?.let { SectionsPagerAdapter(it) }
        root.containerr.adapter = mSectionPagerAdapter
        root.cvViewAll.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, SemuaBarangActivity::class.java)
            // To pass any data to next activity
            // start your next activity
            startActivity(intent)
        })
        root.cvPencarianHome.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, SemuaBarangActivity::class.java)
            // To pass any data to next activity
            // start your next activity
            startActivity(intent)
        })

        fetchBarang()

//        val savedState = fragmentManager?.saveFragmentInstanceState(this)
//        this.setInitialSavedState(savedState)
        return root
    }

//    override fun onStart() {
//        super.onStart()
//        mSectionPagerAdapter = fragmentManager?.let { SectionsPagerAdapter(it) }
//        containerr.adapter = mSectionPagerAdapter
//        Toast.makeText(activity,"onStart",Toast.LENGTH_SHORT).show()
//    }

    class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){
        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstancess(position+1)
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }

    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_slider, container, false)
            if (requireArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView.title.text = "Code Astronout"
                rootView.imageSliderr.setImageResource(R.drawable.codetee)
                rootView.deskripsi.text = "Bahan Adem Terbuat dari combed 30's. Tersedia Ukuran M,L,XL,XXL."
            }
            if (requireArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView.title.text = "Code AT Night Blue"
                rootView.imageSliderr.setImageResource(R.drawable.codetee)
                rootView.deskripsi.text = "Bahan Adem Terbuat dari combed 30's. Tersedia Ukuran XL,XXL."
            }

            if (requireArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                rootView.title.text = "Code Stars"
                rootView.imageSliderr.setImageResource(R.drawable.codetee)
                rootView.deskripsi.text = "Bahan Adem Terbuat dari combed 30's. Tersedia Ukuran M,L."
            }
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstancess(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }

    }

    private fun fetchBarang(){
        // refreshLayoutHome.isRefreshing = true
        root.shimmerlayout.startShimmerAnimation()
        BarangApi()
            .getNew("T-Shirt").enqueue(object: Callback<List<Barang>> {
                override fun onFailure(call: Call<List<Barang>>, t: Throwable) {
                    // refreshLayoutHome.isRefreshing = false
                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<Barang>>, response: Response<List<Barang>>) {
                    //refreshLayoutHome.isRefreshing = false
                    root.shimmerlayout.visibility = View.GONE
                    val barangs = response.body()
                    barangs?.let {
                        recyclerViewBarangHome?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        recyclerViewBarangHome?.adapter =
                            BarangAdapter(barangs)
                    }
                }

            })
    }

//    private fun showBarang(barangs: List<Barang>){
//
//        recyclerViewBarangHome.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        recyclerViewBarangHome.adapter =
//            BarangAdapter(barangs)
//    }

//    override fun onResume() {
//        super.onResume()
//        root.shimmerlayout.startShimmerAnimation()
//    }
//    override fun onPause() {
//        super.onPause()
//        root.shimmerlayout.stopShimmerAnimation()
//    }
}