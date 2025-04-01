package com.example.eazyshop.profile

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.eazyshop.R
import com.example.eazyshop.data.model.User
import com.example.eazyshop.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val user by userViewModel.user.observeAsState()
    val systemUiController = rememberSystemUiController()

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
                .padding(16.dp)
        ) {
            Text("Profile Screen Content")
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
            modifier = Modifier.fillMaxWidth()
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