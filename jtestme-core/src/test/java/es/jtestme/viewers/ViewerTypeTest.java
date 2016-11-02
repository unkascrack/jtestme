package es.jtestme.viewers;

import org.junit.Assert;
import org.junit.Test;

public class ViewerTypeTest {

    @Test
    public void testTypeHTML() {
        Assert.assertEquals(ViewerType.toType("html"), ViewerType.HTML);
        Assert.assertEquals(ViewerType.toType("Html"), ViewerType.HTML);
        Assert.assertEquals(ViewerType.toType("HTML"), ViewerType.HTML);
    }

    @Test
    public void testTypeXML() {
        Assert.assertEquals(ViewerType.toType("xml"), ViewerType.XML);
        Assert.assertEquals(ViewerType.toType("Xml"), ViewerType.XML);
        Assert.assertEquals(ViewerType.toType("XML"), ViewerType.XML);
    }

    @Test
    public void testTypeTXT() {
        Assert.assertEquals(ViewerType.toType("txt"), ViewerType.TXT);
        Assert.assertEquals(ViewerType.toType("Txt"), ViewerType.TXT);
        Assert.assertEquals(ViewerType.toType("TXT"), ViewerType.TXT);
    }

    @Test
    public void testTypeJSON() {
        Assert.assertEquals(ViewerType.toType("json"), ViewerType.JSON);
        Assert.assertEquals(ViewerType.toType("JSon"), ViewerType.JSON);
        Assert.assertEquals(ViewerType.toType("JSON"), ViewerType.JSON);
    }

    @Test
    public void testTypeNull() {
        Assert.assertNull(ViewerType.toType(null));
    }

    @Test
    public void testTypeNotFound() {
        Assert.assertNull(ViewerType.toType("notfound"));
    }

    @Test
    public void testIsHTMLNull() {
        Assert.assertFalse(ViewerType.isHTML(null));
    }

    @Test
    public void testIsHTMLIncorrect() {
        Assert.assertFalse(ViewerType.isHTML(ViewerType.XML));
    }

    @Test
    public void testIsHTMLOk() {
        Assert.assertTrue(ViewerType.isHTML(ViewerType.HTML));
    }

    @Test
    public void testDefaultTypeNull() {
        Assert.assertNotNull(ViewerType.toType(null, ViewerType.HTML));
        Assert.assertEquals(ViewerType.toType(null, ViewerType.HTML), ViewerType.HTML);
    }

    @Test
    public void testDefaultTypeNotFound() {
        Assert.assertNotNull(ViewerType.toType("notfound", ViewerType.HTML));
        Assert.assertEquals(ViewerType.toType("notfound", ViewerType.HTML), ViewerType.HTML);
    }

    @Test
    public void testDefaultTypeFound() {
        Assert.assertNotNull(ViewerType.toType("xml", ViewerType.HTML));
        Assert.assertEquals(ViewerType.toType("xml", ViewerType.HTML), ViewerType.XML);
    }
}
