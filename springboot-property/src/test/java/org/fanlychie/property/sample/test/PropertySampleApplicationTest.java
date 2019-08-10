package org.fanlychie.property.sample.test;

import org.fanlychie.property.sample.PropertySampleApplication;
import org.fanlychie.property.sample.bean.AdministratorsBean;
import org.fanlychie.property.sample.bean.ContactJSR303Bean;
import org.fanlychie.property.sample.bean.InformationBean;
import org.fanlychie.property.sample.bean.RandomSeedBean;
import org.fanlychie.property.sample.bean.ValueBean;
import org.fanlychie.property.sample.bean.VisitorBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by fanlychie on 2019/6/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PropertySampleApplication.class})
public class PropertySampleApplicationTest {

    @Autowired
    private VisitorBean visitorBean;

    @Autowired
    private InformationBean informationBean;

    @Autowired
    private RandomSeedBean randomSeedBean;

    @Autowired
    private AdministratorsBean administratorsBean;

    @Autowired
    private ValueBean valueBean;

    @Autowired
    private ContactJSR303Bean contactJSR303Bean;

    @Test
    public void doTest() {
        System.out.println("=====================================");
        System.out.println(visitorBean);
        System.out.println(informationBean);
        System.out.println(randomSeedBean);
        System.out.println(administratorsBean);
        System.out.println(valueBean);
        System.out.println(contactJSR303Bean);
        System.out.println("=====================================");
    }

}