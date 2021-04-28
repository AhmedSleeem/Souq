package ahmed.adel.sleeem.clowyy.souq

import ahmed.adel.sleeem.clowyy.souq.adapter.FavouriteAdapter
import ahmed.adel.sleeem.clowyy.souq.model.FavouriteItem
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class FavouriteActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var arrayList: ArrayList<FavouriteItem>? = null
    private var gridView: GridView? = null
    private var languageAdapter: FavouriteAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.my_gridView)
        arrayList = ArrayList()
        arrayList = setDataList()
        languageAdapter = FavouriteAdapter(applicationContext, arrayList!!)
        gridView?.adapter = languageAdapter
        gridView?.onItemClickListener = this
    }

    private fun setDataList(): ArrayList<FavouriteItem> {

        var arrayList: ArrayList<FavouriteItem> = ArrayList()


        arrayList.add(
            FavouriteItem(
                R.drawable.womem_bag,
                "Nike Air Max 270 React ENG",
                "$299,43",
                "$534,33",
                "44% Off"
            )
        )
        arrayList.add(
            FavouriteItem(
                R.drawable.shoes,
                "Nike Air Max 271 React ENG",
                "$111,43",
                "$534,33",
                "24% Off"
            )
        )
        arrayList.add(
            FavouriteItem(
                R.drawable.bag2,
                "Nike Air Max 2722 React ENG",
                "$200,43",
                "$534,33",
                "27% Off"
            )
        )
        arrayList.add(
            FavouriteItem(
                R.drawable.shoes2,
                "Nike Air Max 2230 React ENG",
                "$699,43",
                "$534,33",
                "50% Off"
            )
        )
        arrayList.add(
            FavouriteItem(
                R.drawable.womem_bag,
                "Nike Air Max 260 React ENG",
                "$49,43",
                "$534,33",
                "24% Off"
            )
        )

        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items: FavouriteItem = arrayList!!.get(position)
        Toast.makeText(applicationContext, items.productName, Toast.LENGTH_LONG).show()

    }
}