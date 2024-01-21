package my.utm.ip.ecofootprint.model;

import my.utm.ip.ecofootprint.model.DAO.UserDAO;

public class User {
	
	//DTO
    private int userId;
    private String username;
    private String password;
    private String role;
    private String email;
    private String idCard;
    private String name;
    private String contact;
    private String status;
    private String address;
    private String category;
    
    //follow attribute dlm UserDAO

    public User(){

    }

    // DB -> Repo -> DAO -> Service -> DTO -> Controller -> view
	// view -> controller -> DTO -> Service -> DAO -> Repo -> DB

	//convert DAO to DTO obj
    public User(UserDAO dao){
        this.userId = dao.getUserId();
        this.username = dao.getUsername();
        this.password = dao.getPassword();
        this.role = dao.getRole();
        this.name = dao.getName();
        //completekan method this.attribute = attribute kt based on atas
    }
    
    //convert DTO to DAO
    public UserDAO toDAO(){
		UserDAO dao = new UserDAO();
        dao.setUserId(userId);
        dao.setUsername(username);
        dao.setPassword(password);
        dao.setRole(role);
        dao.setName(name); //set kan juga utk semua attribute
		return dao;
	}

    public User(int userId, String username, String password, String role, String name) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name; //buat ni juga utk semua attribute
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}