package com.hasandeniz.reminderassistant

import android.net.MailTo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val actionBar = supportActionBar
        actionBar!!.title = (Html.fromHtml(
            "<font color=\"@color/customAppNameColor\">" + getString(
                R.string.about
            ) + "</font>"
        ))
        val textReference = findViewById<TextView>(R.id.animationRef)
        textReference.movementMethod = LinkMovementMethod.getInstance()


    }


}