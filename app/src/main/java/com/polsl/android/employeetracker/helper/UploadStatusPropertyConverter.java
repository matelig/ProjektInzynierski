package com.polsl.android.employeetracker.helper;

import org.greenrobot.greendao.converter.PropertyConverter;


public class UploadStatusPropertyConverter implements PropertyConverter<UploadStatus, Integer> {
    @Override
    public UploadStatus convertToEntityProperty(Integer databaseValue) {
        return UploadStatus.of(databaseValue);
    }

    @Override
    public Integer convertToDatabaseValue(UploadStatus entityProperty) {
        return entityProperty.getId();
    }
}