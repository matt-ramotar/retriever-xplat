package ai.wandering.retriever.android.feature.account_tab

import ai.wandering.retriever.android.common.sig.R
import ai.wandering.retriever.android.common.sig.SigTheme
import ai.wandering.retriever.android.common.sig.color.Sig
import ai.wandering.retriever.android.common.sig.color.systemThemeColors
import ai.wandering.retriever.android.common.sig.component.Avatar
import ai.wandering.retriever.android.common.sig.component.OpacityButton
import ai.wandering.retriever.android.common.sig.component.PrimaryButton
import ai.wandering.retriever.common.storekit.entity.User
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AccountTab(user: User.Output.Populated, campaignViewModel: CampaignViewModel = viewModel(), onNavigateToProfile: () -> Unit) {

    val colors = systemThemeColors()

    val campaign = campaignViewModel.state.collectAsState()

    SigTheme {

        Column {

            if (campaign.value != null) {
                Row {
                    Text(text = campaign.value!!.content?._id ?: "Empty")
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Text(text = "Account", style = MaterialTheme.typography.titleLarge)

                Row {
                    Icon(painter = painterResource(id = R.drawable.settings), contentDescription = null)
                    Icon(painter = painterResource(id = R.drawable.show), contentDescription = null, modifier = Modifier.clickable {
                        onNavigateToProfile()
                    })
                }
            }
            LazyColumn {

                val avatarUrl = user.avatarUrl
                if (avatarUrl != null) {

                    item {
                        Row {

                            Avatar(avatarUrl = avatarUrl)

                            Column {
                                Text(text = user.name)
                                Text(text = user.email)
                            }

                            Icon(painter = painterResource(id = R.drawable.switcher), contentDescription = null)

                        }
                    }

                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(border = BorderStroke(2.dp, color = Sig.ColorScheme.surface))
                            .padding(16.dp)
                    ) {

                        Image(painter = painterResource(id = R.drawable.retriever), contentDescription = null, modifier = Modifier.size(32.dp))

                        Spacer(modifier = Modifier.size(16.dp))

                        Column {
                            Text(text = "Your plan")
                            Text(text = "Retriever Basic")
                            Text(text = "View details and manage your plan")
                        }
                    }
                }

                item {

                    Spacer(modifier = Modifier.size(16.dp))
                }


                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            modifier = Modifier
                                .border(border = BorderStroke(2.dp, color = Sig.ColorScheme.surface))
                                .padding(16.dp)
                        ) {

                            Box {
                                CircularProgressIndicator(progress = 1f, color = colors.gray.background, strokeWidth = 12.dp, modifier = Modifier.size(96.dp))
                                CircularProgressIndicator(progress = 0.5f, color = colors.blue.text, strokeWidth = 12.dp, modifier = Modifier.size(96.dp))
                            }

                            PrimaryButton(onClick = { /*TODO*/ }) {
                                Text(text = "Manage")
                            }
                        }

                        Spacer(modifier = Modifier.size(24.dp))

                        Column(
                            modifier = Modifier
                                .border(border = BorderStroke(2.dp, color = Sig.ColorScheme.surface))
                                .padding(16.dp)
                        ) {
                            Row {
                                Icon(Icons.Filled.Phone, null)
                                Icon(Icons.Filled.Phone, null)
                            }

                            Text(text = "Devices")
                            Text(text = "2/3")

                            OpacityButton(onClick = { /*TODO*/ }) {
                                Text(text = "Manage")
                            }
                        }


                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(border = BorderStroke(2.dp, color = Sig.ColorScheme.surface))
                            .padding(16.dp)
                    ) {
                        Text(text = "Manage your Retriever", style = MaterialTheme.typography.titleLarge)
                        Text(text = "These are the features you have access to with your Retriever Basic account")

                        ListItem()
                        ListItem()
                        ListItem()
                    }
                }

                item {


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(border = BorderStroke(2.dp, color = Sig.ColorScheme.surface))
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tech_research_lab),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )

                        Text(text = "50% off Retriever Plus", style = MaterialTheme.typography.titleLarge)
                        Text(text = "These are the features you have access to with your Retriever Basic account")

                        ListItem()
                        ListItem()
                        ListItem()

                        PrimaryButton(onClick = { /*TODO*/ }) {
                            Text(text = "Upgrade")
                        }
                    }
                }
            }
        }


    }

}

@Composable
fun ListItem() {
    Row {
        Icon(Icons.Filled.Person, null)
        Column {
            Text(text = "Camera Uploads")
            Text(text = "Manage how your photos are backed up")
        }
        Text(text = "Off")
    }
}