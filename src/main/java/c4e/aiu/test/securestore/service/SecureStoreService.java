package c4e.aiu.test.securestore.service;

import java.sql.SQLException;

public interface SecureStoreService {

	void writeValue(String storeName, String key, String value) throws SQLException;

	String getValue(String string, String string2) throws SQLException;

	void deleteValue(String string, String string2) throws SQLException;

}
