/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Anthony
 */
public abstract class Action {
    
    public abstract void execute(HttpServletRequest request);
    
}
