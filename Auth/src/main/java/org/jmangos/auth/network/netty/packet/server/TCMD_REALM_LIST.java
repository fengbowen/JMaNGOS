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
package org.jmangos.auth.network.netty.packet.server;

import org.apache.log4j.Logger;
import org.jmangos.auth.model.Account;
import org.jmangos.auth.model.Realm;
import org.jmangos.auth.network.netty.packet.AbstractWoWServerPacket;
import org.jmangos.auth.service.RealmListService;

import javolution.util.FastMap;

/**
 * The Class <tt>TCMD_REALM_LIST</tt>.
 */
public class TCMD_REALM_LIST extends AbstractWoWServerPacket {

	/** The Constant log. */
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(TCMD_REALM_LIST.class);

	/** The worldlist. */
	private RealmListService worldlist;

	/**
	 * Instantiates a new TCMD_REALM_LIST.
	 * 
	 * @param worldlist
	 *            the worldlist
	 */
	public TCMD_REALM_LIST(RealmListService worldlist) {
		this.worldlist = worldlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmangos.commons.network.model.SendablePacket#writeImpl()
	 */
	@Override
	protected void writeImpl() {
		FastMap<Integer, Integer> amountofCharacters = worldlist
				.getAmountCharacters(((Account) (getChannel()
						.getChanneledObject())).getObjectId());
		writeH(worldlist.getByteSize());
		writeD(0);
		writeH(worldlist.getRealmCount());
		for (Realm realm : worldlist.getWorlds().values()) {
			writeC(realm.getIcon());
			writeC((realm.getAllowedSecurityLevel() > ((Account) (getChannel()
					.getChanneledObject())).getAccessLevel()) ? 1 : 0);
			writeC(realm.getRealmflags());
			writeS(realm.getName());
			writeS(realm.getAddress());
			writeF(realm.getPopulation());
			writeC((amountofCharacters.containsKey(realm.getId())) ? amountofCharacters
					.get(realm.getId()) : 0);
			writeC(realm.getTimezone());
			writeC(0x2C);
		}
		writeH(0x0010);
	}
}
