package com.hasandeniz.reminderassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.hasandeniz.reminderassistant.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val actionBar = supportActionBar
        val html = "<font color=\"@color/customAppNameColor\">" + getString(R.string.about) + "</font>"
        actionBar!!.title = HtmlCompat.fromHtml(html,HtmlCompat.FROM_HTML_MODE_LEGACY)
        val textReference = findViewById<TextView>(R.id.animationRef)
        textReference.movementMethod = LinkMovementMethod.getInstance()


    }


}