package org.jboss.resteasy.test;

import static org.jboss.resteasy.test.TestPortProvider.generateURL;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.jboss.resteasy.plugins.server.netty.NettyContainer;
import org.jboss.resteasy.util.HttpHeaderNames;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

public class RESTEASY1323Test {
    static String BASE_URI = generateURL("");

    static final int REQUEST_TIMEOUT = 6000;

    @BeforeAll
    public static void setupSuite() throws Exception {
        NettyContainer.start().getRegistry().addSingletonResource(new AsyncJaxrsResource());
    }

    @AfterAll
    public static void tearDownSuite() throws Exception {
        NettyContainer.stop();
    }

    @BeforeEach
    public void setupTest() throws Exception {
    }

    @AfterEach
    public void tearDownTest() throws Exception {
    }

    @Test
    public void testAsyncKeepConnection() throws Exception {
        Assertions.assertTimeout(Duration.ofMillis(10 * REQUEST_TIMEOUT), () -> callAsyncTwiceWithKeepAlive());
    }

    // use netty to better monitor channel connection.
    private void callAsyncTwiceWithKeepAlive() throws InterruptedException, MalformedURLException {

        final CountDownLatch responseLatch = new CountDownLatch(2);
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new HttpClientCodec());
                            ch.pipeline().addLast(new HttpObjectAggregator(4096));
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpResponse>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) {
                                    responseLatch.countDown();
                                }
                            });
                        }
                    });

            // first request;
            URL url = new URL(BASE_URI + "/jaxrs");
            // Make the connection attempt.
            final Channel ch = b.connect(url.getHost(), url.getPort()).sync().channel();

            // Prepare the HTTP request.
            HttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, url.getFile());
            request.headers().set(HttpHeaderNames.HOST, url.getHost());
            request.headers().set(HttpHeaderNames.CONNECTION, "keep-alive");

            // Send the HTTP request.
            ChannelFuture cf = ch.writeAndFlush(request);
            cf.await();
            Thread.sleep(2000);

            // 2nd request
            URL url2 = new URL(BASE_URI + "/jaxrs/empty");
            HttpRequest request2 = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, url2.getFile());
            request2.headers().set(HttpHeaderNames.HOST, url2.getHost());
            request2.headers().set(HttpHeaderNames.CONNECTION, "keep-alive");
            ch.writeAndFlush(request2);

            responseLatch.await();
        } finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully().await();
        }
    }

    @Test
    public void testAsyncCloseConnection() throws Exception {
        Assertions.assertTimeout(Duration.ofMillis(5 * REQUEST_TIMEOUT), () -> callAsyncWithCloseConnection());
    }

    // use netty to better monitor channel connection.
    private void callAsyncWithCloseConnection() throws InterruptedException, MalformedURLException {

        final CountDownLatch responseLatch = new CountDownLatch(2);
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new HttpClientCodec());
                            ch.pipeline().addLast(new HttpObjectAggregator(4096));
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpResponse>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) {
                                    //                             System.out.println("HTTP response from resteasy: "+msg);
                                    responseLatch.countDown();
                                }

                            });
                        }
                    });

            // first request;
            URL url = new URL(BASE_URI + "/jaxrs");
            // Make the connection attempt.
            final Channel ch = b.connect(url.getHost(), url.getPort()).sync().channel();

            // Prepare the HTTP request.
            HttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, url.getFile());
            request.headers().set(HttpHeaderNames.HOST, url.getHost());
            request.headers().set(HttpHeaderNames.CONNECTION, "close");

            // Send the HTTP request.
            ch.writeAndFlush(request);

            // waiting for server close connection after idle.
            ch.closeFuture().await();
        } finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully().await();
        }
    }
}