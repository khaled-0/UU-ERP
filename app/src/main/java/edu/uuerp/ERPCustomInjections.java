package edu.uuerp;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class ERPCustomInjections {

    private Context context;
    private WebView webView;
    private SharedPreferences erpData;

    private final String LOGIN_PAGE =
            "https://erp.uttarauniversity.edu.bd/SSMVC/Account/Account/LoginJSValidation";

    ERPCustomInjections(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
        erpData = context.getSharedPreferences("erpData", Context.MODE_PRIVATE);
        webView.addJavascriptInterface(this, "AndroidInterface");
    }

    public void attemptCustomInjections(WebView web, String url) {
        switch (url) {
            case LOGIN_PAGE:
                {
                    attemptAutomateLogin(web);
                    applyCustomLoginPageStyles(web);
                }
        }
    }

    private void applyCustomLoginPageStyles(WebView view) {
        StringBuilder jsBuilder = new StringBuilder("javascript:");
        jsBuilder
        
                //Hides crappy android app download link
                .append("$('p.col-lg-12.col-md-12.col-sm-12.col-xs-12')")
                .append("[0].style.display = 'none';")
                .append("\n")
                
                //Hides * from right side of input fields
                .append("$('span.field-validation-valid.text-danger')")
                .append(".each((i, obj) =>{ obj.style.display= 'none';})")
                .append("\n");

        view.evaluateJavascript(jsBuilder.toString(), null);
    }

    private void attemptAutomateLogin(WebView view) {

        String studentId = erpData.getString("studentId", "");
        String password = erpData.getString("password", "");

        // Fill saved data if avabilable
        if (!studentId.isEmpty() || !password.isEmpty()) {
            StringBuilder jsBuilder = new StringBuilder();
            jsBuilder
                    .append("javascript:")
                    .append("document.getElementById('UserName').value ='")
                    .append(studentId)
                    .append("'; document.getElementById('Password').value ='")
                    .append(password)
                    .append("';");

            view.evaluateJavascript(jsBuilder.toString(), null);
        }

        // Inject code to get login form data
        StringBuilder loginSaveJS = new StringBuilder();
        loginSaveJS
                .append("javascript:")
                .append("document.getElementById('frmLogin')")
                .append(".onclick=function(){")
                .append("var userName = document.getElementById('UserName').value;")
                .append("var password = document.getElementById('Password').value;")
                .append("AndroidInterface.saveLoginData(userName,password); }");

        view.evaluateJavascript(loginSaveJS.toString(), null);
    }

    @JavascriptInterface
    public void saveLoginData(String userName, String password) {
        erpData.edit().putString("studentId", userName.trim()).apply();
        erpData.edit().putString("password", password.trim().toString()).apply();
    }
}
