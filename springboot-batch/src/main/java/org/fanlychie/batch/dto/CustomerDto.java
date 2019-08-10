package org.fanlychie.batch.dto;

import com.github.fanlychie.excelutils.annotation.Cell;
import com.github.fanlychie.excelutils.spec.Align;
import lombok.Data;

/**
 * Created by fanlychie on 2019/7/23.
 *
 * @author fanlychie
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