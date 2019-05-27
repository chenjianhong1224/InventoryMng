package com.cjh.InventoryMng.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnnotation {
	//1
	String TitleName() default "";
	//2
	int Order() default 0;
}
