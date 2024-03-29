@file:OptIn(ExperimentalLayoutApi::class)

package com.dede.android_eggs.views.main.compose

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.dede.android_eggs.BuildConfig
import com.dede.android_eggs.R
import com.dede.android_eggs.util.CustomTabsBrowser
import com.dede.android_eggs.util.createChooser


@Composable
private fun RowScope.ChipItem(
    @StringRes textRes: Int,
    separator: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.align(Alignment.Bottom)
    ) {
        Text(
            text = stringResource(textRes),
            style = typography.titleSmall,
            color = colorScheme.secondary,
            modifier = Modifier
                .clip(shapes.extraSmall)
                .clickable(onClick = onClick)
        )
        if (separator) {
            Text(
                text = stringResource(id = R.string.char_separator),
                style = typography.titleSmall,
                color = colorScheme.secondary,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
}

@Composable
private fun RowScope.ChipItem2(
    @StringRes textRes: Int,
    onClick: () -> Unit,
) {
    Text(
        text = stringResource(textRes),
        style = typography.titleSmall,
        color = colorScheme.secondary,
        modifier = Modifier
            .clip(shapes.extraSmall)
            .clickable(onClick = onClick)
            .align(Alignment.Bottom)
    )
}

@Preview(showBackground = true)
@Composable
fun ProjectDescription() {
    val context = LocalContext.current
    var konfettiState by LocalKonfettiState.current

    fun openCustomTab(@StringRes uri: Int) {
        CustomTabsBrowser.launchUrl(context, context.getString(uri).toUri())
    }

    fun openBrowser(uri: String) {
        CustomTabsBrowser.launchUrlByBrowser(context, uri.toUri())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .then(Modifier.padding(bottom = 20.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Image(
                res = R.mipmap.ic_launcher_round,
                modifier = Modifier
                    .size(50.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                    ) {
                        konfettiState = true
                    },
                contentDescription = stringResource(id = R.string.app_name)
            )
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(
                    text = stringResource(
                        R.string.label_version,
                        BuildConfig.VERSION_NAME,
                        BuildConfig.VERSION_CODE
                    ),
                    style = typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = BuildConfig.GIT_HASH,
                    style = typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clip(shapes.extraSmall)
                        .clickable {
                            val commitId =
                                context.getString(R.string.url_github_commit, BuildConfig.GIT_HASH)
                            CustomTabsBrowser.launchUrl(context, commitId.toUri())
                        }
                )
            }
        }
        Text(
            text = stringResource(R.string.label_project_desc),
            modifier = Modifier.padding(top = 20.dp),
            style = typography.bodyMedium
        )
        FlowRow(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ChipItem(R.string.label_github) {
                openCustomTab(R.string.url_github)
            }
            ChipItem(R.string.label_translation) {
                openCustomTab(R.string.url_translation)
            }
            ChipItem(R.string.label_star) {
                val uri = context.getString(R.string.url_market_detail, context.packageName)
                openBrowser(uri)
            }
            ChipItem(R.string.label_donate) {
                openCustomTab(R.string.url_sponsor)
            }
            ChipItem(R.string.label_share) {
                val target = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, context.getString(R.string.url_share))
                    .setType("text/plain")
                val intent = context.createChooser(target)
                context.startActivity(intent)
            }
            ChipItem(R.string.label_beta, false) {
                openCustomTab(R.string.url_beta)
            }
        }
        Wavy(res = R.drawable.ic_wavy_line_1, true, colorScheme.secondaryContainer)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = typography.titleMedium,
            )
            ChipItem2(R.string.label_privacy_policy) {
                openCustomTab(R.string.url_privacy)
            }
            ChipItem2(R.string.label_license) {
                openCustomTab(R.string.url_license)
            }
            ChipItem2(R.string.label_email) {
                openCustomTab(R.string.url_github_issues)
            }
        }
    }
}