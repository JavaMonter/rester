package org.jboss.resteasy.plugins.server.netty;

import java.util.List;
import java.util.Map;

import jakarta.ws.rs.ext.RuntimeDelegate;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;

/**
 * {@link MessageToMessageEncoder} implementation which encodes {@link org.jboss.resteasy.spi.HttpResponse}'s to
 * {@link HttpResponse}'s
 *
 * This implementation is {@link Sharable}
 *
 * @author Norman Maurer
 *
 */
@Sharable
public class RestEasyHttpResponseEncoder extends MessageToMessageEncoder<NettyHttpResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyHttpResponse nettyResponse, List<Object> out) throws Exception {
        nettyResponse.getOutputStream().flush();
        if (nettyResponse.isCommitted()) {
            out.add(LastHttpContent.EMPTY_LAST_CONTENT);
        } else {
            HttpResponse response = nettyResponse.getDefaultHttpResponse();
            out.add(response);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void transformHeaders(NettyHttpResponse nettyResponse, HttpResponse response,
            ResteasyProviderFactory factory) {
        if (nettyResponse.isKeepAlive()) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        } else {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }

        for (Map.Entry<String, List<Object>> entry : nettyResponse.getOutputHeaders().entrySet()) {
            String key = entry.getKey();
            for (Object value : entry.getValue()) {
                RuntimeDelegate.HeaderDelegate delegate = factory.getHeaderDelegate(value.getClass());
                if (delegate != null) {
                    response.headers().add(key, delegate.toString(value));
                } else {
                    response.headers().set(key, value.toString());
                }
            }
        }
    }

}
