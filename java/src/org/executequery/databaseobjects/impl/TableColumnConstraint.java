/*
 * TableColumnConstraint.java
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

package org.executequery.databaseobjects.impl;

import org.executequery.databaseobjects.DatabaseColumn;
import org.executequery.databaseobjects.DatabaseTable;
import org.executequery.databaseobjects.NamedObject;
import org.underworldlabs.jdbc.DataSourceException;
import org.underworldlabs.util.MiscUtils;

/**
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 160 $
 * @date     $Date: 2013-02-08 17:15:04 +0400 (пт, 08 фев 2013) $
 */
public class TableColumnConstraint extends AbstractDatabaseObjectElement 
                                   implements ColumnConstraint {
    
    public static final String EMPTY = "";
    /** the table column this constraint belongs to */
    private DatabaseTableColumn column;

    /** The referenced catalog of this constraint */
    private String referencedCatalog;

    /** The referenced schema of this constraint */
    private String referencedSchema;

    /** The referenced table of this constraint */
    private String referencedTable;
    
    /** The referenced column of this constraint */
    private String referencedColumn;

    /** The type of constraint */
    private int keyType;
    
    /** Whether this constraint is new (for editing a table definition) */
    private boolean newConstraint;

    /** Whether this constraint is marked to be dropped */
    private boolean markedDeleted;

    /** an original copy of this object */
    private TableColumnConstraint copy;
    
    /** the foreign key column */
    private DatabaseColumn foreignKeyColumn;
    
    /** Creates a new instance of TableColumnConstraint */
    public TableColumnConstraint(int type) {
        this(null, type);
    }

    /** Creates a new instance of TableColumnConstraint */
    public TableColumnConstraint(DatabaseTableColumn column, int keyType) {
        setColumn(column);
        setKeyType(keyType);
    }

    /**
     * Returns the column object referenced by this column or
     * null if its not a foreign key column.
     *
     * @return the referenced column
     */
    public DatabaseColumn getForeignKeyReference() {
        
        if (isForeignKey()) {

            return foreignKeyColumn;
        }
        
        return null;
    }

    /**
     * Returns whether this is a foreign key constraint.
     *
     * @return true | false
     */
    public boolean isForeignKey() {
        return getKeyType() == FOREIGN_KEY;
    }

    /**
     * Returns whether this is a primary key constraint.
     *
     * @return true | false
     */
    public boolean isPrimaryKey() {
        return getKeyType() == PRIMARY_KEY;
    }

    /**
     * Returns whether this is a unique key constraint.
     *
     * @return true | false
     */
    public boolean isUniqueKey() {
        return getKeyType() == UNIQUE_KEY;
    }

    /**
     * Returns whether this is a new constraint.
     *
     * @return true | false
     */
    public boolean isNewConstraint() {
        return newConstraint;
    }
    
    /**
     * Sets this constraint as a new constraint as specified.
     *
     * @param newConstraint true | false
     */
    public void setNewConstraint(boolean newConstraint) {
        this.newConstraint = newConstraint;
    }

    /**
     * Returns the string representation of this constraints
     * type - ie. PRIMARY, FOREIGN, UNIQUE.
     *
     * @return the type name
     */
    public String getTypeName() {
        int _type = getKeyType();
        switch (_type) {
            case 0:
                return PRIMARY;
            case 1:
                return FOREIGN;
            case 2:
                return UNIQUE;
            default:
                return null;
        }
    }
    
    /**
     * Returns whether the schema has been defined.
     *
     * @return true | false
     */
    public boolean hasSchemaName() {
        return !(MiscUtils.isNull(getSchemaName()));
    }

    /**
     * Sets the constraint type as specified.
     *
     * @param dataType the constraint type
     */
    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }
    
    /**
     * Returns the constraint type identifier.
     *
     * @return the type int
     */
    public int getKeyType() {
        return keyType;
    }

    /**
     * Returns the table associated with this constraint.
     *
     * @return the table
     */
    public DatabaseTable getTable() {
        if (column != null) {
            return column.getTable();
        }
        return null;
    }

    /**
     * Returns the table name associated with this constraint.
     *
     * @return the table name
     */
    public String getTableName() {
        if (getTable() != null) {
            return getTable().getName();
        }
        return null;
    }

    /**
     * Returns the column name associated with this constraint.
     *
     * @return the column name
     */
    public String getColumnName() {
        if (column != null) {
            return column.getName();
        }
        return null;
    }

    /**
     * Returns the table column parent to this object.
     *
     * @return the table column
     */
    public DatabaseTableColumn getColumn() {
        return column;
    }

    /**
     * Sets the table column parent to this object 
     * to that specified.
     *
     * @param column parent column
     */
    public void setColumn(DatabaseTableColumn column) {
        this.column = column;
    }
    
    /**
     * Returns the catalog name parent to this column.
     *
     * @return the catalog name
     */
    public String getCatalogName() {
        if (column != null) {
            return column.getCatalogName();
        }
        return null;
    }

    /**
     * Returns the schema name parent to this database column.
     *
     * @return the schema name
     */
    public String getSchemaName() {
        if (column != null) {
            return column.getSchemaName();
        }
        return null;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public void setReferencedTable(String referencedTable) {
        this.referencedTable = referencedTable;
    }

    public String getReferencedColumn() {
        return referencedColumn;
    }

    public void setReferencedColumn(String referencedColumn) {
        this.referencedColumn = referencedColumn;
    }

    public String getReferencedSchema() {
        return referencedSchema;
    }

    public void setReferencedSchema(String referencedSchema) {
        this.referencedSchema = referencedSchema;
    }

    public String getReferencedCatalog() {
        return referencedCatalog;
    }

    public void setReferencedCatalog(String referencedCatalog) {
        this.referencedCatalog = referencedCatalog;
    }

    public boolean isMarkedDeleted() {
        return markedDeleted;
    }

    public void setMarkedDeleted(boolean markedDeleted) {
        this.markedDeleted = markedDeleted;
    }
    
    /**
     * Returns whether this constraint has been modified.
     * A modification exists where this constraint is not new,
     * an internal value has changed or it has been marked 
     * for deletion.
     *
     * @return true | false
     */
    public boolean isAltered() {
        // check if its new - not modified
        if (isNewConstraint()) {
            return false;
        }
        
        // check for a pending deletion
        if (isMarkedDeleted()) {
            return true;
        }

        // ensure we have something to compare to
        if (copy == null) {
            return false;
        }
        
        // allow for name changes only
        return !(copy.getName().equalsIgnoreCase(getName()));
    }
    
    /**
     * Returns the ALTER TABLE statement to modify this constraint.
     */
    public String getAlteredSQLText() {
        if (!isAltered() && !isNewConstraint()) {
            return "";
        }

        // if its a new constraint return the create text
        if (isNewConstraint()) {
            return getCreateSQLText();
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append("ALTER TABLE ");
        sb.append(getTableName());

        // check for a pending deletion
        if (isMarkedDeleted()) {
            sb.append(" DROP CONSTRAINT ");
            if (copy == null) {
                sb.append(getName());
            } else {
                sb.append(copy.getName());
            }
        }
        else {
            // check for a name change
            if (!(copy.getName().equalsIgnoreCase(getName()))) {
                sb.append(" RENAME CONSTRAINT ");
                sb.append(copy.getName());
                sb.append(" TO ");
                sb.append(getName());
            }
        }

        sb.append(";");
        return sb.toString();
    }
    
    /**
     * Returns the CONSTRAINT portion of the SQL statement 
     * for this constraint.
     */
    public String getConstraintSQLText() {
        StringBuffer sb = new StringBuffer();
        sb.append("CONSTRAINT ");
        sb.append(getName() == null ? "" : getName());
        
        int _type = getKeyType();
        switch (_type) {
            case PRIMARY_KEY:
                sb.append(" PRIMARY KEY (");
                break;
            case FOREIGN_KEY:
                sb.append(" FOREIGN KEY (");
                break;
            case UNIQUE_KEY:
                sb.append(" UNIQUE (");
                break;
        }
        
        sb.append(getColumnName());
        sb.append(")");

        if (_type == FOREIGN_KEY) {
            sb.append(" REFERENCES ");
            sb.append(getReferencedTable());
            sb.append("(");
            sb.append(getReferencedColumn());
            sb.append(")");
        }

        return sb.toString();
    }
    
    /**
     * Returns the ALTER TABLE statement to create this constraint.
     */
    public String getCreateSQLText() {
        StringBuffer sb = new StringBuffer();
        sb.append("ALTER TABLE ");
        sb.append(getTableName());
        sb.append(" ADD ");
        sb.append(getConstraintSQLText());
        sb.append(";");
        return sb.toString();
    }
    
    /**
     * Makes a copy of itself. A copy of this object may 
     * not always be required and may be made available only
     * when deemed necessary - ie. table meta changes.
     */
    public void makeCopy() {
        if (copy == null) {
            // ensure the table column has a copy
            getColumn().makeCopy();
            // make a local copy
            copy = new TableColumnConstraint(getKeyType());
            copyConstraint(this, copy);
        }
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return a clone of this object
     */
    public Object clone() throws CloneNotSupportedException {
        TableColumnConstraint clone = 
                new TableColumnConstraint(getKeyType());
        copyConstraint(this, clone);
        return clone;
    }
    
    /**
     * Copies the specified source object to the specified 
     * destination object of the same type.
     *
     * @param source the source constraint object
     * @param destination the destination constraint
     */
    protected void copyConstraint(TableColumnConstraint source, 
                        TableColumnConstraint destination) {
        destination.setKeyType(source.getKeyType());
        destination.setColumn(source.getColumn());
        destination.setReferencedCatalog(source.getReferencedCatalog());
        destination.setReferencedSchema(source.getReferencedSchema());
        destination.setReferencedTable(source.getReferencedTable());
        destination.setReferencedColumn(source.getReferencedColumn());
        destination.setName(source.getName());        
    }

    /**
     * Reverts any changes made to this constraint.
     */
    public void revert() {

        if (isMarkedDeleted()) {
            
            setMarkedDeleted(false);

        } else if (isAltered()) {
            
            copyConstraint(copy, this);
            copy = null;
        }

    }

    /**
     * Does nothing.
     */
    public int drop() throws DataSourceException {
        return 0;
    }

    /**
     * Returns the parent named object of this object.
     *
     * @return the parent object
     */
    public NamedObject getParent() {
        return getColumn();
    }

    /**
     * Detaches this constraint from the owner column
     */
    public void detachFromColumn() {
        column.removeConstraint(this);
    }
    
}










