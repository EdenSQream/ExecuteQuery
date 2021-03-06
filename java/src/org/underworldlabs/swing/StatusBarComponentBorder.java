/*
 * StatusBarComponentBorder.java
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

package org.underworldlabs.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

/**
 * Simple border for status bar panels.
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 160 $
 * @date     $Date: 2013-02-08 17:15:04 +0400 (пт, 08 фев 2013) $
 */
public class StatusBarComponentBorder implements Border {
    
    /** the border insets */
    private static final Insets insets = new Insets(1,1,1,0);
    
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(GUIUtils.getDefaultBorderColour());
        // top edge
        g.drawLine(x, y, width, y);
        // bottom edge
        g.drawLine(x, height-1, width, height-1);
        // left edge
        g.drawLine(x, 0, x, height-1);
    }

    public boolean isBorderOpaque() {
        return false;
    }
    
}













