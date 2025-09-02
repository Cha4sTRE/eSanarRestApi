package cj.esanar.service;

import cj.esanar.persistence.entity.auth.PermissionsEntity;

public interface PermissionsService {

    void savePermissions(PermissionsEntity permissionsEntity);
    void updatePermissions(PermissionsEntity permissionsEntity);
    PermissionsEntity getByName(String name);


}
