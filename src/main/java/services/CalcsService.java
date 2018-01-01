/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CalcsNumbers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST Web Service
 *
 * @author Devin Durham
 */
@RestController
@RequestMapping("/calcs")
public class CalcsService {
    
    static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(CalcsService.class.getName());
    

    /**
     * Creates a new instance of CalcsResource
     */
    public CalcsService() {
    }

    /**
     * Retrieves representation of an instance of objects.CalcsResource
     * @return an instance of java.lang.String
     */
   @RequestMapping(method = RequestMethod.GET)
    public String getXml() {
        log.info("Calcs GET");
        return "<html><body>Calcs Service</body></html>";
    }

    /**
     * PUT method for updating or creating an instance of CalcsResource
     * @param content representation for the resource
     */
   @RequestMapping(method = RequestMethod.PUT)
    public String putXml(String content) {
        String sText = "The user just put=" + content;
        log.info("Calcs PUT");
        log.info(sText);
        return sText;
    }
    
   @RequestMapping(method = RequestMethod.POST)
    public String PostJson(String jobj) throws JSONException, IOException {
        log.info("Calcs POST");
        ObjectMapper mapper = new ObjectMapper();
        CalcsNumbers args = mapper.readValue(jobj.toString(), CalcsNumbers.class);
                
        StringBuilder text = new StringBuilder();
        text.append("The JSON obj:" + jobj.toString() + "\n");
        text.append("Request for Calcs" + "\n");
        log.info(text.toString());
        
        JSONObject calcString = randoCalc(args.getCalcs(),args.getLoops(), args.getSleep());
        
        JSONObject calcsOut = new JSONObject();
        calcsOut.put("Output", calcString);

        return calcsOut.toString();
    }
    
    
    
    public JSONObject randoCalc(int calcPass, int loopPass, int sleepPass) throws JSONException {

        StringBuilder output = new StringBuilder();
        
        JSONObject randoCalcOut = new JSONObject();
        
        int calcs = calcPass;
        int loopsIn = loopPass;
        int sleepIn = sleepPass;
       
        double globalArrayTime = 0;
        double globalTime = 0;
 
        randoCalcOut.put("Calcs", calcs);
        randoCalcOut.put("Loops", loopsIn);
        randoCalcOut.put("Sleep", sleepIn);

        for (int k = 0; k < loopsIn; k++) {

            Random rand = new Random();
            // By not reusing the same variables in the calc, this should prevent
            // compiler optimization... Also each math operation should operate
            // on between operands in different memory locations.
 
            double arrayST = System.currentTimeMillis();
            long[] operand_a = new long[calcs];
            long[] operand_b = new long[calcs];
            long[] operand_c = new long[calcs];
            double arrayET = System.currentTimeMillis();
            double returnArrayTime = arrayET - arrayST;
 
            //System.out.println("ArrayTime: " + returnArrayTime);
            globalArrayTime += returnArrayTime;
 
            long mult;
            double div1;
 
            double calcST = System.currentTimeMillis();
            for (int i = 0; i < calcs; i++) {
                // By not using sequential locations in the array, we should
                // reduce memory lookup efficiency
                int j = rand.nextInt(calcs);
                operand_a[j] = rand.nextInt(99999);
                operand_b[j] = rand.nextInt(99999);
                operand_c[j] = rand.nextInt(99999);
                mult = operand_a[j] * operand_b[j];
                div1 = (double) mult / (double) operand_c[j];
            }
            double calcET = System.currentTimeMillis();
            double returnCalcTime = calcET - calcST;
 
            //System.out.println("CalcTime: " + returnCalcTime);
            globalTime += returnCalcTime;
 
            //System.out.println("Total Time: " + (calcET-arrayST));
            try {
                Thread.sleep(sleepIn);
            } catch (InterruptedException ex) {
                Logger.getLogger(CalcsService.class.getName()).log(Level.SEVERE, null, ex);
            }

      }
        
       
        randoCalcOut.put("Total Array Time", globalArrayTime);
        randoCalcOut.put("Average Array Time" , globalArrayTime/loopsIn);
        randoCalcOut.put("Total Calc Time" , globalTime);
        randoCalcOut.put("Average Calc Time" , globalTime/loopsIn);
 
        
        return randoCalcOut;
    }
    
}