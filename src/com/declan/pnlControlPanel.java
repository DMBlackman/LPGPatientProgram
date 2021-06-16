package com.declan;




import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class pnlControlPanel {
    private JPanel contentPane;

    public JPanel panelOne;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JLabel lblHeaderTitle;
    private JButton btnPatientSearch;
    private JButton btnAddUpdatePatientInfo;
    private JButton btnViewWellnessInfo;
    private JButton btnPatientSummaryView;
    private JButton btnViewWellnessSummary;
    private JButton btnAddUpdateWellnessInfo;


    public pnlControlPanel(JPanel panel) {
        contentPane = panel;
        createActionListeners();


    }

    private void createActionListeners(){
        //actionlisteners for buttons to navigate to other JPanels
        btnPatientSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "2");
            }
        });
        btnPatientSummaryView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "3");
            }
        });
        btnAddUpdatePatientInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "4");
            }
        });
        btnViewWellnessSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "5");
            }
        });
        btnViewWellnessInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "6");
            }
        });
        btnAddUpdateWellnessInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "7");
            }
        });
    }

    public void fillAllPanelFields(){
        Main.panelPatientSearch.fillPatientSearch();
        Main.panelPatientSummaryInfo.fillPatientSummary();
        Main.panelAddUpdatePatientInfo.fillAddUpdatePatientInfo();
        Main.panelWellnessSummary.fillWellnessSummary();
        Main.panelWellnessLastCheckup.fillWellnessLastCheckup();
        Main.panelAddUpdateWellnessVisit.fillAddUpdateWellness();
    }

}
