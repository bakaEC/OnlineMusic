package com.u21.a0903_onlinemusic.translate.pic.http;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class HttpCallback<T> implements Callback {
    public void onSuccess(T response) throws JSONException {


    }

    protected void onFailure(Throwable e) {

    }

    @Override
    public final void onFailure(Call call, final IOException e) {
        e.printStackTrace();
        sendFailureMessage(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }

    final void sendFailureMessage(final Exception e) {
        onFailure(e);
    }

    final void sendSuccessMessage(final T response) throws JSONException {
        onSuccess(response);
    }
}
