package com.example.demo.validate;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UnusedValidator.class) 
@Target({FIELD}) // 項目に対してバリデーションをかける場合はFIELDを選ぶ
@Retention(RUNTIME)
public @interface Unused {
	
	String message() default "すでに登録済みのメールアドレスです"; // エラーメッセージ
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    @Target({FIELD})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Unused[] value(); // インターフェース名[] value()とする
    }

}
