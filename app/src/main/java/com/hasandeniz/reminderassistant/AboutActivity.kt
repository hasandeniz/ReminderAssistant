package com.hasandeniz.reminderassistant

import android.net.MailTo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.text.HtmlCompat

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val actionBar = supportActionBar
        val html = "<font color=\"@color/customAppNameColor\">" + getString(R.string.about) + "</font>"
        actionBar!!.title = HtmlCompat.fromHtml(html,HtmlCompat.FROM_HTML_MODE_LEGACY)
        val textReference = findViewById<TextView>(R.id.animationRef)
        textReference.movementMethod = LinkMovementMethod.getInstance()


    }


}