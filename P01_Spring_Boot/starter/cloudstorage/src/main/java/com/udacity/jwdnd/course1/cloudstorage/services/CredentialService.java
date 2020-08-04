package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsModel;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


@Service
public class CredentialService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public boolean credentialExist(String username){
        if(credentialsMapper.getCredential(username) != null){
            return true;
        }
        return false;
    }

    public void saveCredential(CredentialsModel credentialsModel, Integer userId){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedkey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credentialsModel.getPassword(), encodedkey);

        credentialsMapper.saveCredential(new CredentialsModel(null,
                                        credentialsModel.getUrl(),
                                        credentialsModel.getUsername(),
                                        encodedkey,
                                        encryptedPassword,
                                        userId));
    }

    public List<CredentialsModel> getCredentialByUserId(Integer userId){ return credentialsMapper.getAllCredentials(userId);}

    public CredentialsModel getCredentialById(Integer credentialId){

        CredentialsModel credentialsModel = credentialsMapper.getCredentialById(credentialId);
        String plainPassword = encryptionService.decryptValue(credentialsModel.getPassword(),
                                                              credentialsModel.getKey());
        credentialsModel.setPassword(plainPassword);

        return credentialsModel;
    }

    public void deleteCredential(Integer credentialId){ credentialsMapper.deleteCredentialById(credentialId);}

    public String getCredentialUsername(Integer credentialId) { return credentialsMapper.getCredentialName(credentialId);}

    public void editCredentials(CredentialsModel credentialsModel){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedkey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credentialsModel.getPassword(), encodedkey);
        credentialsModel.setPassword(encryptedPassword);
        credentialsMapper.updateCredentials(credentialsModel);
    }

    public CredentialsModel decryptPassword(Integer credentialId){
        CredentialsModel credentialsModel = credentialsMapper.getCredentialById(credentialId);
        String plainPassword = encryptionService.decryptValue(credentialsModel.getPassword(), credentialsModel.getKey());
        credentialsModel.setPassword(plainPassword);

        return credentialsModel;
    }

}
