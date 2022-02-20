/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javax.swing.JOptionPane;

/**
 *
 * @author user1
 */
public class AddLinkToEditor {
    HTMLEditor editor;
    public AddLinkToEditor(HTMLEditor editor){
        this.editor = editor;
        Node node = editor.lookup(".top-toolbar");
        if (node instanceof ToolBar) {
            ToolBar bar = (ToolBar) node;
            Button btn = new Button("Hyperlink");
            ImageView iv = new javafx.scene.image.ImageView(new Image(getClass().getResourceAsStream("/css/link.png")));
            btn.setMinSize(26.0, 22.0);
            btn.setMaxSize(26.0, 22.0);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            btn.setGraphic(iv);
            btn.setTooltip(new javafx.scene.control.Tooltip("Hyperlink"));
            btn.setOnAction(e->{
            WebView webView = (WebView) editor.lookup("WebView");
            String selected = (String) webView.getEngine().executeScript("window.getSelection().toString();");
            String url = JOptionPane.showInputDialog("Enter Url");
            String hyperlinkHtml = "<a href=\"" + url.trim() + "\" title=\"" + selected + "\" target=\"_blank\">" + selected + "</a>";
            webView.getEngine().executeScript(getInsertHtmlAtCurstorJS(hyperlinkHtml));
            });
            bar.getItems().add(btn);
        }
        
    }
    
    private String getInsertHtmlAtCurstorJS(String html){
        return "insertHtmlAtCursor('" + html + "');"
        + "function insertHtmlAtCursor(html) {\n"
        + " var range, node;\n"
        + " if (window.getSelection && window.getSelection().getRangeAt) {\n"
        + " window.getSelection().deleteFromDocument();\n"
        + " range = window.getSelection().getRangeAt(0);\n"
        + " node = range.createContextualFragment(html);\n"
        + " range.insertNode(node);\n"
        + " } else if (document.selection && document.selection.createRange) {\n"
        + " document.selection.createRange().pasteHTML(html);\n"
        + " document.selection.clear();"
        + " }\n"
        + "}";
    }
    
}
