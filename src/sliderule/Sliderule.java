/**
 * This project uses batik to render a circular sliderule with the C/D scales.
 * <p>
 * Batik website:
 * http://xmlgraphics.apache.org/batik/
 * SVG documentation:
 * http://www.w3.org/TR/SVG/
 * <p>
 * @author Dustin CS Wagner
 * @version 2013.11.27
 * @license GPLv3
 * <p>
 * Copyright (C) 2013 Dustin CS Wagner
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package sliderule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;


public class Sliderule {
	
	//Class Variables
	private int docWidth;
	private int docHeight;
	private SVGGraphics2D graphics;
	private DOMImplementation impl;
	private String svgNS;
	private SVGDocument doc;
	
	//Constructor
	public Sliderule() {
		//Dimensions for 8.5" x 11"
		//Where 1" = 90px
		docWidth = 765;
		docHeight = 990;
				
	    // Create an SVG document.
	    impl = SVGDOMImplementation.getDOMImplementation();
	    svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	    doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);
	    //graphics = new SVGGraphics2D(doc);
	    graphics = drawCircularSliderule();
	}

	

	/**
	 * Draws the ruler. 
	 * @return SVGGraphics2D
	 */
	private SVGGraphics2D drawCircularSliderule() {
		int a = Math.round(docWidth/2); //center of width
		int bo = 275; //y coord for outer ring
		int bi = 755; //y coord for inner ring
		int roa = 248; //radius for outer allowable circle
		int ro = 203; //radius for outer black circle
		int ri = 202; //radius for inner black circle
		int ria = 157; //radius for inner allowable circle
		DecimalFormat df = new DecimalFormat(".#"); //for the tens
		DecimalFormat intf = new DecimalFormat("#"); //for the ints
		Color mainColor = Color.black; //circles and hole punches
		Color highlightColor = Color.red; //integers and their lines
		Color secondaryColor = Color.gray; //tens and their lines
		Color guideLineColor = Color.white; //outer line white for off, pink is okay
		
		SVGGraphics2D tempG = new SVGGraphics2D(doc);
		tempG.setSVGCanvasSize(new Dimension(docWidth, docHeight));
	    
		/*                   */
		/* Draw Main Circles */
		//Circles are placed as inscribed by a square using the squares top-left corner
		tempG.setPaint(mainColor);
	    tempG.draw(new Ellipse2D.Double(a-ro, bo-ro , 2*ro, 2*ro));
	    tempG.draw(new Ellipse2D.Double(a-ri, bi-ri, 2*ri, 2*ri));
	    
	    tempG.setPaint(guideLineColor);
	    tempG.draw(new Ellipse2D.Double(a-roa, bo-roa, 2*roa, 2*roa));
	    tempG.draw(new Ellipse2D.Double(a-ria, bi-ria, 2*ria, 2*ria));
	    
	    /*            */
	    /* Draw Lines */ 
	    //the starting numbers//
	    List<Double> v = new ArrayList<Double>();
	    for (double i = 1; i <= 10; i = i + 0.10) {
	    	v.add(Double.parseDouble(df.format(i)));
	    } //v = {1.1, 1.2, 1.3, ..., 9.8, 9.9, 10};
	    	    
	    //the log transforms//
	    List<Double> s = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	s.add(Math.log10(v.get(i)));
	    }
	    
	    //the angles in radians//
	    List<Double> theta = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	theta.add((s.get(i)*2.0*Math.PI));
	    } 
	    
	    //Outer circle X and Y coords//
	    List<Double> outX = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	outX.add(a + ro*Math.cos(theta.get(i)));
	    } 
	    
	    List<Double> outY = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	outY.add(bo + ro*Math.sin(theta.get(i)));
	    } 
	    
	    //coords for major lines
	    List<Double> outAX = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	outAX.add(a + roa*Math.cos(theta.get(i)));
	    } 
	    
	    List<Double> outAY = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	outAY.add(bo + roa*Math.sin(theta.get(i)));
	    } 
	    
	    //coords for minor lines
	    List<Double> outMinorX = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	outMinorX.add(a + (0.5)*(roa+ro)*Math.cos(theta.get(i)));
	    } 
	    
	    List<Double> outMinorY = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	outMinorY.add(bo + (0.5)*(roa+ro)*Math.sin(theta.get(i)));
	    } 
	    
	    //Inner circle X and Y coords//
	    List<Double> inX = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	inX.add(a + ri*Math.cos(theta.get(i)));
	    } 
	    
	    List<Double> inY = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	inY.add(bi + ri*Math.sin(theta.get(i)));
	    } 
	    
	    //coords for major lines
	    List<Double> inAX = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	inAX.add(a + ria*Math.cos(theta.get(i)));
	    } 
	    
	    List<Double> inAY = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	inAY.add(bi + ria*Math.sin(theta.get(i)));
	    } 
	    
	    //coords for minor lines
	    List<Double> inMinorX = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	inMinorX.add(a + (0.5)*(ria+ri)*Math.cos(theta.get(i)));
	    } 
	    
	    List<Double> inMinorY = new ArrayList<Double>();
	    for (int i = 0; i < v.size(); i++) {
	    	inMinorY.add(bi + (0.5)*(ria+ri)*Math.sin(theta.get(i)));
	    } 
	    
	    //Draw Lines for outer circle//
	    tempG.setPaint(mainColor);
	    for (int i = 0; i < v.size(); i++) {
	    	if(i<v.size()-1) {              //the ints
	    		if(v.get(i) % 1.0 < 0.05) { 
	    			tempG.setPaint(highlightColor);
	    			tempG.draw(new Line2D.Double(outX.get(i), outY.get(i), outAX.get(i), outAY.get(i)));
	    		}
	    		else {              //the tens 
	    			tempG.setPaint(mainColor);
	    			tempG.draw(new Line2D.Double(outX.get(i), outY.get(i), outMinorX.get(i), outMinorY.get(i)));
	    		}
	    	}
	    }
	    
	    //Draw Lines for inner circle//
	    tempG.setPaint(mainColor);
	    for (int i = 0; i < v.size(); i++) {
	    	if(i<v.size()-1) {              //the ints
	    		if(v.get(i) % 1.0 < 0.05) { 
	    			tempG.setPaint(highlightColor);
	    			tempG.draw(new Line2D.Double(inX.get(i), inY.get(i), inAX.get(i), inAY.get(i)));
	    		}
	    		else {              //the tens 
	    			tempG.setPaint(mainColor);
	    			tempG.draw(new Line2D.Double(inX.get(i), inY.get(i), inMinorX.get(i), inMinorY.get(i)));
	    		}
	    	}
	    }
	    
	    /*              */
	    /* Draw Numbers */
	    tempG.setPaint(secondaryColor);
	    tempG.setFont(new Font("Helvetica", Font.PLAIN, 18)); 
	    AffineTransform orig = tempG.getTransform();
	    
	    //Draw Numbers for outer circle//
	    for (int i = 0; i < v.size(); i++) {
	    	if(i<v.size()-1) { //ignore the last '10'
	    		if(v.get(i) % 1.0 < 0.05) {//the integers
	    			tempG.rotate(theta.get(i),Float.parseFloat(outAX.get(i).toString()), Float.parseFloat(outAY.get(i).toString()));
	    			tempG.setPaint(highlightColor);
	    			tempG.setFont(new Font("Helvetica", Font.BOLD, 24)); 
	    			tempG.drawString(intf.format(v.get(i)), Float.parseFloat(outAX.get(i).toString()), Float.parseFloat(outAY.get(i).toString()));
	    			tempG.setTransform(orig);
	    			//System.out.println("current gray v: "+v.get(i)+" mod 1 =" + v.get(i) % 1.0);
	    		}
	    		else {//the tens
	    			tempG.rotate(theta.get(i),Float.parseFloat(outMinorX.get(i).toString()), Float.parseFloat(outMinorY.get(i).toString()));
	    			tempG.setPaint(secondaryColor);
	    			tempG.setFont(new Font("Helvetica", Font.PLAIN, 8)); 
	    			tempG.drawString(df.format(v.get(i) % 1), Float.parseFloat(outMinorX.get(i).toString()), Float.parseFloat(outMinorY.get(i).toString()));
	    			tempG.setTransform(orig);
	    		}
	    		
	    	}
	    	else {
	    		// do nothing
	    	}
	    } 
	    
	    
	    //Draw Numbers for inner circle//
	    for (int i = 0; i < v.size(); i++) {
	    	if(i<v.size()-1) { //ignore the last '10'
	    		if(v.get(i) % 1.0 < 0.05) {//the integers
	    			tempG.rotate(theta.get(i),Float.parseFloat(inAX.get(i).toString()), Float.parseFloat(inAY.get(i).toString()));
	    			tempG.setPaint(highlightColor);
	    			tempG.setFont(new Font("Helvetica", Font.BOLD, 24)); 
	    			tempG.drawString(intf.format(v.get(i)), Float.parseFloat(inAX.get(i).toString()), Float.parseFloat(inAY.get(i).toString()));
	    			tempG.setTransform(orig);
	    			//System.out.println("current gray v: "+v.get(i)+" mod 1 =" + v.get(i) % 1.0);
	    		}
	    		else {//the tens
	    			tempG.rotate(theta.get(i),Float.parseFloat(inMinorX.get(i).toString()), Float.parseFloat(inMinorY.get(i).toString()));
	    			tempG.setPaint(secondaryColor);
	    			tempG.setFont(new Font("Helvetica", Font.PLAIN, 8)); 
	    			tempG.drawString(df.format(v.get(i) % 1), Float.parseFloat(inMinorX.get(i).toString()), Float.parseFloat(inMinorY.get(i).toString()));
	    			tempG.setTransform(orig);
	    		}
	    		
	    	}
	    	else {
	    		//do nothing
	    	}
	    } 
	    	    
	    /*           */
	    /* Draw Misc */
	    //Hole punches//
		tempG.setPaint(mainColor);
	    tempG.draw(new Ellipse2D.Double(a-3, bo-3 , 6, 6));
	    tempG.draw(new Ellipse2D.Double(a-3, bi-3, 6, 6));
	    
	    //Cut line for outer circle//
	    tempG.setPaint(mainColor);
	    int cut = roa+20;
	    tempG.draw(new Ellipse2D.Double(a-cut, bo-cut, 2*cut, 2*cut));
	    
	    //C and D labels//
	    tempG.rotate(Math.PI,Float.parseFloat(outAX.get(0).toString()), Float.parseFloat(outAY.get(0).toString()));
		tempG.setPaint(mainColor);
		tempG.setFont(new Font("Helvetica", Font.BOLD, 18)); 
		tempG.drawString("D", Float.parseFloat(outAX.get(0).toString()), Float.parseFloat(outAY.get(0).toString()));
		tempG.setTransform(orig);

	    tempG.rotate(Math.PI,Float.parseFloat(inAX.get(0).toString()), Float.parseFloat(inAY.get(0).toString()));
		tempG.setPaint(mainColor);
		tempG.drawString("C", Float.parseFloat(inAX.get(0).toString()), Float.parseFloat(inAY.get(0).toString()));
		tempG.setTransform(orig);
		
		
	    //text//
		int writeX = a-50;
		int writeY = bi+25;
		int lineIncrement = 15;
		int byLineIncrement = 10;
		String[] conversions = {
				"1 in  = 2.54  cm", 
				"1 lb  = 0.454 kg",
				"1 mph = 0.447 m/s",
				};
		String[] byLine = {
				"Copyright (C) 2013 Dustin CS Wagner", 
				"Licensed under GPLv3",
				"https://github.com/DustinCSWagner/sliderule",
				};
		
		tempG.setPaint(mainColor);
		tempG.setFont(new Font("Helvetica", Font.PLAIN, 12)); 
		for (int i =0; i<conversions.length; i++){
			tempG.drawString(conversions[i], writeX, writeY);
			writeY += lineIncrement; 
		}
		
		tempG.setFont(new Font("Helvetica", Font.PLAIN, 8)); 
		for (int i =0; i<byLine.length; i++){
			tempG.drawString(byLine[i], writeX, writeY);
			writeY += byLineIncrement; 
		}
		
	    return tempG;
	}
  
	
	//Display the ruler in a JFrame
	private void display() {
	    // Populate the document root with the generated SVG content.
	    Element root = this.doc.getDocumentElement();
	    
	    //g.getRoot(root);
	    this.graphics.getRoot(root);
	    
	    // Display the document.
	    JSVGCanvas canvas = new JSVGCanvas();
	    JFrame f = new JFrame();
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.getContentPane().add(canvas);
	    canvas.setSVGDocument(this.doc);
	    f.setSize(this.docWidth, this.docHeight);
	    f.setVisible(true);
	}
  
	

	/**
	 * Writes a svg file out and then converts it to a pdf.
	 * @param filepath
	 */
	public void write(String filepath) {
		File svgFile = new File(filepath+"slideRuler.svg");
		File pdfFile = new File(filepath+"slideRuler.pdf");
		
		System.out.println("Printing svg to: "+svgFile.getAbsolutePath()+"");
		try {
			OutputStream outputStream = new FileOutputStream(svgFile);
			Writer out = new OutputStreamWriter(outputStream, "UTF-8");
			this.graphics.stream(out, true);						
			outputStream.flush();
			outputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		Transcoder transcoder = new PDFTranscoder();
		System.out.println("Transcoding svg to: "+pdfFile.getAbsolutePath()+"");
		try {
			TranscoderInput transcoderInput = new TranscoderInput(new FileInputStream(svgFile));
			TranscoderOutput transcoderOutput = new TranscoderOutput(new FileOutputStream(pdfFile));
			transcoder.transcode(transcoderInput, transcoderOutput);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
	}

	
/**
 * Main Method
 * @param args
 * @throws IOException
 */
public static void main(String[] args) throws IOException {
	  Sliderule circleRuler = new Sliderule();
	  circleRuler.display();
	  circleRuler = new Sliderule(); //why is the ruler being garbage collected?
	  circleRuler.write("");
  }

}








