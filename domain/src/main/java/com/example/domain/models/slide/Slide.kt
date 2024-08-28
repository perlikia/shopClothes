package com.example.domain.models.slide

class Slide(val id : String) {
    var image : SlideImage? = null
    var header : String? = null
    var description : String? = null

    var action : (() -> Unit)? = null
}