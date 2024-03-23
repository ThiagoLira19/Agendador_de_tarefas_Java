/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agendadordetarefas.service;

import com.agendadordetarefas.MainWindow;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.Timer;

/**
 *
 * @author G0041901
 */
public class Service implements ActionListener{

    public Timer timer;
    public MainWindow window;
    
    int hora;
    int min;
    int seg;
    
    String proximaExecucao = "primeira";
    
    public Service(MainWindow window) {
        
        this.window = window;
        hora = 0;
        min = 0;
        seg = 0;
        
    }
    
    public void init() {
        
        timer = new Timer(1000, this);
        timer.start();
        
    }
    
    public void stop() {
        
        timer.stop();
        window.getTempoDecorrido().setText("");
        
    }
    
    public void actionPerformed(final ActionEvent e) {
        
        seg++;
        if(seg==60){
            min++;
            seg=0;
        }
        if(min==60){
            hora++;
            min=0;
        }
        window.getTempoDecorrido().setText(hora+":"+min+":"+seg);
        
        String texto = executarTarefa();
        
        if(!texto.equals("")){
            window.getTerminal().setText(window.getTerminal().getText()+texto);        
        }
        
    }
    
    public String executarTarefa(){
        
        System.out.println(testaIntervaloExcecao());
        
        if(!testaIntervaloExcecao()){
        
            if(proximaExecucao.equals("primeira") || proximaExecucao.equals(getDataAtual())){

                String arquivo = window.getCaminhoArquivo().getText();
                String intervalo = window.getIntervaloFrequencia().getSelectedItem().toString();
                String inicioExcecao = window.getInicioExcecao().getSelectedItem().toString();
                String fimExcecao = window.getFimExcecao().getSelectedItem().toString();

                try {
                    Desktop.getDesktop().open(new java.io.File(arquivo));
                    //Runtime.getRuntime().exec("cmd /c \""+arquivo+"\"");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                proximaExecucao = getDataAgendada(intervalo);

                return "Tarefa executada com sucesso em "+getDataAtual()+"\nPróxima execução prevista para "+proximaExecucao+"\n\n";

            }else{

                return "";

            }
                       
        }else{
            
            return "";
            
        }
        
    }
    
    public String getDataAtual(){
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        return sdf.format(c.getTime());
    }
    
    public String getDataAgendada(String tempo){
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        switch(tempo){
            case "10 min":
                c.add(Calendar.MINUTE,10);
            break;
            case "15 min":
                c.add(Calendar.MINUTE,15);
            break;
            case "30 min":
                c.add(Calendar.MINUTE,30);
            break;
            case "1 h":
                c.add(Calendar.MINUTE,60);
            break;
            case "2 h":
                c.add(Calendar.MINUTE,120);
            break;
            case "6 h":
                c.add(Calendar.MINUTE,360);
            break;
            case "12 h":
                c.add(Calendar.MINUTE,720);
            break;
            case "24 h":
                c.add(Calendar.MINUTE,1440);
            break;
                
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(c.getTime());
        
    }
    
    public boolean testaIntervaloExcecao(){
        
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        
        int hora_atual = Integer.parseInt(sdf.format(c.getTime()));
        
        String texto_hora_inicio = window.getInicioExcecao().getSelectedItem().toString();
        int hora_inicio;
        if(!texto_hora_inicio.equals("Nenhum")){
            texto_hora_inicio = texto_hora_inicio.replace(" h", "");
            hora_inicio = Integer.parseInt(texto_hora_inicio);
        }else
            hora_inicio = 24;
        
        String texto_hora_fim = window.getFimExcecao().getSelectedItem().toString();
        int hora_fim;
        if(!texto_hora_fim.equals("Nenhum")){
            texto_hora_fim = texto_hora_fim.replace(" h", "");
            hora_fim = Integer.parseInt(texto_hora_fim);
        }else
            hora_fim = 24;
                
        if(hora_atual >= hora_inicio && hora_atual <= hora_fim)
            return true;
        else
            return false;
    }

}
