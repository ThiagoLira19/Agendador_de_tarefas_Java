/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agendadordetarefas.controller;

import com.agendadordetarefas.MainWindow;
import com.agendadordetarefas.service.Service;

/**
 *
 * @author ThiagoLira19
 */
public class Controller {
    
    final Service service;
    
    public Controller(MainWindow window){
        
        this.service = new Service(window);
        
    }
    
    public void start(){
        
        service.init();
        
    }
    
}
