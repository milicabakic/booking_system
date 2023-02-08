package com.griddynamics.lidlbooking.logging;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedBodyServletInputStream extends ServletInputStream {

    private final InputStream cachedBody;

    public CachedBodyServletInputStream(final byte[] cachedBody) {
        this.cachedBody = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedBody.available() == 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(final ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        return cachedBody.read();
    }
}
