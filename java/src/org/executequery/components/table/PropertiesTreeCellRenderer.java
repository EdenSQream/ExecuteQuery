/*
 * PropertiesTreeCellRenderer.java
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

package org.executequery.components.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.UIManager;

import org.underworldlabs.swing.tree.AbstractTreeCellRenderer;

/**
 * Properties frame tree renderer.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 1202 $
 * @date     $Date: 2013-02-18 11:14:16 +1100 (Mon, 18 Feb 2013) $
 */
public class PropertiesTreeCellRenderer extends AbstractTreeCellRenderer {
    
    private Color textBackground;
    private Color textForeground;
    private Color selectionBackground;
	private Color selectionForeground;
    
    public PropertiesTreeCellRenderer() {

        textBackground = UIManager.getColor("Tree.textBackground");
        textForeground = UIManager.getColor("Tree.textForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");        
        selectionForeground = UIManager.getColor("Tree.selectionForeground");        

        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    }
    
    public Component getTreeCellRendererComponent(JTree tree, 
                                                  Object value,
                                                  boolean isSelected, 
                                                  boolean isExpanded,
                                                  boolean isLeaf, 
                                                  int row, 
                                                  boolean hasFocus) {
        
        this.selected = isSelected;
        this.hasFocus = hasFocus;
        
        if (!isSelected) {
           
            setBackground(textBackground);
            setForeground(textForeground);

        } else {
          
            setBackground(selectionBackground);
            setForeground(selectionForeground);
        }

        setText(value.toString());
        
        return this;
    }
    
}




