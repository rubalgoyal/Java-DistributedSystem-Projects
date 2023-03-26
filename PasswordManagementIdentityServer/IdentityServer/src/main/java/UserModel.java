import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    @JsonProperty("userId")
    private int userId;

    @JsonProperty("loginName")
    private String loginName;

    @JsonProperty("realName")
    private String realName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("oldLoginName")
    private String oldLoginName;

    @JsonProperty("createdOn")
    private String createdOn;

    @JsonProperty("deleted")
    private boolean isDeleted;
    @JsonProperty("salt")
    private byte[] salt;
    public UserModel(){}

    @JsonCreator
    public UserModel(@JsonProperty("userId") Number userId,
                    @JsonProperty("loginName") String loginName,
                    @JsonProperty("realName") String realName,
                    @JsonProperty("password") String password,
                    @JsonProperty("oldLoginName") String oldLoginName,
                    @JsonProperty("createdOn") String createdOn,
                    @JsonProperty("deleted") boolean isDeleted,
                    @JsonProperty("salt") byte[] salt){

        this.userId = userId.intValue();
        this.loginName = loginName;
        this.realName = realName;
        this.password = password;
        this.oldLoginName = oldLoginName;
        this.createdOn = createdOn;
        this.isDeleted = isDeleted;
        this.salt = salt;
    }

    @JsonProperty("deleted")
    public boolean isDeleted() {
        return isDeleted;
    }
    @JsonProperty("deleted")
    public void setDeleted(boolean  deleted) {
        this.isDeleted = deleted;
    }

    @JsonProperty("createdOn")
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty("createdOn")
    public String getCreatedOn() {
        return createdOn;
    }

    @JsonProperty("loginName")
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @JsonProperty("loginName")
    public String getLoginName() {
        return loginName;
    }

    @JsonProperty("userId")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonProperty("userId")
    public int getUserId() {
        return userId;
    }

    @JsonProperty("oldLoginName")
    public void setOldLoginName(String oldLoginName) {
        this.oldLoginName = oldLoginName;
    }

    @JsonProperty("oldLoginName")
    public String getOldLoginName() {
        return oldLoginName;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("realName")
    public void setRealName(String realName){
        this.realName = realName;
    }

    @JsonProperty("realName")
    public String getRealName(){
        return realName;
    }
    @JsonProperty("salt")
    public byte[] getSalt() {
        return salt;
    }
    @JsonProperty("salt")
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
