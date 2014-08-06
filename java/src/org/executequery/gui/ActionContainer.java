/*
 * ActionContainer.java
 *
 * Copyright (C) 2002-2013 Takis Diakoumis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.executequery.gui;

/**
 * Defines a container (dialog, internal frame etc.) where
 * a component panel completing a process may indicate for this
 * container to close.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 160 $
 * @date     $Date: 2013-02-08 17:15:04 +0400 (пт, 08 фев 2013) $
 */
public interface ActionContainer {

    /**
     * Indicates whether this is a dialog.
     *
     * @return true | false
     */
    public boolean isDialog();
    
    /**
     * Indicates that a [long-running] process has begun.
     * This triggers the glass pane on and sets the cursor appropriately.
     */
    public void block();

    /**
     * Indicates that a [long-running] process has ended. 
     * This triggers the glass pane off and sets the cursor appropriately.
     */
    public void unblock();    
    
    /**
     * Indicates the process has completed.
     */
    public void finished();

}





