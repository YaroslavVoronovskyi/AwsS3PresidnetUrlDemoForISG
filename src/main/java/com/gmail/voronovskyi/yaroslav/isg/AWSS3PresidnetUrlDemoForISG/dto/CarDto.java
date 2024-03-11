package com.gmail.voronovskyi.yaroslav.isg.AWSS3PresidnetUrlDemoForISG.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CarDto {

    private int id = 1;
    private String bucketName;
    private String keyName;
    private String presidnetUrl;
}
