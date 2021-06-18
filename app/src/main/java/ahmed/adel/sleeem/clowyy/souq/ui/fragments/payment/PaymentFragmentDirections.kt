package ahmed.adel.sleeem.clowyy.souq.ui.fragments.payment

import ahmed.adel.sleeem.clowyy.souq.R
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class PaymentFragmentDirections private constructor() {
  public companion object {
    public fun actionPaymentFragmentToSuccessFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_paymentFragment_to_successFragment)
  }
}
