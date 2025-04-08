package com.example.eazyshop.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Attribution
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LineAxis
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.eazyshop.R
import com.example.eazyshop.data.model.User
import com.example.eazyshop.ui.theme.EazyShopTheme
import com.example.eazyshop.viewmodel.UserViewModel


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val user by userViewModel.user.observeAsState()

    LaunchedEffect(Unit) {
        userViewModel.getUser(id = 1)
    }

    Scaffold(
        topBar = {
            if (user != null) {
                TopProfileBar(
                    user = user!!
                )
            } else {
                Text(
                    text = "Loading...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            FestivalCard()

            UtilitiesCard()

            OtherUtilitiesCard()

            SupportCard()
        }
    }
}

@Composable
fun TopProfileBar(
    modifier: Modifier = Modifier,
    user: User
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .background(Color(0xFFFF6600))
            .height(80.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (avatar, userName, phoneNumber, edit) = createRefs()

            val avatarUri = user.avatarUri

            if (avatarUri != null) {
                // Nếu avatarUri là URL, sử dụng AsyncImage
                AsyncImage(
                    model = avatarUri,
                    contentDescription = "avatar",
                    modifier = Modifier
                        .fillMaxWidth(0.15f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .constrainAs(avatar) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    contentScale = ContentScale.Crop,
                )
            } else {
                // Nếu avatarUri là null, sử dụng Image với Painter
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .fillMaxWidth(0.15f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .constrainAs(avatar) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = user.userName,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(userName) {
                    top.linkTo(parent.top)
                    start.linkTo(avatar.end, margin = 8.dp)
                }
            )

            Text(
                text = "Phone number: " + user.phoneNumber,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(phoneNumber) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(avatar.end, margin = 8.dp)
                }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(1.dp, Color.White, shape = RoundedCornerShape(4.dp))
                    .constrainAs(edit) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, margin = 8.dp)
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        "Edit",
                        color = Color.White
                    )

                    Spacer(Modifier.width(2.dp))

                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun FestivalCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        ) {
            Text(
                "Super Sale Festival",
                fontSize = 14.sp
            )
            
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sale),
                        contentDescription = "sale",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        "Home",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                    Text(
                        "Page",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp,
                        color = Color(0xFFFF6600)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.saletime),
                        contentDescription = "sale",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        "Sale Rush",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                    Text(
                        "Hours",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp,
                        color = Color(0xFFFF6600)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.live),
                        contentDescription = "sale",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        "Shopoo Live",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                    Text(
                        "& Video",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp,
                        color = Color(0xFFFF6600)
                    )
                }
            }
        }
    }
}

@Composable
fun UtilitiesCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        val utilityItems = listOf(
            UtilityItemData(Icons.Default.AttachMoney, "EzS Consumer Loans", "Receive up to $1500"),
            UtilityItemData(Icons.Default.Wallet, "ShopooPay Wallet", "Download the app to get $15 Combo"),
            UtilityItemData(Icons.Default.HealthAndSafety, "My Insurance", "Free MINI Accident Package"),
            UtilityItemData(Icons.Default.ConfirmationNumber, "Voucher warehouse", "50+ Voucher"),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 10.dp),
        ) {
            Text(
                "My Utilities",
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp) // Chiều cao cố định để tránh xung đột
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(utilityItems) { item ->
                        UtilitiesItem(
                            icon = item.icon,
                            title = item.title,
                            subTitle = item.subTitle
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UtilitiesItem(
    icon: ImageVector,
    title: String,
    subTitle: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(0.5.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFF6600)
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    lineHeight = 15.sp
                )
                Text(
                    text = subTitle,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = Color(0xFFFF6600)
                )
            }
        }
    }
}

data class UtilityItemData(
    val icon: ImageVector,
    val title: String,
    val subTitle: String
)

@Composable
fun OtherUtilitiesCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 8.dp)
                .background(Color.White)
        ) {

            Text("OtherUtilities", fontSize = 15.sp, modifier = Modifier.padding(horizontal = 8.dp))

            Spacer(Modifier.height(2.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OtherUtilitiesItem(
                        modifier = Modifier
                            .weight(1f)
                            .border(0.2.dp, color = Color.Black, RoundedCornerShape(8.dp)),
                        icon = Icons.Default.Attribution,
                        title = "Loyalty Program",
                        subTitle = "Diamond Member"
                    )
                    Spacer(Modifier.width(8.dp))
                    OtherUtilitiesItem(
                        modifier = Modifier
                            .weight(1f)
                            .border(0.2.dp, color = Color.Black, RoundedCornerShape(8.dp)),
                        icon = Icons.Default.ShoppingBag,
                        title = "Acquisition",
                        subTitle = "Acquisition"
                    )

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OtherUtilitiesItem(
                        modifier = Modifier
                            .weight(1f)
                            .border(0.2.dp, color = Color.Black, RoundedCornerShape(8.dp)),
                        icon = Icons.Default.Tv,
                        title = "Creator Channel",
                        subTitle = "Creator"
                    )
                    Spacer(Modifier.width(8.dp))
                    OtherUtilitiesItem(
                        modifier = Modifier
                            .weight(1f)
                            .border(0.2.dp, color = Color.Black, RoundedCornerShape(8.dp)),
                        icon = Icons.Default.Wallet,
                        title = "Shopoo account balance",
                        subTitle = ""
                    )

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OtherUtilitiesItem(
                        modifier = Modifier
                            .weight(1f)
                            .border(0.2.dp, color = Color.Black, RoundedCornerShape(8.dp)),
                        icon = Icons.Default.CardGiftcard,
                        title = "Bounty hunt",
                        subTitle = "Coin hunt game"
                    )
                    Spacer(Modifier.width(8.dp))
                    OtherUtilitiesItem(
                        modifier = Modifier
                            .weight(1f)
                            .border(0.2.dp, color = Color.Black, RoundedCornerShape(8.dp)),
                        icon = Icons.Default.LineAxis,
                        title = "Shopoo Affiliate Marketing",
                        subTitle = ""
                    )

                }
            }
        }
    }
}

@Composable
fun OtherUtilitiesItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subTitle: String
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFF6600)
        )

        Spacer(Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                lineHeight = 15.sp
            )
            Text(
                text = subTitle,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SupportCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.height(4.dp))
            Text(
                "Support",
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(2.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    Icons.Default.Support,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    "Support Center",
                    fontSize = 15.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .height(1.dp)
                    .border(0.1.dp, color = Color.LightGray)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    Icons.Default.SupportAgent,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    "Contact Shopoo",
                    fontSize = 15.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .height(1.dp)
                    .border(0.1.dp, color = Color.LightGray)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    Icons.Default.Forum,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    "Shopoo Blog",
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun SupportCardPreview() {
    EazyShopTheme {
        SupportCard()
    }
}

@Preview
@Composable
private fun OtherUtilitiesItemPreview() {
    EazyShopTheme {
        OtherUtilitiesCard()
    }
}

@Preview
@Composable
private fun UtilitiesItemPreview() {
    EazyShopTheme {
        UtilitiesItem(
            icon = Icons.Default.AccountBalanceWallet,
            title = "Borrow",
            subTitle = "hahahaah"
        )
    }
}

@Preview
@Composable
private fun FestivalCardPreview() {
    EazyShopTheme {
        FestivalCard()
    }
}

@Preview
@Composable
private fun TopProfileBarPreview() {
    TopProfileBar(
        user = User(
            userId = 1,
            userName = "AnhQuocs",
            avatarUri = null,
            phoneNumber = "0839072300"
        )
    )
}