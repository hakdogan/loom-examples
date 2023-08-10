package org.jugistanbul.util;

/**
 * @author hakdogan (hakdogan75@gmail.com)
 * Created on 30.07.2023
 ***/
public enum ThreadType
{
    PLATFORM("Platform Threads"), VIRTUAL("Virtual Threads");
    private String desc;

    ThreadType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
