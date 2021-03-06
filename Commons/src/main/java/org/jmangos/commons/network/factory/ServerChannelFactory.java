/*******************************************************************************
 * Copyright (C) 2013 JMaNGOS <http://jmangos.org/>
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.jmangos.commons.network.factory;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * A factory for creating ServerChannel objects.
 */
public class ServerChannelFactory implements NetworkChannelFactory {

    /** The channel factory. */
    private final ChannelFactory channelFactory;

    /** The isa. */
    private final InetSocketAddress isa;

    /** The bootstrap. */
    private ServerBootstrap bootstrap;

    /**
     * Instantiates a new server channel factory.
     * 
     * @param isa
     *        the isa
     */
    public ServerChannelFactory(final InetSocketAddress isa) {

        this.channelFactory =
                new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool(),
                        (Runtime.getRuntime().availableProcessors() * 2) + 1);
        this.isa = isa;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.wowemu.common.network.netty.factory.NetworkChannelFactory#connect()
     */
    @Override
    public Channel connect() {

        return this.bootstrap.bind(this.isa);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.wowemu.common.network.netty.factory.NetworkChannelFactory#initialize
     * (org.jboss.netty.channel.ChannelPipelineFactory)
     */
    @Override
    public void initialize(final ChannelPipelineFactory pipelineFactory) {

        this.bootstrap = new ServerBootstrap(this.channelFactory);
        this.bootstrap.setPipelineFactory(pipelineFactory);
        this.bootstrap.setOption("child.bufferFactory",
                HeapChannelBufferFactory.getInstance(ByteOrder.LITTLE_ENDIAN));
        this.bootstrap.setOption("child.tcpNoDelay", true);
        this.bootstrap.setOption("child.keepAlive", true);
        this.bootstrap.setOption("child.reuseAddress", true);
        this.bootstrap.setOption("child.connectTimeoutMillis", 100);
        this.bootstrap.setOption("readWriteFair", true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.wowemu.common.network.netty.factory.NetworkChannelFactory#getAddress
     * ()
     */
    @Override
    public InetSocketAddress getAddress() {

        return this.isa;
    }

}
