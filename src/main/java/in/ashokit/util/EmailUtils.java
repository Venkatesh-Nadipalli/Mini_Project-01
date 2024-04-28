package in.ashokit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailsender;
	
	public boolean sendEmail(String sub,String body,String to) {
		boolean isSent = false;
		
		try {
			
			MimeMessage mimeMsg = mailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
			helper.setSubject(sub);
			helper.setText(body, true);
			helper.setTo(to);
			mailsender.send(mimeMsg);
			
			isSent = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isSent;
	}
}
