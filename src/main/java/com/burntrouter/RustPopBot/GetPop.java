package com.burntrouter.RustPopBot;

import io.graversen.rust.rcon.rustclient.RustClient;

import java.io.File;
import java.io.IOException;

public class GetPop {
    private RustClient rustClient;
    public String GetPop(File file) throws IOException {
        try{
            ConfigLoader configLoader = new ConfigLoader(file.getPath());
            rustClient = RustClient.builder().connectTo(configLoader.getHostname(), configLoader.getRCONPassword(), configLoader.getRCONPort()).quietMode().withoutLogger().build();
            rustClient.open();
            if(!rustClient.isOpen()) {
                return "Waiting for RCON...";
            } else {
                String currentPop = String.valueOf(rustClient.rcon().info().getServerInfo().getCurrentPlayers());
                String maxPop = String.valueOf(rustClient.rcon().info().getServerInfo().getMaxPlayers());
                String players;
                try {
                    int joining = rustClient.rcon().info().getServerInfo().getJoiningPlayers();
                    if (joining > 0) {
                        players = currentPop + "/" + maxPop + " | " + joining + " joining";
                    } else {
                        players = currentPop + "/" + maxPop;
                    }
                } catch (Exception e) {
                    players = currentPop + "/" + maxPop;
                }
                rustClient.close();
                return players;
            }
        } catch (Exception e) {
            return "Waiting for RCON...";
        }
    }
}
