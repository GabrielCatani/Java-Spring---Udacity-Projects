package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    CredentialsModel getCredential(String username);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid)" +
            " VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    void saveCredential(CredentialsModel credentialsModel);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<CredentialsModel> getAllCredentials(Integer userId);

    @Select("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void    deleteCredentialById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    CredentialsModel getCredentialById(Integer credentialId);

    @Select("SELECT username FROM CREDENTIALS WHERE username = #{credentialId}")
    String getCredentialName(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialId = #{credentialId}")
    void    updateCredentials(CredentialsModel credentialsModel);

}
