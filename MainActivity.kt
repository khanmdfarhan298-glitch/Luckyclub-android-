package com.example.luckyclub

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val bg = Color.rgb(34, 34, 34)
    private val panel = Color.rgb(49, 49, 47)
    private val gold = Color.rgb(218, 178, 77)
    private val paleGold = Color.rgb(249, 224, 143)
    private val cream = Color.rgb(246, 240, 222)
    private val muted = Color.rgb(177, 171, 155)
    private lateinit var content: LinearLayout
    private var current = "Home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Lucky Club"
        window.statusBarColor = bg
        window.navigationBarColor = bg
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
        }
        content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val scroll = ScrollView(this).apply {
            isFillViewport = true
            addView(content)
        }
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setBackgroundColor(panel)
            setPadding(3, 8, 3, 8)
        }
        listOf("Home", "Activity", "Rewards", "Promotion", "Account").forEach { label ->
            val item = TextView(this).apply {
                text = when (label) {
                    "Home" -> "⌂\nHome"
                    "Activity" -> "▣\nActivity"
                    "Rewards" -> "🎁\nRewards"
                    "Promotion" -> "＄\nPromotion"
                    else -> "☻\nAccount"
                }
                gravity = Gravity.CENTER
                textSize = 12f
                setTextColor(if (label == current) paleGold else muted)
                setPadding(3, 4, 3, 4)
                setOnClickListener { showPage(label) }
            }
            nav.addView(item, LinearLayout.LayoutParams(0, -2, 1f))
        }
        root.addView(nav)
        setContentView(root)
        showPage("Home")
    }

    private fun text(value: String, size: Float = 16f, color: Int = cream, bold: Boolean = false): TextView =
        TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
            setPadding(0, 3, 0, 3)
        }

    private fun box(color: Int, radius: Float = 16f): GradientDrawable =
        GradientDrawable().apply { setColor(color); cornerRadius = radius }

    private fun addCard(title: String, subtitle: String? = null): LinearLayout {
        val c = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 14, 16, 14)
            background = box(panel)
        }
        c.addView(text(title, 16f, cream, true))
        if (subtitle != null) c.addView(text(subtitle, 13f, muted))
        val lp = LinearLayout.LayoutParams(-1, -2)
        lp.setMargins(14, 7, 14, 7)
        content.addView(c, lp)
        return c
    }

    private fun button(label: String, action: () -> Unit): Button =
        Button(this).apply {
            text = label
            isAllCaps = false
            setTextColor(Color.rgb(82, 57, 13))
            background = box(gold, 48f)
            setOnClickListener { action() }
        }

    private fun goldHeader(subtitle: String) {
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 22, 20, 24)
            background = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(Color.rgb(248, 228, 159), Color.rgb(195, 145, 48), Color.rgb(237, 208, 117))
            ).apply { cornerRadii = floatArrayOf(0f,0f,0f,0f,34f,34f,34f,34f) }
        }
        header.addView(text("✦ LUCKY CLUB", 23f, Color.WHITE, true))
        header.addView(text(subtitle, 14f, Color.WHITE))
        content.addView(header)
    }

    private fun showPage(page: String) {
        current = page
        content.removeAllViews()
        when (page) {
            "Home" -> homePage()
            "Activity" -> simplePage("Activity", "Activity center", "Daily check-in · Rewards calendar · Sample history")
            "Rewards" -> rewardsPage()
            "Promotion" -> promotionPage()
            "Account" -> accountPage()
        }
    }

    private fun homePage() {
        goldHeader("MD Farid     ♛ VIP1\nUID 186742903  ·  Demo account")
        val balance = addCard("Demo points     620 ✦", "Sample balance · no cash value")
        balance.addView(button("Enter wallet / Deposit", ::depositPage))
        val grid = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        listOf("Wallet", "Rewards", "History", "VIP").forEach { label ->
            val item = TextView(this).apply {
                text = when(label) { "Wallet" -> "▰\nWallet"; "Rewards" -> "🎁\nRewards"; "History" -> "▤\nHistory"; else -> "♛\nVIP" }
                textSize = 13f; gravity = Gravity.CENTER; setTextColor(paleGold); setPadding(4, 16, 4, 16)
                setOnClickListener { if (label == "Wallet") depositPage() else showPage(if (label == "Rewards") "Rewards" else "Activity") }
            }
            grid.addView(item, LinearLayout.LayoutParams(0, -2, 1f))
        }
        content.addView(grid)
        listOf(
            "Game History" to "Sample game history",
            "Transactions" to "Sample transaction history",
            "Deposit" to "Open your payment QR",
            "Withdraw" to "Demo only"
        ).forEach { (title, sub) ->
            val c = addCard(title, sub)
            c.setOnClickListener { if (title == "Deposit") depositPage() else toast(if (title == "Withdraw") "Withdrawals are not enabled in this demo" else sub) }
        }
        val menu = addCard("Account menu")
        listOf("Notification", "Gifts", "My Top-Up Coupons", "Game statistics", "Language").forEach {
            menu.addView(text("✦  $it     ›", 15f, cream).apply { setPadding(0, 10, 0, 10) })
        }
        val service = addCard("Service center", "Settings · Feedback · Announcement\nCustomer service · Beginner's guide · About us")
        service.addView(button("Log out (demo)", { toast("Demo only") }))
        content.addView(text("Independent UI demo · Not affiliated with BDG Win", 11f, muted).apply {
            gravity = Gravity.CENTER; setPadding(12, 16, 12, 24)
        })
    }

    private fun depositPage() {
        content.removeAllViews()
        val top = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; setPadding(20, 18, 20, 18); setBackgroundColor(panel)
        }
        top.addView(text("‹  Deposit", 22f, paleGold, true).apply { setOnClickListener { showPage("Home") } })
        content.addView(top)
        val c = addCard("Pay using MD Farid's QR code", "Open your UPI app and scan this QR to pay.")
        val qr = ImageView(this).apply {
            setImageResource(R.drawable.payment_qr)
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.FIT_CENTER
            setBackgroundColor(Color.WHITE)
            setPadding(8, 8, 8, 8)
            contentDescription = "MD Farid payment QR code"
        }
        val lp = LinearLayout.LayoutParams(-1, 300)
        lp.setMargins(0, 12, 0, 12)
        c.addView(qr, lp)
        c.addView(text("Account name: MD Farid", 16f, paleGold, true))
        c.addView(text("Before paying, check the recipient name shown in your UPI app. This app does not automatically verify incoming payments.", 13f, muted))
        c.addView(button("I have paid (not verified)", { toast("Payment is not verified by this demo") }))
        c.addView(button("Back to home", { showPage("Home") }))
    }

    private fun promotionPage() {
        goldHeader("Promotion · Demo rewards")
        val c = addCard("Invitation link", "Demo code: LUCKY-DEMO-620")
        c.addView(button("Copy demo code", { toast("Demo code: LUCKY-DEMO-620") }))
        addCard("Promotion overview", "08 demo invites · 24 team demo invites\n50 sample points · 0 verified transactions")
        addCard("Subordinate data", "Sample data only")
        addCard("Reward details", "No cash value")
        addCard("Invitation rules", "Preview")
    }

    private fun rewardsPage() {
        val c = addCard("Demo reward wheel", "Sample points: 620 · No cash value")
        val wheel = TextView(this).apply {
            text = "✦\nDEMO\nSPIN\n✦"
            textSize = 27f; gravity = Gravity.CENTER; setTextColor(Color.rgb(115, 51, 20))
            background = GradientDrawable(GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(255, 236, 157), Color.rgb(220, 159, 39))).apply {
                shape = GradientDrawable.OVAL
                setStroke(10, Color.rgb(178, 61, 35))
            }
        }
        val lp = LinearLayout.LayoutParams(260, 260); lp.gravity = Gravity.CENTER; lp.setMargins(0, 20, 0, 20)
        c.addView(wheel, lp)
        c.addView(button("Try demo spin", { toast("Demo only — no prize or cash awarded") }))
        c.addView(text("This is a visual demo. No real prizes are issued.", 12f, muted))
    }

    private fun accountPage() {
        goldHeader("MD Farid     ♛ VIP1\nUID 186742903")
        val c = addCard("Account tools")
        listOf("Wallet / Deposit", "Game history", "Transactions", "Settings", "Customer service").forEach { label ->
            c.addView(text("✦  $label    ›", 15f, cream).apply {
                setPadding(0, 12, 0, 12)
                setOnClickListener { if (label.contains("Deposit")) depositPage() else toast("$label · demo") }
            })
        }
    }

    private fun simplePage(title: String, heading: String, detail: String) {
        val head = LinearLayout(this).apply { setPadding(20, 20, 20, 20); setBackgroundColor(panel) }
        head.addView(text(title, 22f, paleGold, true))
        content.addView(head)
        addCard(heading, detail)
        addCard("Demo mode", "This screen uses sample content only.")
    }

    private fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
