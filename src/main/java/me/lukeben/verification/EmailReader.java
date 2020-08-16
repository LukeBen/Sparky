package me.lukeben.verification;

import lombok.Getter;
import me.lukeben.Conf;
import me.lukeben.verification.utils.PurchasedResource;
import me.lukeben.verification.utils.ResourceType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.*;

@Getter
public class EmailReader {

    private final Folder folder;

    public EmailReader() {
        this.folder = setupFolder();
    }

    public Folder setupFolder() {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(Conf.VERIFICATION.HOST, Conf.VERIFICATION.EMAIL, Conf.VERIFICATION.PASSWORD);
            Folder inbox = store.getFolder(Conf.VERIFICATION.FOLDER_TO_SCAN);
            inbox.open(Folder.READ_ONLY);
            return inbox;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void scanEmails() {
        try {
            for(Message message: folder.getMessages()) {
                try {

                    if(Conf.VERIFICATION.LAST_SCANNED_DATE != null && message.getReceivedDate().before(Conf.VERIFICATION.LAST_SCANNED_DATE)) continue;
                    InternetAddress address = (InternetAddress) message.getFrom()[0];
                    if(!address.getAddress().equalsIgnoreCase("service@paypal.com")) continue;

                    String rawMessage = getText(message);
                    Document document = Jsoup.parse(rawMessage);
                    document.outputSettings(new Document.OutputSettings().prettyPrint(false));
                    String body = document.text();
                    String[] words = body.split(" ");

                    String email = Arrays.stream(words).filter(w -> w != null && w.contains("@") && w.startsWith("(") && w.endsWith(")")).findAny().get();
                    email = email.substring(1, email.length() - 1);

                    ResourceType type = null;
                    if(Arrays.stream(words).filter(w -> w.contains("ImmortalTags") || w.contains("Tags")).findAny().isPresent()) type = ResourceType.IMMORTAL_TAGS;

                    PurchasedResource resource = new PurchasedResource(message.getReceivedDate(), type);
                    HashMap<String, List<PurchasedResource>> pendingVerification = VerificationManager.getInstance().getPendingVerification();
                    List<PurchasedResource> purchasedResourceList = pendingVerification.containsKey(email) ? pendingVerification.get(email) : new ArrayList<>();
                    purchasedResourceList.add(resource);

                    VerificationManager.getInstance().getPendingVerification().put(email, purchasedResourceList);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("No Information");
                }
                BuyerData.getInstance().save();
                Conf.VERIFICATION.LAST_SCANNED_DATE = new Date(System.currentTimeMillis());
                Conf.getInstance().save();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) return (String) p.getContent();

        if (p.isMimeType("multipart/alternative")) {
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null) text = getText(bp);continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null) return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) return s;
            }
        }
        return null;
    }

}
