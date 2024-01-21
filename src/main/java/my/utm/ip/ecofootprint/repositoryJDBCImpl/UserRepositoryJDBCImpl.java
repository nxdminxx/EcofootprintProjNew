package my.utm.ip.ecofootprint.repositoryJDBCImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import my.utm.ip.ecofootprint.model.DAO.UserDAO;
import my.utm.ip.ecofootprint.repository.UserRepository;

public class UserRepositoryJDBCImpl implements UserRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    // Keep an in-memory list of user
    private final List<UserDAO> userList = getAllUserFromDatabase();

    private List<UserDAO> getAllUserFromDatabase() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDAO.class));
    }

    @Override
    public List<UserDAO> getAllUsers() {
        return userList;
    }

    @Override
    public UserDAO getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE userId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDAO.class), userId);
        } catch (Exception e) {
            return null; // return null if id not found
        }
    }

    @Override
    public UserDAO addUser(UserDAO user) {
        String sql = "INSERT INTO user (username, password, role, email, idCard, name, contact, status, address, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getEmail(),
                user.getIdCard(),
                user.getName(),
                user.getContact(),
                user.getStatus(),
                user.getAddress(),
                user.getCategory()
        );

        // Refresh after adding new data
        return getUserById(user.getUserId());
    }

    @Override
    public UserDAO updateUser(UserDAO user) {
        String sql = "UPDATE user SET username=?, password=?, role=?, email=?, idCard=?, name=?, contact=?, status=?, address=?, category=? WHERE userId=?";
        jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getEmail(),
                user.getIdCard(),
                user.getName(),
                user.getContact(),
                user.getStatus(),
                user.getAddress(),
                user.getCategory(),
                user.getUserId()
        );

        // Refresh after update
        return getUserById(user.getUserId());
    }

    @Override
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE userId=?";
        int affectedRows = jdbcTemplate.update(sql, userId);

        // Refresh the in-memory list after deleting the record
        if (affectedRows > 0) {
            refreshInMemoryList();
            return true;
        }

        return false;
    }

    private void refreshInMemoryList() {
        userList.clear();
        userList.addAll(getAllUserFromDatabase());
    }

    // Other methods and helper functions

    private UserDAO extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserDAO(
                resultSet.getInt("userId"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("role"),
                resultSet.getString("email"),
                resultSet.getString("idCard"),
                resultSet.getString("name"),
                resultSet.getString("contact"),
                resultSet.getString("status"),
                resultSet.getString("address"),
                resultSet.getString("category"),
                0, 0, 0
        );
    }

    @Override
    public boolean isUsernameTaken(String username) {
    String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
    return count != null && count > 0;
}

    
}
