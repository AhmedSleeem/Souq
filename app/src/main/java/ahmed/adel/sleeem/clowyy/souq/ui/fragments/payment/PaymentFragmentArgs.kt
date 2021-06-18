package ahmed.adel.sleeem.clowyy.souq.ui.fragments.payment

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class PaymentFragmentArgs(
  public val totalPrice: String = "0"
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("totalPrice", this.totalPrice)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): PaymentFragmentArgs {
      bundle.setClassLoader(PaymentFragmentArgs::class.java.classLoader)
      val __totalPrice : String?
      if (bundle.containsKey("totalPrice")) {
        __totalPrice = bundle.getString("totalPrice")
        if (__totalPrice == null) {
          throw IllegalArgumentException("Argument \"totalPrice\" is marked as non-null but was passed a null value.")
        }
      } else {
        __totalPrice = "0"
      }
      return PaymentFragmentArgs(__totalPrice)
    }
  }
}
