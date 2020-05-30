package app.word.game

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.logistics.utils.helper.genericadapter.ListItemViewModel
import app.word.game.model.Sales
import app.word.game.utlis.PrefUtils
import app.word.game.utlis.PrefUtils.setNotAds
import app.word.game.utlis.PrefUtils.setNotAdsAndOffline
import app.word.game.utlis.PrefUtils.setOffline
import app.word.game.utlis.PrefUtils.setUnLimitedHeart
import app.word.game.utlis.PrefUtils.setUnLimitedTime
import app.word.game.utlis.genericadapter.GenericAdapter
import app.word.game.utlis.showSucces
import com.android.billingclient.api.*
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.toolbar.*

class SalesActivity : AppCompatActivity(), PurchasesUpdatedListener {

    val baseAdapter by lazy { GenericAdapter<Sales>(R.layout.sales_item) }

    /***Billing*/
    private lateinit var billingClient: BillingClient
    private val skuList = listOf("bir", "iki", "uc", "dort", "bes")
    private var moneyType: String? = null
    private var isBillingSetupFinished: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        setupBillingClient()

        rv.adapter = baseAdapter
        baseAdapter.addItems(list())

        baseAdapter.setOnListItemViewClickListener(object :
            GenericAdapter.OnListItemViewClickListener {
            override fun onClickDetail(view: View, position: Int, model: ListItemViewModel) {
                super.onClickDetail(view, position, model)
                var model = model as Sales
                moneyType = model.id
                loadAllSKUs()

            }
        })
        ivBack.setOnClickListener {
            finish()
        }
    }

    fun list(): ArrayList<Sales> {
        val sales: ArrayList<Sales> = arrayListOf()
        sales.add(Sales("bir", getString(R.string.offline_oyna), resources.getDrawable(R.drawable.ic_empty)))
        sales.add(Sales("iki", getString(R.string.sinirsiz_sure), resources.getDrawable(R.drawable.ic_empty)))
        sales.add(Sales("uc", getString(R.string.sinirsiz_can), resources.getDrawable(R.drawable.ic_empty)))
        sales.add(Sales("dort", getString(R.string.reklamsiz_oyna), resources.getDrawable(R.drawable.ic_empty)))
        sales.add(
            Sales("bes", getString(R.string.reklamsiz_ve_offline), resources.getDrawable(R.drawable.ic_empty))
        )
        return sales
    }


    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener(this)
            .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    isBillingSetupFinished = true
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.

            }
        })

    }

    private fun loadAllSKUs() = if (billingClient.isReady) {
        val params = SkuDetailsParams
            .newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.INAPP)
            .build()
        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            // Process the result.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList.isNotEmpty()) {
                for (skuDetails in skuDetailsList) {
                    if (skuDetails.sku == moneyType) {
                        val billingFlowParams = BillingFlowParams
                            .newBuilder()
                            .setSkuDetails(skuDetails)
                            .build()
                        billingClient.launchBillingFlow(this, billingFlowParams)
                    }
                }
            }
            //logger(skuDetailsList.get(0).description)

        }

    } else {
        println("Billing Client not ready")
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult?,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                tripUpdatePremium()
                // acknowledgePurchase(purchase.purchaseToken)

            }
        } else if (billingResult?.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            //logger("User Cancelled")
            // logger(billingResult.debugMessage.toString())


        } else {
            //logger(billingResult?.debugMessage.toString())
            // Handle any other error codes.
        }
    }

    fun tripUpdatePremium() {
        PrefUtils.updatePremium(moneyType!!)
        showSucces(getString(R.string.tebrikler_satin_aldiniz))
        when (moneyType) {
            "1" -> {
                setOffline()
            }
            "2" -> {
                setUnLimitedTime()
            }
            "3" -> {
                setUnLimitedHeart()
            }
            "4" -> {
                setNotAds()
            }
            "5" -> {
                setNotAdsAndOffline()
            }
        }
    }

}
