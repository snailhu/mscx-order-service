package com.digitalchina.common;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Snail on 2017/1/10.
 */
public class GatewayResponseExecutor<Object> implements ResponseExtractor<Object> {
    public GatewayResponseExecutor(){
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object extractData(ClientHttpResponse response) throws IOException {
        InputStream input = response.getBody();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte  []bytes = new byte[1024];
        int read = -1;
        while((read = input.read(bytes)) >0){
            out.write(bytes,0,read);
        }
        return (Object) out.toString("UTF-8");
    }
}
