import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class IdentityUtil {
    private static final int JEDIS_PORT = 6379;
    private static final String ADDRESS = "localhost" ;
    private static final String MAX_USER_ID = "maxUserId";

    private static  final String ALGORITHM = "SHA-256";


    private static byte[] createSalt(){
        byte[] bytes = new byte[20];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }

    public static String generateHash(String password, UserModel user) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        messageDigest.reset();
        byte[] salt = user.getSalt() == null ? createSalt() : user.getSalt();
        if(user.getSalt() == null)
            user.setSalt(salt);
        messageDigest.update(salt);
        byte[] hash = messageDigest.digest(password.getBytes());
        return bytesTOStringHex(hash);
    }

    private static String bytesTOStringHex(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        String hex = bigInteger.toString(16);

        while(hex.length() < 32){
            hex = "0" + hex;
        }
        return hex;
    }

    public static boolean isPasswordMatch(UserModel nuser, String password){
        try {
            String passHash = generateHash(password, nuser);
            if(nuser.getPassword().equals(passHash)) {
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public static ConcurrentHashMap<String, UserModel> fetchAllUsers(){
        Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
        Set<String> keys = jedis.keys("*");

        ConcurrentHashMap<String, UserModel> users = new ConcurrentHashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try{
            for(String key : keys){
                if (!key.equals(MAX_USER_ID)){
                    UserModel user = mapper.readValue(jedis.get(key),UserModel.class);
                    users.put(key, user);
                }
            }
        }
        catch(JedisConnectionException e){
            System.out.println(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally{
            jedis.close();
        }
        return users;
    }

    public static int getLastUserId(){
        /**
         * get maximum user id from redis database
         * returns 1 if there is no users
         */
        Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
        int userId = 1;
        try{
            String maxUserId = jedis.get(MAX_USER_ID);
            if(maxUserId != null)
                userId = Integer.parseInt(maxUserId);
        }
        catch(JedisConnectionException e){
            System.out.println(e);
        }
        finally {
            jedis.close();
        }
        return userId;
    }

    public static void bulkInsert(ConcurrentHashMap<String, UserModel> inSessionUsers, boolean isNewUser){

        try {
            Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
            try {
                ObjectMapper mapper = new ObjectMapper();
                inSessionUsers.forEach((key, value) -> {
                            try {
                                String valueJson = mapper.writeValueAsString(value);
                                jedis.set(key, valueJson);
                                // Increase the max user id key in the db
                                if(isNewUser)
                                    jedis.set(MAX_USER_ID, String.valueOf(value.getUserId() + 1));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
            } finally {
                jedis.bgsave();
                jedis.close();
            }
        } catch (JedisConnectionException e) {
            System.out.println(e);
        }
    }

    public static void insertUser(UserModel user, String userId, boolean isNewUser){
        ConcurrentHashMap<String, UserModel> insert = new ConcurrentHashMap<>();
        if (user.getUserId() == 0) {
            int nextId = getLastUserId();
            user.setUserId(nextId);
            userId = String.valueOf(nextId);
        }
        insert.put(userId, user);
        bulkInsert(insert, isNewUser);
    }

    public static boolean isUserExist(int userId){
        Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
        String userKey = String.valueOf(userId);
        try{
            String users = jedis.get(userKey);
            if(users != null)
                return true;
        }
        catch(JedisConnectionException e){
            System.out.println(e);
        }
        finally {
            jedis.close();
        }
        return false;
    }

    public static boolean isUserExist(String loginName) {
        Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Set<String> keys = jedis.keys("*");
            if(keys.isEmpty())
                return false;
            for (String key : keys) {
                if (!key.equals(MAX_USER_ID)) {
                    String value = jedis.get(key);
                    UserModel user = mapper.readValue(value, UserModel.class);
                    if (user.getLoginName().equals(loginName))
                        return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            jedis.close();
        }
        return false;
    }

    public static UserModel returnUser(String loginName){
        Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
        ObjectMapper mapper = new ObjectMapper();
        try{
            Set<String> keys = jedis.keys("*");
            if(keys.isEmpty())
                return null;
            for (String key : keys) {
                if (!key.equals(MAX_USER_ID)) {
                    String value = jedis.get(key);
                    UserModel user = mapper.readValue(value, UserModel.class);
                    if (user.getLoginName().equals(loginName))
                        return user;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        } finally {
            jedis.close();
        }
        return null;
    }

    public static boolean deleteUser(String loginName){
        UserModel user = returnUser(loginName);
        if( user != null && !user.isDeleted()){
            Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
            if ( jedis.del(String.valueOf(user.getUserId())) == 1){
                return true;
            }
        }
        return false;
    }

    public static UserModel returnUser(int userId) {
        Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Set<String> keys = jedis.keys("*");
            if (keys.isEmpty())
                return null;
            for (String key : keys) {
                if (!key.equals(MAX_USER_ID)) {
                    String value = jedis.get(key);
                    UserModel user = mapper.readValue(value, UserModel.class);
                    if (user.getUserId() == userId)
                        return user;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            jedis.close();
        }
        return null;
    }

}
