package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import org.TextImage;
import ip.imagegenerator.Margin;
import ip.imagegenerator.imagecallbacks.BackgroundColorCallback;
import ip.imagegenerator.imageexporter.ImageType;
import ip.imagegenerator.imageexporter.ImageWriter;
import ip.imagegenerator.imageexporter.ImageWriterFactory;
import ip.imagegenerator.impl.TextImageImpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;



public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render());
    }
    
    public static Result generate(String size) throws IOException {
    	
    	response().setContentType("image/png");
    	//Color.WHITE is #FFFFFF
		return generatecolor(size, "FFFFFF");
	}
    
    
    public static Result generatecolor(String size, String color) throws IOException {
    	String dircolor = "";
    	Boolean isgray = false;
    	// Image size is checked by the regex in routes file
    	int width = Integer.parseInt(size.substring(0, size.indexOf((int)'x')));
    	int height = Integer.parseInt(size.substring(size.indexOf((int)'x')+1));
    	InputStream decodedInput;
    	
    	//check color input string
    	if (color.matches("[0-9a-fA-f]{6}") && width >= 100 && height >= 100)
    		{
	    	Color bColor = Color.decode('#'+color);
	    	// TODO add alpha gradient & color compatibility!
	    	
	    	//Color.GRAY is #808080
	    	if (bColor.getRGB() == Color.GRAY.getRGB())
	    	{
	    		dircolor = "white";
	    		isgray = true;
	    	}
	    	else
	    		dircolor = "grey";
	    	
	        final TextImage placeholder = new TextImageImpl(width, height, new Margin(0, 0, 0, 0));
	        
	        // Setting up the font
			InputStream isfont = new FileInputStream("fonts/seguisb.ttf");
			Font SegUIsb = null;
			try {
				SegUIsb = Font.createFont(Font.PLAIN, isfont);
				SegUIsb = SegUIsb.deriveFont(20f);
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
			
			// IMAGES   //////////////////////////////////////////////////////////
	        InputStream iswater = Application.class.getResourceAsStream("/images/ip_"+dircolor+".png");
	        BufferedImage ipwater = ImageIO.read(iswater);
	        
	        InputStream isupl = Application.class.getResourceAsStream("/images/arrows/"+dircolor+"/up-l.png");
	        BufferedImage upl = ImageIO.read(isupl);
	        
	        InputStream isupr = Application.class.getResourceAsStream("/images/arrows/"+dircolor+"/up-r.png");
	        BufferedImage upr = ImageIO.read(isupr);
	        
	        InputStream islowl = Application.class.getResourceAsStream("/images/arrows/"+dircolor+"/low-l.png");
	        BufferedImage lowl = ImageIO.read(islowl);
	        
	        InputStream islowr = Application.class.getResourceAsStream("/images/arrows/"+dircolor+"/low-r.png");
	        BufferedImage lowr = ImageIO.read(islowr);
	        
	        //END IMAGES /////////////////////////////////////////////////////////
			
	        if (isgray)
	        	placeholder.performAction(new BackgroundColorCallback(bColor, Color.WHITE, placeholder));
	        else
	        	placeholder.performAction(new BackgroundColorCallback(bColor, Color.GRAY, placeholder));
	        
	        
	        //Writing the 4 arrows
	        placeholder.addVSpace(40);
	        placeholder.write(upl);
	        placeholder.addHSpace(width-100);
	        placeholder.write(upr);
	        placeholder.addHSpace(-width);
	        
	        //Logo
	        placeholder.addVSpace(height-height/2-83);
	        placeholder.addHSpace(width-width/2-25);
	        placeholder.write(ipwater);
	        
	        //Size Label
	        placeholder.addHSpace(-61);
	        placeholder.addVSpace(30);
	        placeholder.withFont(SegUIsb).write(width+"x"+height);
	        placeholder.addVSpace(-30);
	        placeholder.addHSpace(61);
	        
	        
	        //Fixing H/V for low arrows
	        placeholder.addHSpace(-width+width/2-101);
	        placeholder.addVSpace(height/2+18);
	      
	        
	
	        placeholder.write(lowl);
	        placeholder.addHSpace(width-102);
	        placeholder.write(lowr);
	        
	
	        
	        ImageWriter imageWriter = ImageWriterFactory.getImageWriter(ImageType.PNG);
	        OutputStream libout = new ByteArrayOutputStream();
	    	imageWriter.writeImageToOutputStream(placeholder, libout);
	    	decodedInput=new ByteArrayInputStream(((ByteArrayOutputStream) libout).toByteArray());
	    
	    response().setContentType("image/png");
		return ok(decodedInput);
    		}
    	else if(width < 100 || height < 100)
    		return badRequest("Invalid image size!");
    	else
    		return badRequest("Invalid color! Specify an hex color (#ffffff or #ABCDEF, case insensitive)");
	    	
	    }
}

 

