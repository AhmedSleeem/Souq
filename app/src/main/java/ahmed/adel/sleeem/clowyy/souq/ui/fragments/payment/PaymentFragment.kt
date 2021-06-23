package ahmed.adel.sleeem.clowyy.souq.ui.fragments.payment


import ahmed.adel.sleeem.clowyy.souq.databinding.FragmentPaymentBinding
import ahmed.adel.sleeem.clowyy.souq.room.cart.CartViewModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stripe.android.Stripe
import com.stripe.android.getPaymentIntentResult
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference


class PaymentFragment : Fragment() {

    private val backendUrl = "https://souqitigraduationproj.herokuapp.com/api/payment/"
    private val httpClient = OkHttpClient()
    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe

    val args: PaymentFragmentArgs by navArgs()

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false);

        // Configure the SDK with your Stripe publishable key so it can make requests to Stripe
        stripe = Stripe(
            requireContext(),
            "pk_test_51Iy5fBL3JuHukjIeUugTP9MeW5FPA1CvAPXwaNn6jvnTiHIhoFymfvTX66ESqIoGiTcAGZIF5b0inkUlShgLX75600E4Qxn7hX"
        )

        startCheckout()


        return binding.root
    }

    private fun startCheckout() {
        val weakActivity = WeakReference<Activity>(requireActivity())
        // Create a PaymentIntent by calling your server's endpoint.
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val payMap: MutableMap<String, Any> = HashMap()
        val itemMap: MutableMap<String, Any> = HashMap()
        val itemList: MutableList<Map<String, Any>> = ArrayList()
        payMap["currency"] = "usd"


        //dont change currency in testing phase otherwise it won't work


        itemMap["id"] = "photo_subscription"
        var amount :Int = args.totalPrice.toDouble().toInt()
        Log.i( "startCheckout: $amount",amount.toString())
        itemMap["amount"] = amount
        itemList.add(itemMap)
        payMap["items"] = itemList
        val json = Gson().toJson(payMap)

        val body = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(backendUrl + "create-payment-intent")
            .post(body)
            .build()




            httpClient.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        weakActivity.get()?.let { activity ->

//                                displayAlert(activity, "Failed to load page", "Error: $e")

                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (!response.isSuccessful) {
                            weakActivity.get()?.let { activity ->
//                                displayAlert(activity, "Failed to load page", "Error: $response")
                            }
                        } else {
                            val responseData = response.body?.string()
                            val responseJson =
                                responseData?.let { JSONObject(it) } ?: JSONObject()

                            // For added security, our sample app gets the publishable key
                            // from the server.
                            paymentIntentClientSecret = responseJson.getString("clientSecret")
                        }
                    }
                })


        // Hook up the pay button to the card widget and stripe instance
        val payButton: Button = binding.payButton
        payButton.setOnClickListener {
            val cardInputWidget = binding.cardInputWidget

            cardInputWidget.paymentMethodCreateParams?.let { params ->

                val confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret )
                stripe.confirmPayment(this, confirmParams)
            }
        }
    }

       fun  displayAlert(
        activity: Activity,
        title: String,
        message: String,
        restartDemo: Boolean = false
    ) {
        run {
            val builder = AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)

            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val weakActivity = WeakReference<Activity>(requireActivity())

        // Handle the result of stripe.confirmPayment
        if (stripe.isPaymentResult(requestCode, data)) {
            lifecycleScope.launch {
                runCatching {

                    stripe.getPaymentIntentResult(requestCode, data!!).intent
                }.fold(
                    onSuccess = { paymentIntent ->
                        val status = paymentIntent.status
                        if (status == StripeIntent.Status.Succeeded) {
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            weakActivity.get()?.let { activity ->
//                                displayAlert(activity, "Payment succeeded", gson.toJson(paymentIntent))

                                ViewModelProvider(requireActivity()).get(CartViewModel::class.java).deleteAll();

                               val action =  PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment()
                                requireActivity().findNavController(id).navigate(action)
                            }
                        } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                            weakActivity.get()?.let { activity ->
//                                displayAlert( activity, "Payment failed", paymentIntent.lastPaymentError?.message.orEmpty() )
                            }
                        }
                    },
                    onFailure = {
                        weakActivity.get()?.let { activity ->
//                            displayAlert(activity, "Payment failed", it.toString());
                        }
                    }
                )
            }
        }
    }

}












