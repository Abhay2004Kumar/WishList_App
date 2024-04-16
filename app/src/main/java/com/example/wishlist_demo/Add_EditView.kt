package com.example.wishlist_demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerDefaults.backgroundColor
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun AddEditView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){
    //a simple bottom message
    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()          //used for asynchronous methods

    val scaffoldState = rememberScaffoldState()

    if(id!=0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.WishTitleState = wish.value.title
        viewModel.WishDescState = wish.value.description
    }else{
        viewModel.WishTitleState=""
        viewModel.WishDescState=""
    }

Scaffold(
    scaffoldState= scaffoldState,
    topBar = { AppBarView(title =
    if(id!=0L) "Update Wish" else "Add Wish")

    {navController.navigateUp()}
    },

) {
Column(modifier= Modifier
    .padding(it)
    .wrapContentSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
    ){
    Spacer(modifier = Modifier.height(10.dp))

    WishTextField(label = "Title", value = viewModel.WishTitleState,
        onValueChanged = {
            viewModel.onWishtitleChnged(it)
        })

    Spacer(modifier = Modifier.height(10.dp))

    WishTextField(label = "Description", value = viewModel.WishDescState,
        onValueChanged = {
            viewModel.onWishDescChanged(it)
        })
    Spacer(modifier = Modifier.height(10.dp))

    val btColor = Color(0xFFDD1E5F)
    Button(onClick = {
        if(viewModel.WishTitleState.isNotEmpty() && viewModel.WishDescState.isNotEmpty()){
            if(id!=0L){
                //TODO update wish
                viewModel.updateWish(
                    Wish(
                        id=id,
                        title = viewModel.WishTitleState.trim(),
                        description = viewModel.WishDescState.trim()

                    )
                )
            }else{
                //TODO Add Wish
                viewModel.addWish(
                    Wish(
                        title = viewModel.WishTitleState.trim(),
                        description = viewModel.WishDescState.trim()
                    )
                )
                snackMessage.value = "Wish has been created successfully"
            }

        }else{
            //enter fields for wish to be created
            snackMessage.value= "Enter fields to create your wish"
        }
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
            navController.navigateUp()
        }
    },
        colors = ButtonDefaults.buttonColors(backgroundColor = btColor)

    ){

        Text(
            text = if(id!=0L) "Update" else "Add Wish"
        ,
            color = Color.White,
        style= TextStyle(fontSize = 18.sp))



    }

}
}

}

@Composable
fun WishTextField(
   label:String,
   value:String,
   onValueChanged: (String) -> Unit
){
    OutlinedTextField(value = value, onValueChange = onValueChanged,
        label={ Text(text = label, color = Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors= TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        )



    )


}

@Preview
@Composable
fun WishTextFieldPrev(){
    WishTextField(label = "text", value = "text", onValueChanged = {})
        
    }
