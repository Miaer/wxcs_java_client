package com.springboot.netty;

import com.springboot.common.SpringUtil;
import com.springboot.handler.DispatchHandler;
import com.springboot.netty.decode.FTPDecode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner：在spring启动完成之后，执行此回调。可实现多个
 * @author mrliz
 */
@Component
@Slf4j
public class UdpClient implements ApplicationRunner {

    private static final NioEventLoopGroup group = new NioEventLoopGroup();

    @Value("${netty.port}")
    private String port;

    @Override
    public void run(ApplicationArguments args){
        startUdpClient();
    }

     private void startUdpClient(){
         try {
             DispatchHandler dispatchHandler = SpringUtil.getBean(DispatchHandler.class);

             Bootstrap bootstrap = new Bootstrap();


             bootstrap.group(group)
                     .channel(NioDatagramChannel.class)
                     .option(ChannelOption.SO_BROADCAST, false)
                     .handler(new ChannelInitializer<NioDatagramChannel>() {
                         @Override
                         protected void initChannel(NioDatagramChannel datagramChannel) throws Exception {
                             datagramChannel.pipeline()
                                     .addLast(new FTPDecode())
                                     .addLast(dispatchHandler);
                         }
                     });
             try {
                 ChannelFuture future = bootstrap.bind(Integer.parseInt(port)).sync();
                 future.channel().closeFuture().sync();
             } catch (InterruptedException e) {
                 e.printStackTrace();
                 log.error("Netty Interrupted Error:" + e.getMessage());
             }
         }
         catch (Exception ex) {
             ex.printStackTrace();
             log.error("Netty Error:" + ex.getMessage());
         }
         finally {
             group.shutdownGracefully();
             log.info("------------------------Netty Close，port is ：" + port);
         }
     }


}
