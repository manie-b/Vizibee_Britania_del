package com.mapolbs.vizibeebritannia.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mapolbs.vizibeebritannia.R;


public class Privacypolicy {

    public Privacypolicy(Context context){
        try {
            final Dialog dialog = new Dialog(context,
                    R.style.MyAlert);
            dialog.setContentView(R.layout.info_dialoge);
            Rect displayRectangle = new Rect();
            Activity activity =(Activity)context;
            Window window = activity.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = (int)(displayRectangle.width() * 0.5f);
            lp.height = (int)(displayRectangle.height() * 0.5f);
            lp.gravity = Gravity.CENTER;
            final ViewFlipper viewflipper = (ViewFlipper)dialog.findViewById(R.id.viewflipper);
            TextView underlinetxt = (TextView) dialog.findViewById(R.id.txtaboutus);
            TextView underlinetxtprivacypolicy = (TextView) dialog.findViewById(R.id.txtprivacypolicy);
            underlinetxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewflipper.showPrevious();
                }
            });
            underlinetxtprivacypolicy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewflipper.showNext();
                }
            });
            String udata="About Us";
            SpannableString content = new SpannableString(udata);
            content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
            underlinetxt.setText(content);
            String updata="Privacy Policy";
            SpannableString contentp = new SpannableString(updata);
            contentp.setSpan(new UnderlineSpan(), 0, updata.length(), 0);
            underlinetxtprivacypolicy.setText(contentp);
            TextView txt = (TextView) dialog.findViewById(R.id.textView9);
            txt.setMovementMethod(new ScrollingMovementMethod());
            TextView textView19 = (TextView) dialog.findViewById(R.id.textView19);
            textView19.setMovementMethod(new ScrollingMovementMethod());
            txt.setText(Html.fromHtml("<div class=\"entry-content clearfix\">\n" +
                    "<p><strong>And Marketing</strong> (<a target=\"_blank\" title=\"andmarketing\" href=\"http://andmarketing.in/\">www.andmarketing.in</a>) offers “<strong>Vizibee</strong>” app as a Commercial app. Vizibee – is a mobile app based field reporting tool used by individual users/merchandisers/promoters/field personnel for submitting their field/market/sales/availability/visibility/implementation reports and is used to monitor the field force across Pan India for various brands. And Marketing is a partnership firm incorporated in India and is committed to the privacy and security of all data. This SERVICE is provided by And Marketing and is intended for use as is. Vizibee is currently developed and maintained by Mapol Business Solutions Pvt. Ltd (<a title=\"Mapol\" target=\"_blank\" href=\"http://mapolbs.com/\">www.mapolbs.com</a>)<br>\n" +
                    "This page is used to inform website/app visitors/users regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.<br>\n" +
                    "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\n" +
                    "<p><strong>Information Collection and Use</strong></p>\n" +
                    "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request is will be retained by us and used as described in this privacy policy.<br>\n" +
                    "The app does use third party services that may collect information used to identify you.<br>\n" +
                    "Link to privacy policy of third party service providers used by the app<br>\n" +
                    "<a title=\"Google Play Services\" href=\"https://www.google.com/policies/privacy/\">Google Play Services</a></p>\n" +
                    "<p><strong>Log Data</strong></p>\n" +
                    "<p>We want to inform you that whenever you use our Service, both during regular usage or in a case of an error in the app we collect data and information on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\n" +
                    "<p><strong>Location information</strong></p>\n" +
                    "<p>We collect the IP address you use to connect to the Service, and your location information from a mobile device. This helps us allows us to show you relevant content accessible from your account and also to capture the location of visit to use it for marking the visit/site address for future use while you/other user intend to visit the same location and/or make the route/beat plans and/or attendance for the field force based on the locations visited. Location access may include: Approximate location (network-based), Precise location (GPS and network-based), Access extra location provider commands and GPS access</p>\n" +
                    "<p><strong>Camera</strong></p>\n" +
                    "<p>We use your camera only to capture the pictures/videos you voluntarily submit in the app as a proof of work completion and/or as part of your reporting norms as agreed in your employment. Any unsolicited/unethical/inappropriate pictures/videos if any taken/submitted shall be deleted and may lead to blocking of the user id. We access and sync the data/pictures/images/videos which are stored in a designated app folder during offline usage, and do not access other images/pictures stored in your device.</p>\n" +
                    "<p><strong>Storage – Photos/Media/Files</strong></p>\n" +
                    "<p>We use files or data stored on your device. Photos/Media/Files access may include the ability to: Read the contents of your USB storage (example: SD card), Modify or delete the contents of your USB storage, Format external storage and Mount or unmount external storage.</p>\n" +
                    "<p><strong>Cookies</strong></p>\n" +
                    "<p>Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your device internal memory.<br>\n" +
                    "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collection information and to improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\n" +
                    "<p><strong>Service Providers</strong></p>\n" +
                    "<p>We may employ third-party companies and individuals due to the following reasons:</p>\n" +
                    "<ul>\n" +
                    "<li>To facilitate our Service;</li>\n" +
                    "<li>To provide the Service on our behalf;</li>\n" +
                    "<li>To perform Service-related services; or</li>\n" +
                    "<li>To assist us in analyzing how our Service is used.</li>\n" +
                    "</ul>\n" +
                    "<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\n" +
                    "<p><strong>Security</strong></p>\n" +
                    "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it.<br>\n" +
                    "We only provide articles and information. We never ask for credit card numbers. We use regular Malware Scanning.<br>\n" +
                    "But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\n" +
                    "<p><strong>Links to Other Sites</strong></p>\n" +
                    "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\n" +
                    "<p><strong>Children’s Privacy</strong></p>\n" +
                    "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\n" +
                    "<p><strong>Changes to This Privacy Policy</strong></p>\n" +
                    "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page.</p>\n" +
                    "<p><strong>Contact Us</strong></p>\n" +
                    "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.<br>\n" +
                    "<strong>And Marketing</strong>, No. 272-A, Ground Floor, Dr. Selvi Jayakumar Street, Golden George Nagar, Nerkundram, Chennai – 600107. Tamil Nadu, India.<br>\n" +
                    "<strong>Email</strong>: <a title=\"andmarketing\" href=\"http://andmarketing.in/privacy-policy/info@andmarketing.in\">info@andmarketing.in</a><br>\n" +
                    "<strong>Phone</strong>: +91 44 64622444</p>\n" +
                    "  </div> <!-- end .entry-content -->"));

            textView19.setText(Html.fromHtml("<div class=\"entry-content clearfix\">\n" +
                    "<p><strong>And Marketing</strong> (<a target=\"_blank\" title=\"andmarketing\" href=\"http://andmarketing.in/\">www.andmarketing.in</a>) offers “<strong>Vizibee</strong>” app as a Commercial app. Vizibee – is a mobile app based field reporting tool used by individual users/merchandisers/promoters/field personnel for submitting their field/market/sales/availability/visibility/implementation reports and is used to monitor the field force across Pan India for various brands. And Marketing is a partnership firm incorporated in India and is committed to the privacy and security of all data. This SERVICE is provided by And Marketing and is intended for use as is. Vizibee is currently developed and maintained by Mapol Business Solutions Pvt. Ltd (<a title=\"Mapol\" target=\"_blank\" href=\"http://mapolbs.com/\">www.mapolbs.com</a>)<br>\n" +
                    "This page is used to inform website/app visitors/users regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.<br>\n" +
                    "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\n" +
                    "<p><strong>Information Collection and Use</strong></p>\n" +
                    "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request is will be retained by us and used as described in this privacy policy.<br>\n" +
                    "The app does use third party services that may collect information used to identify you.<br>\n" +
                    "Link to privacy policy of third party service providers used by the app<br>\n" +
                    "<a title=\"Google Play Services\" href=\"https://www.google.com/policies/privacy/\">Google Play Services</a></p>\n" +
                    "<p><strong>Log Data</strong></p>\n" +
                    "<p>We want to inform you that whenever you use our Service, both during regular usage or in a case of an error in the app we collect data and information on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\n" +
                    "<p><strong>Location information</strong></p>\n" +
                    "<p>We collect the IP address you use to connect to the Service, and your location information from a mobile device. This helps us allows us to show you relevant content accessible from your account and also to capture the location of visit to use it for marking the visit/site address for future use while you/other user intend to visit the same location and/or make the route/beat plans and/or attendance for the field force based on the locations visited. Location access may include: Approximate location (network-based), Precise location (GPS and network-based), Access extra location provider commands and GPS access</p>\n" +
                    "<p><strong>Camera</strong></p>\n" +
                    "<p>We use your camera only to capture the pictures/videos you voluntarily submit in the app as a proof of work completion and/or as part of your reporting norms as agreed in your employment. Any unsolicited/unethical/inappropriate pictures/videos if any taken/submitted shall be deleted and may lead to blocking of the user id. We access and sync the data/pictures/images/videos which are stored in a designated app folder during offline usage, and do not access other images/pictures stored in your device.</p>\n" +
                    "<p><strong>Storage – Photos/Media/Files</strong></p>\n" +
                    "<p>We use files or data stored on your device. Photos/Media/Files access may include the ability to: Read the contents of your USB storage (example: SD card), Modify or delete the contents of your USB storage, Format external storage and Mount or unmount external storage.</p>\n" +
                    "<p><strong>Cookies</strong></p>\n" +
                    "<p>Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your device internal memory.<br>\n" +
                    "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collection information and to improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\n" +
                    "<p><strong>Service Providers</strong></p>\n" +
                    "<p>We may employ third-party companies and individuals due to the following reasons:</p>\n" +
                    "<ul>\n" +
                    "<li>To facilitate our Service;</li>\n" +
                    "<li>To provide the Service on our behalf;</li>\n" +
                    "<li>To perform Service-related services; or</li>\n" +
                    "<li>To assist us in analyzing how our Service is used.</li>\n" +
                    "</ul>\n" +
                    "<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\n" +
                    "<p><strong>Security</strong></p>\n" +
                    "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it.<br>\n" +
                    "We only provide articles and information. We never ask for credit card numbers. We use regular Malware Scanning.<br>\n" +
                    "But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\n" +
                    "<p><strong>Links to Other Sites</strong></p>\n" +
                    "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\n" +
                    "<p><strong>Children’s Privacy</strong></p>\n" +
                    "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\n" +
                    "<p><strong>Changes to This Privacy Policy</strong></p>\n" +
                    "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page.</p>\n" +
                    "<p><strong>Contact Us</strong></p>\n" +
                    "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.<br>\n" +
                    "<strong>And Marketing</strong>, No. 272-A, Ground Floor, Dr. Selvi Jayakumar Street, Golden George Nagar, Nerkundram, Chennai – 600107. Tamil Nadu, India.<br>\n" +
                    "<strong>Email</strong>: <a title=\"andmarketing\" href=\"http://andmarketing.in/privacy-policy/info@andmarketing.in\">info@andmarketing.in</a><br>\n" +
                    "<strong>Phone</strong>: +91 44 64622444</p>\n" +
                    "  </div> <!-- end .entry-content -->"));



            dialog.show();


        } catch (Exception ex) {
            Log.e("Info", ex.getMessage().toString());
        }
    }

  /* try {
                        final Dialog dialog = new Dialog(DefaultForm.this,
                                R.style.MyAlert);
                        dialog.setContentView(R.layout.info_dialoge);
                        Rect displayRectangle = new Rect();
                        Window window = DefaultForm.this.getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = (int)(displayRectangle.width() * 0.5f);
                        lp.height = (int)(displayRectangle.height() * 0.5f);
                        lp.gravity = Gravity.CENTER;
                        final ViewFlipper viewflipper = (ViewFlipper)dialog.findViewById(R.id.viewflipper);
                        TextView underlinetxt = (TextView) dialog.findViewById(R.id.txtaboutus);
                        TextView underlinetxtprivacypolicy = (TextView) dialog.findViewById(R.id.txtprivacypolicy);
                        underlinetxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewflipper.showPrevious();
                            }
                        });
                        underlinetxtprivacypolicy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewflipper.showNext();
                            }
                        });
                        String udata="About Us";
                        SpannableString content = new SpannableString(udata);
                        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
                        underlinetxt.setText(content);
                        String updata="Privacy Policy";
                        SpannableString contentp = new SpannableString(updata);
                        contentp.setSpan(new UnderlineSpan(), 0, updata.length(), 0);
                        underlinetxtprivacypolicy.setText(contentp);
                        TextView txt = (TextView) dialog.findViewById(R.id.textView9);
                        txt.setMovementMethod(new ScrollingMovementMethod());
                        TextView textView19 = (TextView) dialog.findViewById(R.id.textView19);
                        textView19.setMovementMethod(new ScrollingMovementMethod());
                        txt.setText(Html.fromHtml("<div class=\"entry-content clearfix\">\n" +
                                "<p><strong>And Marketing</strong> (<a target=\"_blank\" title=\"andmarketing\" href=\"http://andmarketing.in/\">www.andmarketing.in</a>) offers “<strong>Vizibee</strong>” app as a Commercial app. Vizibee – is a mobile app based field reporting tool used by individual users/merchandisers/promoters/field personnel for submitting their field/market/sales/availability/visibility/implementation reports and is used to monitor the field force across Pan India for various brands. And Marketing is a partnership firm incorporated in India and is committed to the privacy and security of all data. This SERVICE is provided by And Marketing and is intended for use as is. Vizibee is currently developed and maintained by Mapol Business Solutions Pvt. Ltd (<a title=\"Mapol\" target=\"_blank\" href=\"http://mapolbs.com/\">www.mapolbs.com</a>)<br>\n" +
                                "This page is used to inform website/app visitors/users regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.<br>\n" +
                                "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\n" +
                                "<p><strong>Information Collection and Use</strong></p>\n" +
                                "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request is will be retained by us and used as described in this privacy policy.<br>\n" +
                                "The app does use third party services that may collect information used to identify you.<br>\n" +
                                "Link to privacy policy of third party service providers used by the app<br>\n" +
                                "<a title=\"Google Play Services\" href=\"https://www.google.com/policies/privacy/\">Google Play Services</a></p>\n" +
                                "<p><strong>Log Data</strong></p>\n" +
                                "<p>We want to inform you that whenever you use our Service, both during regular usage or in a case of an error in the app we collect data and information on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\n" +
                                "<p><strong>Location information</strong></p>\n" +
                                "<p>We collect the IP address you use to connect to the Service, and your location information from a mobile device. This helps us allows us to show you relevant content accessible from your account and also to capture the location of visit to use it for marking the visit/site address for future use while you/other user intend to visit the same location and/or make the route/beat plans and/or attendance for the field force based on the locations visited. Location access may include: Approximate location (network-based), Precise location (GPS and network-based), Access extra location provider commands and GPS access</p>\n" +
                                "<p><strong>Camera</strong></p>\n" +
                                "<p>We use your camera only to capture the pictures/videos you voluntarily submit in the app as a proof of work completion and/or as part of your reporting norms as agreed in your employment. Any unsolicited/unethical/inappropriate pictures/videos if any taken/submitted shall be deleted and may lead to blocking of the user id. We access and sync the data/pictures/images/videos which are stored in a designated app folder during offline usage, and do not access other images/pictures stored in your device.</p>\n" +
                                "<p><strong>Storage – Photos/Media/Files</strong></p>\n" +
                                "<p>We use files or data stored on your device. Photos/Media/Files access may include the ability to: Read the contents of your USB storage (example: SD card), Modify or delete the contents of your USB storage, Format external storage and Mount or unmount external storage.</p>\n" +
                                "<p><strong>Cookies</strong></p>\n" +
                                "<p>Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your device internal memory.<br>\n" +
                                "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collection information and to improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\n" +
                                "<p><strong>Service Providers</strong></p>\n" +
                                "<p>We may employ third-party companies and individuals due to the following reasons:</p>\n" +
                                "<ul>\n" +
                                "<li>To facilitate our Service;</li>\n" +
                                "<li>To provide the Service on our behalf;</li>\n" +
                                "<li>To perform Service-related services; or</li>\n" +
                                "<li>To assist us in analyzing how our Service is used.</li>\n" +
                                "</ul>\n" +
                                "<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\n" +
                                "<p><strong>Security</strong></p>\n" +
                                "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it.<br>\n" +
                                "We only provide articles and information. We never ask for credit card numbers. We use regular Malware Scanning.<br>\n" +
                                "But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\n" +
                                "<p><strong>Links to Other Sites</strong></p>\n" +
                                "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\n" +
                                "<p><strong>Children’s Privacy</strong></p>\n" +
                                "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\n" +
                                "<p><strong>Changes to This Privacy Policy</strong></p>\n" +
                                "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page.</p>\n" +
                                "<p><strong>Contact Us</strong></p>\n" +
                                "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.<br>\n" +
                                "<strong>And Marketing</strong>, No. 272-A, Ground Floor, Dr. Selvi Jayakumar Street, Golden George Nagar, Nerkundram, Chennai – 600107. Tamil Nadu, India.<br>\n" +
                                "<strong>Email</strong>: <a title=\"andmarketing\" href=\"http://andmarketing.in/privacy-policy/info@andmarketing.in\">info@andmarketing.in</a><br>\n" +
                                "<strong>Phone</strong>: +91 44 64622444</p>\n" +
                                "  </div> <!-- end .entry-content -->"));

                        textView19.setText(Html.fromHtml("<div class=\"entry-content clearfix\">\n" +
                                "<p><strong>And Marketing</strong> (<a target=\"_blank\" title=\"andmarketing\" href=\"http://andmarketing.in/\">www.andmarketing.in</a>) offers “<strong>Vizibee</strong>” app as a Commercial app. Vizibee – is a mobile app based field reporting tool used by individual users/merchandisers/promoters/field personnel for submitting their field/market/sales/availability/visibility/implementation reports and is used to monitor the field force across Pan India for various brands. And Marketing is a partnership firm incorporated in India and is committed to the privacy and security of all data. This SERVICE is provided by And Marketing and is intended for use as is. Vizibee is currently developed and maintained by Mapol Business Solutions Pvt. Ltd (<a title=\"Mapol\" target=\"_blank\" href=\"http://mapolbs.com/\">www.mapolbs.com</a>)<br>\n" +
                            "This page is used to inform website/app visitors/users regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.<br>\n" +
                            "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\n" +
                            "<p><strong>Information Collection and Use</strong></p>\n" +
                            "<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request is will be retained by us and used as described in this privacy policy.<br>\n" +
                            "The app does use third party services that may collect information used to identify you.<br>\n" +
                            "Link to privacy policy of third party service providers used by the app<br>\n" +
                            "<a title=\"Google Play Services\" href=\"https://www.google.com/policies/privacy/\">Google Play Services</a></p>\n" +
                            "<p><strong>Log Data</strong></p>\n" +
                            "<p>We want to inform you that whenever you use our Service, both during regular usage or in a case of an error in the app we collect data and information on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\n" +
                            "<p><strong>Location information</strong></p>\n" +
                            "<p>We collect the IP address you use to connect to the Service, and your location information from a mobile device. This helps us allows us to show you relevant content accessible from your account and also to capture the location of visit to use it for marking the visit/site address for future use while you/other user intend to visit the same location and/or make the route/beat plans and/or attendance for the field force based on the locations visited. Location access may include: Approximate location (network-based), Precise location (GPS and network-based), Access extra location provider commands and GPS access</p>\n" +
                            "<p><strong>Camera</strong></p>\n" +
                            "<p>We use your camera only to capture the pictures/videos you voluntarily submit in the app as a proof of work completion and/or as part of your reporting norms as agreed in your employment. Any unsolicited/unethical/inappropriate pictures/videos if any taken/submitted shall be deleted and may lead to blocking of the user id. We access and sync the data/pictures/images/videos which are stored in a designated app folder during offline usage, and do not access other images/pictures stored in your device.</p>\n" +
                            "<p><strong>Storage – Photos/Media/Files</strong></p>\n" +
                            "<p>We use files or data stored on your device. Photos/Media/Files access may include the ability to: Read the contents of your USB storage (example: SD card), Modify or delete the contents of your USB storage, Format external storage and Mount or unmount external storage.</p>\n" +
                            "<p><strong>Cookies</strong></p>\n" +
                            "<p>Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your device internal memory.<br>\n" +
                            "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collection information and to improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\n" +
                            "<p><strong>Service Providers</strong></p>\n" +
                            "<p>We may employ third-party companies and individuals due to the following reasons:</p>\n" +
                            "<ul>\n" +
                            "<li>To facilitate our Service;</li>\n" +
                            "<li>To provide the Service on our behalf;</li>\n" +
                            "<li>To perform Service-related services; or</li>\n" +
                            "<li>To assist us in analyzing how our Service is used.</li>\n" +
                            "</ul>\n" +
                            "<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\n" +
                            "<p><strong>Security</strong></p>\n" +
                            "<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it.<br>\n" +
                            "We only provide articles and information. We never ask for credit card numbers. We use regular Malware Scanning.<br>\n" +
                            "But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\n" +
                            "<p><strong>Links to Other Sites</strong></p>\n" +
                            "<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\n" +
                            "<p><strong>Children’s Privacy</strong></p>\n" +
                            "<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\n" +
                            "<p><strong>Changes to This Privacy Policy</strong></p>\n" +
                            "<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page.</p>\n" +
                            "<p><strong>Contact Us</strong></p>\n" +
                            "<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.<br>\n" +
                            "<strong>And Marketing</strong>, No. 272-A, Ground Floor, Dr. Selvi Jayakumar Street, Golden George Nagar, Nerkundram, Chennai – 600107. Tamil Nadu, India.<br>\n" +
                            "<strong>Email</strong>: <a title=\"andmarketing\" href=\"http://andmarketing.in/privacy-policy/info@andmarketing.in\">info@andmarketing.in</a><br>\n" +
                            "<strong>Phone</strong>: +91 44 64622444</p>\n" +
                            "  </div> <!-- end .entry-content -->"));



                    dialog.show();


                } catch (Exception ex) {
                    Log.e("Info", ex.getMessage().toString());
                }*/
}
