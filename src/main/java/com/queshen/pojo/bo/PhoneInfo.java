package com.queshen.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 22:36
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneInfo {

    private String phoneNumber;

    private String purePhoneNumber;

    private Integer countryCode;

    private Object watermark;

}
