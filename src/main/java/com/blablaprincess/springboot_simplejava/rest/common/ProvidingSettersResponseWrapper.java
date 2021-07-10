package com.blablaprincess.springboot_simplejava.rest.common;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class ProvidingSettersResponseWrapper extends HttpServletResponseWrapper {

    public ProvidingSettersResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this.setStatus(sc);
    }

    @Override
    public void sendError(int sc) throws IOException {
        this.setStatus(sc);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
    }

}
