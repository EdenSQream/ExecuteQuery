/*
 * DefaultDriverLoader.java
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

package org.executequery.datasource;

import org.executequery.databasemediators.DatabaseDriver;
import org.executequery.log.Log;
import org.underworldlabs.jdbc.DataSourceException;
import org.underworldlabs.util.DynamicLibraryLoader;
import org.underworldlabs.util.MiscUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 1241 $
 * @date     $Date: 2013-04-10 22:56:22 +1000 (Wed, 10 Apr 2013) $
 */
public class DefaultDriverLoader implements DriverLoader {

    private static final Map<DatabaseDriver, Driver> LOADED_DRIVERS = new HashMap<DatabaseDriver, Driver>();
    
    public Driver load(DatabaseDriver databaseDriver) {

        Driver driver = null;
        if (LOADED_DRIVERS.containsKey(databaseDriver)) {
            
            return LOADED_DRIVERS.get(databaseDriver);
        }

        try {
        
            Class<?> clazz = null;
            String driverName = databaseDriver.getClassName();
    
            Log.info("Loading JDBC driver class: " + driverName);
            
            if (!databaseDriver.isDefaultSunOdbc()) {
                
                String path = databaseDriver.getPath();
                Log.trace("Loading driver from: " + path);
                
                if (!MiscUtils.isNull(path)) {
    
                    URL[] urls = MiscUtils.loadURLs(path);
                    DynamicLibraryLoader loader = new DynamicLibraryLoader(urls);
                    clazz = loader.loadLibrary(driverName);
                
                } else {
    
                    clazz = loadUsingSystemLoader(driverName);
                }
    
            } else {
                
                clazz = loadUsingSystemLoader(driverName);
            } 

            Object object = clazz.newInstance();
            driver = (Driver) object;

            Log.info("JDBC driver " + driverName + " loaded - v" 
                    + driver.getMajorVersion() + "." + driver.getMinorVersion());
            
            LOADED_DRIVERS.put(databaseDriver, driver);
            
        } catch (ClassNotFoundException e) {
            
            handleException("The specified JDBC driver class was not found", databaseDriver, e);
        
        } catch (MalformedURLException e) {

            handleException("Error loading the driver from the specified path.", databaseDriver, e);
            
        } catch (InstantiationException e) {
            
            handleException(e.getMessage(), databaseDriver, e);

        } catch (IllegalAccessException e) {
            
            handleException("The specified JDBC driver class was not accessible", databaseDriver, e);
        }

        return driver;
    }

    private Class<?> loadUsingSystemLoader(String driverName) throws ClassNotFoundException {

        return Class.forName(driverName, true, ClassLoader.getSystemClassLoader());
    }

    public void unload(DatabaseDriver databaseDriver) {
        
        if (LOADED_DRIVERS.containsKey(databaseDriver)) {
            
            Driver driver = LOADED_DRIVERS.get(databaseDriver);
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {e.printStackTrace();}
            LOADED_DRIVERS.remove(databaseDriver);
            driver = null;
        }
        
    }
    
    private void handleException(String message, DatabaseDriver databaseDriver, Throwable e) {

        if (Log.isDebugEnabled()) {
            
            Log.error("Error loading JDBC driver " + databaseDriver.getClassName(), e);
        }

        throw new DataSourceException(message);
    }
    
}




