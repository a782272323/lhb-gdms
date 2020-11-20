package lhb.gdms.commons.utils;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;

/**
 * @Description  富文本转纯文本
 * @author Herbie Leung
 * @date 2020/7/11
 * @time 14:24
 */
public class HtmlToTextUtils extends HTMLEditorKit.ParserCallback {

    private static HtmlToTextUtils htmlToTextUtils = new HtmlToTextUtils();

    StringBuffer s;
    public void parse(String str) throws IOException {

        InputStream iin = new ByteArrayInputStream(str.getBytes());
        Reader in = new InputStreamReader(iin);
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        iin.close();
        in.close();
    }

    @Override
    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public String getText() {
        return s.toString();
    }

    public static String getContent(String str) {
        try {
            htmlToTextUtils.parse(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htmlToTextUtils.getText();
    }
}