package com.springboot.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

/**
 * @author mrliz
 */
@Component
public class DispatchHandler extends ChannelInboundHandlerAdapter {

    /**
     * 在连接被建立并且准备进行通信时被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println("channelRead");
    }

    /**
     * Netty由于IO错误或者处理器在处理事件时抛出的异常时被调用
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
