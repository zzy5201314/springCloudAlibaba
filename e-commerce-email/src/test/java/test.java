import com.imooc.ecommerce.EmailApplication;
import com.imooc.ecommerce.service.IEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author zzy
 * @date 2022/4/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmailApplication.class)
public class test {

    private String receptionMailAddr = "1971166454@qq.com";

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 测试 文本邮件
     * @throws Exception
     */
    @Test
    public void testSimpleMail() throws Exception {
        iEmailService.sendTextEmail(receptionMailAddr,"测试文本邮箱发送","你好你好！");
    }

    /**
     * 测试 html 邮箱
     * @throws Exception
     */
    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        iEmailService.sendHtmlEmail(receptionMailAddr,"simple mail",content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath="D:\\code\\项目图片\\123.txt";
        iEmailService.sendAttachmentsEmail(receptionMailAddr, "主题：带附件的邮件", "有附件，请查收！", filePath);
    }


    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "D:\\code\\项目图片\\索隆.jpg";

        iEmailService.sendInlineResourceEmail(receptionMailAddr, "主题：这是有图片的邮件", content, imgPath, rscId);
    }


    @Test
    public void sendTemplateMail() {
        // 创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");

        // 传递 emailTemplate.html 模板需要的值，并将模板转换为 String
        String emailContent = templateEngine.process("emailTemplates", context);

        iEmailService.sendHtmlEmail(receptionMailAddr,"主题：这是模板邮件",emailContent);
    }

}


