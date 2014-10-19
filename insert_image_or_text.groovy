//@ExecutionModes({ON_SELECTED_NODE})
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.UUID;
import java.net.URI;

import java.nio.file.*;

import org.freeplane.plugin.script.proxy.Proxy.Node;

//References:-
//1. freeplane\plugin\script\proxy\NodeProxy.java
//2. freeplane\plugin\script\proxy\Proxy.java
//3. freeplane_plugin_script\src\org\freeplane\plugin\script\ScriptingConfiguration.java


Toolkit tool = Toolkit.getDefaultToolkit();
Clipboard clipboard = tool.getSystemClipboard();

try {
    if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
        BufferedImage image = clipboard.getData(DataFlavor.imageFlavor); 
        Node child = node.createChild();
        UUID uuid = UUID.randomUUID();
        String parentFolder = node.getMap().getFile().getParent();
        String fileName = uuid.toString() + ".png";
        String filePath = parentFolder + "/images/" + fileName;
        child.text = fileName;
        File outputfile = new File(filePath);
        if(!outputfile.getParentFile().exists()) {
            outputfile.getParentFile().mkdir();
        }
        ImageIO.write(image, "png", outputfile);
        URI uri = outputfile.toURI();
        child.externalObject.setURI(uri.toString());
    } else if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
        String data = (String) clipboard.getData(DataFlavor.stringFlavor);
        Node child = node.createChild();
        child.text = data;
    }
} catch(Exception e) {
    logger.warn("Exception caught: ", e);
}