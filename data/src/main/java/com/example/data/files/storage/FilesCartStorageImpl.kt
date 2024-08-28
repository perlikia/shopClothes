package com.example.data.files.storage

import android.content.Context
import com.example.data.files.storage.models.FileCart
import com.example.data.files.storage.models.FileCartItem
import com.example.data.files.storage.models.FileSize
import com.example.data.storage.CartStorage
import com.example.domain.models.cart.Cart
import com.example.domain.models.cart.CartItem
import com.example.domain.models.product.CartProduct
import com.example.domain.models.product.FullProduct
import com.example.domain.models.product.Size
import com.example.domain.models.user.UserToken
import com.example.files.FileHandler
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

class FilesCartStorageImpl(private val context: Context, private val userManager: FilesUserStorageImpl) : CartStorage {
    val cartFilename = "cart.json"

    fun getFileCarts() : List<FileCart>{
        checkFileExists()
        val data = FileHandler.getContentFromFile(context, cartFilename, FileHandler.Type.USER_DATA)
        return Json{
            ignoreUnknownKeys = true
        }.decodeFromString<List<FileCart>>(data)
    }

    fun writeListInFile(data: List<FileCart>){
        FileHandler.writeContentInFile(
            context,
            cartFilename,
            FileHandler.Type.USER_DATA,
            Json{
                ignoreUnknownKeys = true
            }.encodeToString(data)
        )
    }

    fun insertCartInList(data: FileCart){
        val list = ArrayList(getFileCarts())
        val searchItem = list.filter { item ->
            item.id == data.id
        }.firstOrNull()

        if(searchItem != null){
            list.remove(searchItem)
        }

        list.add(data)
        writeListInFile(list)
    }


    //Methods
    override fun getCartByUser(userToken: UserToken): Cart? {
        val user = userManager.getUserByToken(userToken)
        return getFileCarts().filter { cart ->
            return@filter cart.userID == user?.id
        }.map(this::normalizeCart).firstOrNull()
    }

    override fun addItemInCart(userToken: UserToken, cartItem: CartItem) {
        val user = userManager.getUserByToken(userToken)

        var cart = getCartByUser(userToken)
        if(cart == null) {
            cart = Cart(
                user!!.id,
                generateId(),
                listOf()
            )
        }

        val list = ArrayList(cart.items)
        val searchItem = list.filter { item ->
            item.product.id == cartItem.product.id
                &&
            item.size?.size == cartItem.size?.size
        }.firstOrNull()

        if(searchItem != null){
            list.remove(searchItem)
        }
        list.add(
            CartItem(
                searchItem?.id ?: generateId(),
                cartItem.product,
                cartItem.size,
                cartItem.count
            )
        )

        cart.items = list
        insertCartInList(decompileCart(user!!.id, cart))
    }
    //

    fun generateId(): String{
        return UUID.randomUUID().toString()
    }

    fun checkFileExists(){
        if(FileHandler.fileExists(context, cartFilename, FileHandler.Type.USER_DATA).not()){
            FileHandler.writeContentInFile(
                context,
                cartFilename,
                FileHandler.Type.USER_DATA,
                Json{
                }.encodeToString(
                    listOf(
                        FileCart(
                            "-1",
                            "-1",
                            listOf()
                        )
                    )
                )
            )
        }
    }


    fun decompileCartSize(size: Size): FileSize{
        return FileSize(
            size.size
        )
    }

    fun decompileCartItem(item: CartItem) : FileCartItem{
        return FileCartItem(
            item.id ?: generateId(),
            item.product.id,
            item.size?.let { decompileCartSize(it) },
            item.count
        )
    }

    fun decompileCart(userID: String, cart: Cart): FileCart{
        return FileCart(
            userID,
            cart.id ?: generateId(),
            cart.items.map(this::decompileCartItem)
        )
    }

    fun normalizeCartSize(size: FileSize) : Size{
        return Size(
            size.size,
        )
    }

    fun normalizeCartItem(item: FileCartItem) : CartItem{
        val productsManager = FilesProductStorageImpl(context)
        return CartItem(
            item.id,
            productsManager.getProducts(FullProduct::class.java){ product ->
                product.id == item.productID
            }.first(),
            item.size?.let { normalizeCartSize(it) },
            item.count
        )
    }

    fun normalizeCart(cart: FileCart) : Cart{
        return Cart(
            cart.userID,
            cart.id,
            cart.list.map(this::normalizeCartItem)
        )
    }

}