/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import objects.FiboNumbers;

/**
 * REST Web Service
 *
 * @author Wes Lloyd
 * @author Devin Durham
 */
@RestController
@RequestMapping("/fibo")
public class FiboService {

    static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(FiboService.class.getName());
    

    /**
     * Creates a new instance of GenericResource
     */
    public FiboService() {
    }

    /**
     * Retrieves representation of an instance of services.GenericResource
     * @return an instance of java.lang.String
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getXml() {
        log.info("Fibonacci GET");
        return "<html><body>Fibonacci Service TEST</body></html>";
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String putXml(String content) {
        String sText = "The user just put=" + content;
        log.info("Fibonacci PUT");
        log.info(sText);
        return sText;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Map<String, Object> PostJson(@RequestBody Map<String, Object> jobj) {
        log.info("Fibonacci POST");
        
        int inputVal = (Integer) jobj.get("number");
        
        FiboNumbers fn = new FiboNumbers();
        fn.setNumber(inputVal);
        
        StringBuilder text = new StringBuilder();
        text.append("The JSON obj:" + jobj.toString() + "\n");
        text.append("Request for fibonacci number for:" + fn.getNumber()+ "\n");
        log.info(text.toString());
        
        //BigInteger bn = fibonacci3(fn.getNumber()-1);
        BigInteger bn2 = fibonacci(fn.getNumber());
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        
        map.put("number", fn.getNumber());
        //fiboans.append("fibonacci_rec", bn.toString());
        map.put("fibonacci_seq", bn2.toString());
        
        return map;
    }
    
    
    private Map<Integer, BigInteger> memo = new HashMap<>();

    // Recursive fibonacci
    // Using Java we run out of stack space with large numbers
    public BigInteger fibonacci3(int n) {
        if (n == 0 || n == 1) {
            return BigInteger.ONE;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        BigInteger v = fibonacci3(n - 2).add(fibonacci3(n - 1));
        memo.put(n, v);
        return v;
    }    
    
    // Iterative fibonacci
    // Slower, but can handle larger numbers
    public BigInteger fibonacci(int n)
    {
        int i=0;
        BigInteger[] vec = new BigInteger[n+1];
        vec[0]=BigInteger.ZERO;
        vec[1]=BigInteger.ONE;
        // calculating
        for(i = 2 ; i<=n ; i++){
            //System.out.println("i=" + i + " n=" + n + " i-1=" + (i-1) + " i-2=" + (i-2));
            vec[i]=vec[i-1].add(vec[i-2]);
        }
        //System.out.println("RETURNING n=" + (n));
        return vec[n];
    }
}
