package org.jmangos.test;

import org.jmangos.commons.network.model.NetworkChannel;
import org.jmangos.commons.network.model.SendablePacket;
import org.jmangos.commons.network.sender.AbstractPacketSender;

/**
 * The Class DummyPacketSender.
 */
public class DummyPacketSender implements AbstractPacketSender {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.wowemu.common.network.netty.sender.AbstractPacketSender#send(org.
     * wowemu.common.network.model.NetworkChannel,
     * org.wowemu.common.network.model.SendablePacket)
     */
    @Override
    public void send(final NetworkChannel channel, final SendablePacket packet) {}

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.wowemu.common.network.netty.sender.AbstractPacketSender#sendAndClose
     * (org.wowemu.common.network.model.NetworkChannel,
     * org.wowemu.common.network.model.SendablePacket)
     */
    @Override
    public void sendAndClose(final NetworkChannel channel, final SendablePacket networkPacket) {}

}
