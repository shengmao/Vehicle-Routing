/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jgraphics;

/**
 *
 * @author lis
 */
import UIView.StringParser;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import org.jgraph.graph.*;
import org.jgraph.JGraph;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import org.jgraph.graph.EdgeView;


// resolve ambiguity
import org.jgraph.graph.DefaultEdge;
import org.json.JSONException;
import org.json.JSONObject;


public class JGraphAdapterDemo
    extends JApplet
{
    //~ Static fields/initializers ---------------------------------------------

    //private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(1000, 750);

    //~ Instance fields --------------------------------------------------------

    //
    private JGraphModelAdapter<String, DefaultEdge> jgAdapter;

   
    public static void main(String [] args)
    {
        org.jgrapht.demo.JGraphAdapterDemo applet = new org.jgrapht.demo.JGraphAdapterDemo();
        
        
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

 
        public void init(ArrayList fal, String[] strarr, ArrayList al2) throws JSONException
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g;
        g = new ListenableDirectedMultigraph<String, DefaultEdge>(
                DefaultEdge.class);
        
        // create a visualization using JGraph, via an adapter
        jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(g);

        JGraph jgraph = new JGraph(jgAdapter);
        adjustDisplaySettings(jgraph);
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);
         //jgraph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
        
        
         /**
	 * Removing edge labels
	 */
	GraphLayoutCache cache = jgraph.getGraphLayoutCache();
		CellView[] cells = cache.getCellViews();
		for (CellView cell : cells) {
                                
			if (cell instanceof EdgeView) {
				EdgeView ev = (EdgeView) cell;
                                System.out.println("label postions: "+ev.getLabelPosition ());
				DefaultEdge eval = (DefaultEdge) ev.getCell();
                                eval.setUserObject("");
			        
			}
		}
		cache.reload();
		jgraph.repaint();
        
        
        
        
        
        
        
        //parse customer fulda, otherwise fulda is postioned more than one time, can not be displayed in the map
         g.addVertex("fulda");
         JSONObject json_object1 = (JSONObject) al2.get(0);
         positionVertexAt("fulda", json_object1.getInt("y"), json_object1.getInt("x"));
        
        //parse planning result        
        StringParser sp = new StringParser();        
        for(int i=0; i<fal.size(); i++){            
            
            String[] subtoken = (String[])fal.get(i);
            String sequence = subtoken[0];
            System.out.println(sequence);
            String[] cindex= sp.convert2(sequence);
            
            
            for(int j=0; j<cindex.length-1; j++){
                System.out.println(cindex[j]);
            }
            
            //parse to get customer names
            for(int j=0; j<cindex.length-1; j++){
                 System.out.println("Erro------------------------------------>");
                 System.out.println(cindex[j]);
                 
                int index = Integer.parseInt(cindex[j]);
                String cusname = strarr[index];
                if(index ==0)
                {
                    System.out.println("customer fulda has already be postioned on the map");
                }else{
                     g.addVertex(cusname);
                     JSONObject json_object2 = (JSONObject) al2.get(index);
                     int x=json_object2.getInt("x");
                     int y = json_object2.getInt("y");
                     positionVertexAt(cusname, y, x);
                }                        
               
             }
            
             //parse to get route value pair
            for(int j=0; j<cindex.length-1; j++){
            int index1 = Integer.parseInt(cindex[j]); 
            int index2 = Integer.parseInt(cindex[j+1]); 
            
            String x= strarr[index1];
            String y;
            if(j==cindex.length-2)
                y="fulda";
            else{
                System.out.println("ArrayIndexOutOfBoundsException:"+index2+" j:"+j+" cindex :"+index1);
                y=strarr[index2];
            }
            g.addEdge(x,y);
            
            
            System.out.print("x:"+x+" y:"+y);
            System.out.println();   
        }
        }
        
    }

    private void adjustDisplaySettings(JGraph jg)
    {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter("bgcolor");
        } catch (Exception e) {
        }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }

    @SuppressWarnings("unchecked") // FIXME hb 28-nov-05: See FIXME below
    private void positionVertexAt(Object vertex, int x, int y)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
            new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth()-15,
                bounds.getHeight()-16);

        GraphConstants.setBounds(attr, newBounds);

        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }

    //~ Inner Classes ----------------------------------------------------------

    private static class ListenableDirectedMultigraph<V, E>
        extends DefaultListenableGraph<V, E>
        implements DirectedGraph<V, E>
    {
        //private static final long serialVersionUID = 1L;

        ListenableDirectedMultigraph(Class<E> edgeClass)

        {
            super(new DirectedMultigraph<V, E>(edgeClass));
        }
    }
    
    
   
}