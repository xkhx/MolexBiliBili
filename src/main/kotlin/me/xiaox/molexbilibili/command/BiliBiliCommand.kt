package me.xiaox.molexbilibili.command

import me.xiaox.molexbilibili.MolexBiliBili
import me.xiaox.molexbilibili.util.BiliBiliUtils
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.UserCommandSender
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.uploadAsImage
import java.net.URL

object BiliBiliCommand : CompositeCommand(
    MolexBiliBili, "bilibili",
    description = "查询bilibili用户&视频信息"
) {
    @SubCommand
    suspend fun CommandSender.user(uid: Long) {
        kotlin.runCatching {
            val userData = BiliBiliUtils.getBiliBiliUserData(uid)
            val followerData = BiliBiliUtils.getBiliBiliFollowerData(uid)
            val result = MessageChainBuilder()
            if (this is UserCommandSender) {
                val face = URL(userData.data.face).openStream().uploadAsImage(subject)
                result.append(face).append("\n")
            }
            result.append(
                "用户: ${userData.data.name}\n" +
                        "性别: ${userData.data.sex}\n" +
                        "签名: ${userData.data.sign}\n" +
                        "等级: ${userData.data.level}\n" +
                        "粉丝: ${followerData.data.follower}"
            )
            this.sendMessage(result.build())
        }.getOrElse {
            this.sendMessage("BiliBili > $it")
        }
    }

    @SubCommand
    suspend fun CommandSender.av(av: Long) {
        kotlin.runCatching {
            val avInfoData = BiliBiliUtils.getAvInfo(av)
            val result = MessageChainBuilder()
            if (this is UserCommandSender) {
                val pic = URL(avInfoData.data.pic).openStream().uploadAsImage(subject)
                result.append(pic).append("\n")
            }
            result.append(
                "UP: ${avInfoData.data.owner.name}\n" +
                        "标题: ${avInfoData.data.title}\n" +
                        "描述: ${avInfoData.data.desc}\n" +
                        "弹幕: ${avInfoData.data.stat.danmaku}\n" +
                        "投币: ${avInfoData.data.stat.coin}\n" +
                        "点赞: ${avInfoData.data.stat.like}\n" +
                        "分享: ${avInfoData.data.stat.share}\n" +
                        "收藏: ${avInfoData.data.stat.favorite}\n" +
                        "查看: ${avInfoData.data.stat.view}\n" +
                        "回复: ${avInfoData.data.stat.reply}"
            )
            this.sendMessage(result.build())
        }.getOrElse {
            this.sendMessage("BiliBili > $it")
        }
    }
}