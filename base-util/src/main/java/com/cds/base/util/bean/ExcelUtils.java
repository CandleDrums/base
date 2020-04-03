/*
 * Copyright 2013-2015 duolabao.com All Right Reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.cds.base.util.bean;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormat;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月12日 下午4:04:58
 * @version 1.0
 * @since JDK 1.8
 */
public class ExcelUtils {

    private POIFSFileSystem fs;
    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;

    private HSSFRow hdRow = null;

    /**
     * 设置工作表的格式
     * 
     * @param sheetName
     */
    public ExcelUtils() {

        wb = new HSSFWorkbook();
    }

    /**
     * 创建工作簿
     * 
     * @param sheetName
     */
    public void createSheet(String sheetName) {
        sheet = wb.createSheet(sheetName);
        hdRow = sheet.createRow(0);
        sheet.setDefaultRowHeightInPoints(20);
        sheet.setDefaultColumnWidth(12);

    }

    /**
     * 表头数据
     * 
     * @throws Exception
     */
    public void addHeader(List<String> rowvalues, boolean isFilter) throws Exception {
        // 设置字体
        HSSFFont workFont = wb.createFont();
        workFont.setFontName("宋体");
        workFont.setFontHeightInPoints((short)14);
        // workFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 表头样式及背景色

        HSSFCellStyle hdStyle = wb.createCellStyle();
        hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        hdStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        hdStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        hdStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        hdStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        hdStyle.setRightBorderColor(HSSFColor.BLACK.index);
        hdStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        hdStyle.setTopBorderColor(HSSFColor.BLACK.index);
        hdStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        hdStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        hdStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        hdStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        hdStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        hdStyle.setFont(workFont);

        String[] title = new String[rowvalues.size()];
        for (int i = 0; i < rowvalues.size(); i++) {
            title[i] = rowvalues.get(i);
        }
        HSSFRow dtRow = sheet.createRow(0);
        if (isFilter == true) {
            for (int i = 0; i < title.length; i++) {
                HSSFCell cell1 = hdRow.createCell(i);
                HSSFRichTextString value = new HSSFRichTextString(title[i]);
                cell1.setCellValue(value);
                cell1.setCellStyle(hdStyle);
            }
        } else {
            for (int i = 0; i < title.length; i++) {
                HSSFCell cell2 = dtRow.createCell(i);
                HSSFRichTextString value2 = new HSSFRichTextString(title[i]);
                cell2.setCellValue(value2);
            }
        }
    }

    /**
     * 值：List rowvalues,从第几行开始添加：int s 添加一行
     */
    public void addRow(List<Object> rowvalues, int s) {
        HSSFRow dtRow = sheet.createRow(s++);
        DataFormat format = wb.createDataFormat();

        HSSFCellStyle dtStyle = wb.createCellStyle();
        dtStyle.setDataFormat(format.getFormat("text"));
        dtStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dtStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        dtStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dtStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        dtStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dtStyle.setRightBorderColor(HSSFColor.BLACK.index);
        dtStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dtStyle.setTopBorderColor(HSSFColor.BLACK.index);
        dtStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);

        HSSFCellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));
        dateStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dateStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        dateStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dateStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        dateStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dateStyle.setRightBorderColor(HSSFColor.BLACK.index);
        dateStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dateStyle.setTopBorderColor(HSSFColor.BLACK.index);
        dateStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);

        for (int j = 0; j < rowvalues.size(); j++) {
            String flag = "";
            Object cell_data = rowvalues.get(j);
            HSSFCell cell = dtRow.createCell(j);
            // 正文格式
            if (cell_data instanceof String) {
                flag = "string";
                cell.setCellValue((String)cell_data);
            } else if (cell_data instanceof Double) {
                cell.setCellValue((Double)cell_data);
            } else if (cell_data instanceof Integer) {
                cell.setCellValue(Double.valueOf(String.valueOf(cell_data)));
            } else if (cell_data instanceof Date) {
                flag = "date";
                cell.setCellValue((Date)cell_data);
            } else if (cell_data instanceof Boolean) {
                cell.setCellValue((Boolean)cell_data);
            } else if (cell_data instanceof Float) {
                cell.setCellValue((Float)cell_data);
            }
            // 背景颜色
            // if(s%2!=0)
            // dtStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            // dtStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            if (flag == "" || flag.equals("string")) {
                cell.setCellStyle(dtStyle);
            } else if (flag.equals("date")) {
                cell.setCellStyle(dateStyle);
            }

        }
    }

    /**
     * 具体文件生成的路径
     * 
     * @param file
     * @throws Exception
     */
    public void exportExcel(String file) throws Exception {
        FileOutputStream fileOut = new FileOutputStream(file);
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * 具体文件生成的文件
     * 
     * @param file
     * @throws Exception
     */
    public void exportExcel(OutputStream outputstream) throws Exception {
        BufferedOutputStream buffout = new BufferedOutputStream(outputstream);
        wb.write(buffout);
        buffout.flush();
        buffout.close();
    }

    /**
     * 读取Excel表格表头的内容
     * 
     * @param InputStream
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        hdRow = sheet.getRow(0);
        // 标题总列数
        int colNum = hdRow.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            // title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(hdRow.getCell(i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     * 
     * @param InputStream
     * @return List<List<Object>> 包含单元格数据内容的可变数组，内部数组是每行数据
     */
    public List<Object[]> readExcelContent(InputStream is) {
        List<Object[]> content = new ArrayList<Object[]>();
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        hdRow = sheet.getRow(0);
        int colNum = hdRow.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        Object[] each = null;
        for (int i = 1; i <= rowNum; i++) {
            hdRow = sheet.getRow(i);
            int j = 0;
            each = new Object[colNum];
            for (; j < colNum; j++) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                each[j] = getStringCellValue(hdRow.getCell(j)).trim();
            }
            content.add(each);
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 根据HSSFCell类型设置数据
     * 
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();

                        // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}
