package com.queshen.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winston
 * @create 2022/12/11 15:14
 * @Description: Man can conquer nature
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
