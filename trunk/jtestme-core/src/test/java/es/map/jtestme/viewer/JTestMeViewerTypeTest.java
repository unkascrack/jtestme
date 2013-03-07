package es.map.jtestme.viewer;

import junit.framework.Assert;

import org.junit.Test;

public class JTestMeViewerTypeTest {

    @Test
    public void testTypeHTML() {
        Assert.assertEquals(JTestMeViewerType.toType("html"), JTestMeViewerType.HTML);
        Assert.assertEquals(JTestMeViewerType.toType("Html"), JTestMeViewerType.HTML);
        Assert.assertEquals(JTestMeViewerType.toType("HTML"), JTestMeViewerType.HTML);
    }

    @Test
    public void testTypeXML() {
        Assert.assertEquals(JTestMeViewerType.toType("xml"), JTestMeViewerType.XML);
        Assert.assertEquals(JTestMeViewerType.toType("Xml"), JTestMeViewerType.XML);
        Assert.assertEquals(JTestMeViewerType.toType("XML"), JTestMeViewerType.XML);
    }

    @Test
    public void testTypeTXT() {
        Assert.assertEquals(JTestMeViewerType.toType("txt"), JTestMeViewerType.TXT);
        Assert.assertEquals(JTestMeViewerType.toType("Txt"), JTestMeViewerType.TXT);
        Assert.assertEquals(JTestMeViewerType.toType("TXT"), JTestMeViewerType.TXT);
    }

    @Test
    public void testTypeJSON() {
        Assert.assertEquals(JTestMeViewerType.toType("json"), JTestMeViewerType.JSON);
        Assert.assertEquals(JTestMeViewerType.toType("JSon"), JTestMeViewerType.JSON);
        Assert.assertEquals(JTestMeViewerType.toType("JSON"), JTestMeViewerType.JSON);
    }

    @Test
    public void testTypeNull() {
        Assert.assertNull(JTestMeViewerType.toType(null));
    }

    @Test
    public void testTypeNotFound() {
        Assert.assertNull(JTestMeViewerType.toType("notfound"));
    }

    @Test
    public void testIsHTMLNull() {
        Assert.assertFalse(JTestMeViewerType.isHTML(null));
    }

    @Test
    public void testIsHTMLIncorrect() {
        Assert.assertFalse(JTestMeViewerType.isHTML(JTestMeViewerType.XML));
    }

    @Test
    public void testIsHTMLOk() {
        Assert.assertTrue(JTestMeViewerType.isHTML(JTestMeViewerType.HTML));
    }

    @Test
    public void testDefaultTypeNull() {
        Assert.assertNotNull(JTestMeViewerType.toType(null, JTestMeViewerType.HTML));
        Assert.assertEquals(JTestMeViewerType.toType(null, JTestMeViewerType.HTML), JTestMeViewerType.HTML);
    }

    @Test
    public void testDefaultTypeNotFound() {
        Assert.assertNotNull(JTestMeViewerType.toType("notfound", JTestMeViewerType.HTML));
        Assert.assertEquals(JTestMeViewerType.toType("notfound", JTestMeViewerType.HTML), JTestMeViewerType.HTML);
    }

    @Test
    public void testDefaultTypeFound() {
        Assert.assertNotNull(JTestMeViewerType.toType("xml", JTestMeViewerType.HTML));
        Assert.assertEquals(JTestMeViewerType.toType("xml", JTestMeViewerType.HTML), JTestMeViewerType.XML);
    }
}
