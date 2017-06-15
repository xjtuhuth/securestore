package c4e.aiu.test.securestore.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SecureStoreServiceImpl implements SecureStoreService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecureStoreService.class);

	@Autowired
	@Qualifier("secureStoreDataSource")
	private DataSource dataSource;

	@Override
	public void writeValue(String storeName, String key, String value) throws SQLException {
		LOGGER.info("secure store write value: " + storeName + " / " + key + " / " + value);

		this.deleteValue(storeName, key);

		try (Connection connection = dataSource.getConnection()) {

			CallableStatement callableStatement = connection.prepareCall("{call SYS.USER_SECURESTORE_INSERT(?,?,?,?)}");
			callableStatement.setString(1, storeName);
			callableStatement.setBoolean(2, false);
			callableStatement.setString(3, key);
			callableStatement.setBytes(4, value.getBytes());

			callableStatement.executeUpdate();
		}

	}

	@Override
	public String getValue(String storeName, String key) throws SQLException {
		LOGGER.info("secure store read value: " + storeName + " / " + key);

		try (Connection connection = dataSource.getConnection()) {

			CallableStatement callableStatement = connection
					.prepareCall("{call SYS.USER_SECURESTORE_RETRIEVE(?,?,?,?)}");
			callableStatement.setString(1, storeName);
			callableStatement.setBoolean(2, false);
			callableStatement.setString(3, key);
			callableStatement.registerOutParameter(4, Types.VARBINARY);

			callableStatement.executeUpdate();

			byte[] bytes = callableStatement.getBytes(4);

			if (null == bytes) {
				LOGGER.info("value is null");
				return null;
			} else {
				String returnValue = new String(bytes);
				LOGGER.info("return value is :" + returnValue);

				return returnValue;
			}
		}
	}

	@Override
	public void deleteValue(String storeName, String key) throws SQLException {
		LOGGER.info("secure store delete value: " + storeName + " / " + key);

		try (Connection connection = dataSource.getConnection()) {

			CallableStatement callableStatement = connection.prepareCall("{call SYS.USER_SECURESTORE_DELETE(?,?,?)}");
			callableStatement.setString(1, storeName);
			callableStatement.setBoolean(2, false);
			callableStatement.setString(3, key);

			callableStatement.executeUpdate();
		}
	}

}
