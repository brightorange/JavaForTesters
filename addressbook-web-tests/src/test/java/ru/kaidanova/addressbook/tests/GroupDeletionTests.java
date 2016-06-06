package ru.kaidanova.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by i.loputneva on 2016-06-03.
 */
public class GroupDeletionTests extends TestBase{

    @Test
    public void testGroupDeletion() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().deleteGroup();
        app.getGroupHelper().returnToGroupPage();



    }

}
