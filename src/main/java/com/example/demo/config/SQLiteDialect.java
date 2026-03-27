package com.example.demo.config;

import java.sql.Types;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.type.StandardBasicTypes;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl();
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

//    @Override
    public String getTypeName(int code) {
        switch (code) {
            case Types.BIT:
            case Types.BOOLEAN:
                return "boolean";
            case Types.TINYINT:
                return "tinyint";
            case Types.SMALLINT:
                return "smallint";
            case Types.INTEGER:
                return "integer";
            case Types.BIGINT:
                return "bigint";
            case Types.FLOAT:
                return "float";
            case Types.DOUBLE:
                return "double";
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                return "text";
            default:
                return "text";
        }
    }
}