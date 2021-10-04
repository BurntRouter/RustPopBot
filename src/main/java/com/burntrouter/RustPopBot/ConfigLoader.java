package com.burntrouter.RustPopBot;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class ConfigLoader {
    public JSONObject configJson;

    public ConfigLoader(String path) throws IOException {
        this.configJson = new JSONObject(FileUtils.readFileToString(new File(path), Charset.defaultCharset()));
    }

    public String getHostname() throws IOException {
        return this.configJson.getString("hostname");
    }

    public int getRCONPort() throws IOException {
        return this.configJson.getInt("port");
    }

    public String getRCONPassword() throws IOException {
        return this.configJson.getString("password");
    }

    public String getToken() throws IOException {
        return this.configJson.getString("token");
    }
}
