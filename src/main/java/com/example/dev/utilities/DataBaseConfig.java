package com.example.dev.utilities;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataBaseConfig {
	
	public static BasicDataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3307/ivancastfg");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return dataSource;
    }
}
