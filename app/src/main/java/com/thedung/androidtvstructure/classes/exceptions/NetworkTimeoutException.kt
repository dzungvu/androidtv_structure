package com.thedung.androidtvstructure.classes.exceptions

import java.net.SocketTimeoutException

/**
 * Use for
 * Created by DzungVu on 5/28/2021.
 */
class NetworkTimeoutException(msg: String? = null) : SocketTimeoutException(msg) {
    var requestBody: String? = null
    var path: String? = null
}