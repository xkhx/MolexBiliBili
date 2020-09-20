package me.xiaox.molexbilibili

import com.google.auto.service.AutoService
import me.xiaox.molexbilibili.command.BiliBiliCommand
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

@AutoService(JvmPlugin::class)
object MolexBiliBili : KotlinPlugin(
    JvmPluginDescription("me.xiaox.molex-bilibili", "1.0-SNAPSHOT")
) {
    override fun onEnable() {
        BiliBiliCommand.register()
        logger.info { "插件加载成功!" }
    }
}