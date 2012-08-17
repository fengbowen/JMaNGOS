/*******************************************************************************
 * Copyright (C) 2012 JMaNGOS <http://jmangos.org/>
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
 *******************************************************************************/
package org.jmangos.realm.network.netty.packetClient.client;

import java.nio.BufferUnderflowException;

import javax.inject.Inject;
import javax.inject.Named;

import org.jmangos.commons.network.netty.sender.AbstractPacketSender;
import org.jmangos.realm.network.netty.packetClient.AbstractWoWClientPacket;
import org.jmangos.realm.network.netty.packetClient.server.SMSG_PLAYED_TIME;
import org.springframework.stereotype.Component;

/**
 * The Class CMSG_PLAYED_TIME.
 */
@Component
public class CMSG_PLAYED_TIME extends AbstractWoWClientPacket {
    
    /** The sender. */
    @Inject
    @Named("nettyPacketSender")
    private AbstractPacketSender sender;
    
    /** The unk. */
    byte                         unk;
    
    /*
     * (non-Javadoc)
     * 
     * @see org.wowemu.common.network.model.ReceivablePacket#readImpl()
     */
    @Override
    protected void readImpl() throws BufferUnderflowException, RuntimeException {
    
        this.unk = (byte) readC();
        
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.wowemu.common.network.model.ReceivablePacket#runImpl()
     */
    @Override
    protected void runImpl() {
    
        this.sender.send(getClient(), new SMSG_PLAYED_TIME(this.unk));
        
    }
    
}
