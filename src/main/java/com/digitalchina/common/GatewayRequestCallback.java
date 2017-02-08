package com.digitalchina.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;
import com.digitalchina.invoketrace.client.InvokeTrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by Snail on 2017/1/10.
 */
public  class GatewayRequestCallback implements RequestCallback, InvokeTrace{

    private String params;

    private HttpHeaders headers;

    public GatewayRequestCallback(){}

    public GatewayRequestCallback(String params, HttpHeaders headers){
        this.params = params;
        this.headers = headers;
    }

    @Override
    public byte[] getBytes() {
        return this.params == null ? null : this.params.getBytes();
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        String params = this.params;
        HttpHeaders headers = request.getHeaders();
        if(getHeaders() != null){
            Set<String> keys = this.getHeaders().keySet();
            for (String key : keys) {
                headers.put(key, this.getHeaders().get(key));
            }
        }
        //need encoded?
			/*if(headers.getContentType() == MediaType.APPLICATION_FORM_URLENCODED){
				params = params == null ? "" :  URLEncoder.encode(this.params, "UTF-8");
			}*/
        OutputStream out = request.getBody();
        out.write(params.getBytes());
        out.flush();
        out.close();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }
}
