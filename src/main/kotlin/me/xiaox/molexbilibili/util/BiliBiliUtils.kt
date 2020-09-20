package me.xiaox.molexbilibili.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.nio.charset.StandardCharsets

object BiliBiliUtils {
    private val json = Json { ignoreUnknownKeys = true }

    fun getAvInfo(av: Long): BiliBiliAvInfoData {
        val text = getStringByWeb("https://api.bilibili.com/x/web-interface/view?aid=$av")
        return json.decodeFromString(text)
    }

    fun getBiliBiliUserData(uid: Long): BiliBiliUserData {
        val text = getStringByWeb("https://api.bilibili.com/x/space/acc/info?mid=$uid")
        return json.decodeFromString(text)
    }

    fun getBiliBiliFollowerData(uid: Long): BiliBiliFollowerData {
        val text = getStringByWeb("https://api.bilibili.com/x/relation/stat?vmid=$uid")
        return json.decodeFromString(text)
    }

    private fun getStringByWeb(url: String): String {
        val webText = StringBuilder()
        try {
            val urlObject = URL(url)
            val uc: URLConnection = urlObject.openConnection()
            val br = BufferedReader(InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
            var inputLine: String?
            while (br.readLine().also { inputLine = it } != null) {
                webText.append(inputLine)
            }
            br.close()
        } catch (e: IOException) {
            return ""
        }
        return webText.toString()
    }
}

@Serializable
data class BiliBiliAvInfoData(
    val data: Data
) {
    @Serializable
    data class Data(
        val pic: String,
        val title: String,
        val desc: String,
        val owner: Owner,
        val stat: Stat
    ) {
        @Serializable
        data class Owner(
            val name: String
        )
        @Serializable
        data class Stat(
            val view: Int,
            val danmaku: Int,
            val reply: Int,
            val favorite: Int,
            val coin: Int,
            val share: Int,
            val like: Int
        )
    }
}

@Serializable
data class BiliBiliUserData(
    val data: Data
) {
    @Serializable
    data class Data(
        val name: String,
        val sex: String,
        val face: String,
        val sign: String,
        val level: Int
    )
}
@Serializable
data class BiliBiliFollowerData(
    val data: Data
) {
    @Serializable
    data class Data(
        val follower: Int
    )
}