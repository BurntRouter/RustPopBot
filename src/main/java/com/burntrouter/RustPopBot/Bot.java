package com.burntrouter.RustPopBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bot {
    public static void main(String[] args) {
        List<File> files = (List<File>) FileUtils.listFiles(new File("conf"), new String[] {"json"}, false);
        for (File file : files) {
            System.out.println("Config found: " + file.getName());
            RunBot runBot = new RunBot();
            runBot.run(file);
        }
    }

    public static class RunBot extends Thread {
        public void run(File file) {
            try{
                ConfigLoader configLoader = new ConfigLoader(file.getPath());
                JDA jda;
                System.out.println("Loading JDA for: " + file.getName());
                jda = JDABuilder.createLight(configLoader.getToken()).setActivity(Activity.of(Activity.ActivityType.WATCHING, "Waiting for RCON...")).build();
                System.out.println(jda.getSelfUser().getName() + " is loaded");
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        GetPop getPop = new GetPop();
                        try {
                            jda.getPresence().setActivity(Activity.of(Activity.ActivityType.WATCHING, getPop.GetPop(file)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                timer.scheduleAtFixedRate(task, 500, 5000);
            } catch(Exception e) {
                System.out.println("Error with shard from " + file.getName());
                e.printStackTrace();
            }

        }
    }
}
