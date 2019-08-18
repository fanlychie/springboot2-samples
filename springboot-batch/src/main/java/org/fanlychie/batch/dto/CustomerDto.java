package org.fanlychie.batch.dto;

import com.github.fanlychie.excelutils.annotation.Cell;
import com.github.fanlychie.excelutils.spec.Align;
import lombok.Data;

/**
 * 客户实例DTO
 *
 * @author fanlychie
 * @since 2019/8/18
 */
@Data
public class CustomerDto {

    @Cell(index = 0, name = "姓名", align = Align.CENTER)
    private String name;

    @Cell(index = 1, name = "电话", align = Align.CENTER)
    private String mobile;

    @Cell(index = 2, name = "年龄", align = Align.CENTER)
    private int age;

}