package org.mediamod.mediamod.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import org.apache.commons.io.FileUtils;
import org.mediamod.mediamod.MediaMod;
import org.mediamod.mediamod.util.PlayerMessager;
import org.mediamod.mediamod.util.VersionChecker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * The client-side command to update MediaMod
 *
 * @see net.minecraft.command.ICommand
 */
public class MediaModUpdateCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "mediamodupdate";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/mediamodupdate";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (VersionChecker.INSTANCE.IS_LATEST_VERSION) {
            PlayerMessager.sendMessage("MediaMod is up-to-date!", true);
        } else {
            PlayerMessager.sendMessage("Downloading MediaMod v" + VersionChecker.INSTANCE.LATEST_VERSION_INFO.latestVersionS, true);
            try {
                FileUtils.copyURLToFile(new URL(VersionChecker.INSTANCE.LATEST_VERSION_INFO.downloadURL), new File(MediaMod.class.getProtectionDomain().getCodeSource().getLocation().getFile().substring(0,
                        MediaMod.class.getProtectionDomain().getCodeSource().getLocation().getFile().indexOf(""))));
                PlayerMessager.sendMessage("Update downloaded to " + MediaMod.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "! Relaunch Minecraft to complete installation", true);
            } catch (IOException e) {
                e.printStackTrace();
                PlayerMessager.sendMessage("Failed to download MediaMod v" + VersionChecker.INSTANCE.LATEST_VERSION_INFO.latestVersionS, true);
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}