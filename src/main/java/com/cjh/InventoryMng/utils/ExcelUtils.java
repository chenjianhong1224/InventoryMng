package com.cjh.InventoryMng.utils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.cjh.InventoryMng.annotation.ExcelAnnotation;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;

public class ExcelUtils {

	public static byte[] createExcelContent(List<?> beans, Class z) throws Exception {
		SXSSFWorkbook wb = null;
		ByteArrayOutputStream fos = null;
		if (CollectionUtils.isEmpty(beans)) {
			return null;
		} else {
			Field[] fields = z.getDeclaredFields();
			Field.setAccessible(fields, true);
			Multimap<Integer, Field> multimap = ArrayListMultimap.create();
			List<Integer> colOrder = Lists.newArrayList();
			for (int i = 0; i < fields.length; i++) {
				ExcelAnnotation excelTitleAnnotation = fields[i].getAnnotation(ExcelAnnotation.class);
				if (excelTitleAnnotation != null) {
					multimap.put(excelTitleAnnotation.Order(), fields[i]);
				}
			}
			colOrder.addAll(multimap.keySet());
			Ordering<Integer> natural = Ordering.natural();
			colOrder = natural.sortedCopy(colOrder);
			try {
				wb = new SXSSFWorkbook();
				SXSSFSheet sheet = wb.createSheet();
				SXSSFRow row = sheet.createRow(0);// 第一行
				int colNum = 0;
				for (Integer i : colOrder) {
					Collection<Field> values = multimap.get(i);
					for (Field val : values) {
						row.createCell(colNum).setCellValue(val.getAnnotation(ExcelAnnotation.class).TitleName());
						colNum++;
					}
				}
				colNum = 0;
				int rowNum = 1;
				for (Object bean : beans) {
					SXSSFRow rowTmp = sheet.createRow(rowNum);
					for (Integer i : colOrder) {
						Collection<Field> values = multimap.get(i);
						for (Field val : values) {
							String v = val.get(bean) == null ? null : val.get(bean).toString();
							rowTmp.createCell(colNum).setCellValue(v);
							colNum++;
						}
					}
					colNum = 0;
					rowNum++;
				}
				fos = new ByteArrayOutputStream();
				wb.write(fos);
				byte[] buf = fos.toByteArray();// 获取内存缓冲区中的数据
				fos.close();
				wb.close();
				return buf;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (fos != null) {
					fos.close();
				}
				if (wb != null) {
					wb.close();
				}
			}
		}
	}
}
