package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String httpRequestType = null;
        String poohMode = null;
        String sourceName = null;
        String param = null;
        String[] lines = content.split(System.lineSeparator());
        if (lines.length == 8 && lines[0].contains("POST")) {
            String[] subLines = lines[0].split(" /|/| HTTP/");
            httpRequestType = subLines[0];
            poohMode = subLines[1];
            sourceName = subLines[2];
            param = lines[7];
        }
        if (lines.length == 4 && lines[0].contains("GET")) {
            String[] subLines = lines[0].split(" /|/| HTTP/");
            if (subLines.length == 5) {
                httpRequestType = subLines[0];
                poohMode = subLines[1];
                sourceName = subLines[2];
                param = subLines[3];
            } else if (subLines.length == 4) {
                httpRequestType = subLines[0];
                poohMode = subLines[1];
                sourceName = subLines[2];
                param = "";

            }
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
