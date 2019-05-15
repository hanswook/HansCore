package com.mckj.tec_library.http;



import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.mckj.tec_library.BuildConfig;
import com.mckj.tec_library.utils.EmptyUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultRetrofit extends IRetrofit {

    public DefaultRetrofit(@NonNull String baseUrl, @NonNull List<Interceptor> interceptors, List<Interceptor> networkInterceptor) {
        this(baseUrl, interceptors, networkInterceptor, TIMEOUT, TIMEOUT, TIMEOUT);
    }

    public DefaultRetrofit(@NonNull String baseUrl,
                           @NonNull List<Interceptor> interceptors,
                           List<Interceptor> networkInterceptor,
                           long connectTimeout,
                           long readTimeout,
                           long writeTimeout) {
        super(baseUrl, interceptors, networkInterceptor, connectTimeout, readTimeout, writeTimeout);
    }

    @Override
    OkHttpClient buildClient() {

//        X509TrustManager trustManager;
//        SSLSocketFactory sslSocketFactory;
//        final InputStream inputStream;
//
//            inputStream = BaseApplication.getApp().getResources().openRawResource(R.raw.rootca); // 得到证书的输入流
//            try {
//
//                trustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                sslContext.init(null, new TrustManager[]{trustManager}, null);
//                sslSocketFactory = sslContext.getSocketFactory();
//
//            } catch (GeneralSecurityException e) {
//                throw new RuntimeException(e);
//            }


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .sslSocketFactory(getDefaultSocketFactory())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)

                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false);

        if (BuildConfig.DEBUG) {

        }else{
            builder.proxy(Proxy.NO_PROXY);
        }
        if (!EmptyUtils.isEmpty(networkInterceptor)) {
            for (Interceptor interceptor : networkInterceptor) {
                builder.addNetworkInterceptor(interceptor);
            }
        }
        if (!EmptyUtils.isEmpty(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();

    }


    /**
     * 信任所有证书 （官方不推荐使用）
     *
     * @return
     */
    public static SSLSocketFactory getDefaultSocketFactory() {
        TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        return createSSLSocketFactory(trustManagers);
    }

    private static SSLSocketFactory createSSLSocketFactory(TrustManager[] trustManagers) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以流的方式添加信任证书
     */
    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * <p>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * xyr.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }


    /**
     * 添加password
     *
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


    @Override
    Converter.Factory converterFactory() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new DefaultNullStringAdapterFactory())
                .create();
        return GsonConverterFactory.create(gson);
    }

    @Override
    CallAdapter.Factory callAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Override
    String baseUrl() {
        return baseUrl;
    }

    /**
     * create retrofit service
     *
     * @param service the service class
     * @param <T>     class type
     * @return the real service instance
     */
    @Override
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}

