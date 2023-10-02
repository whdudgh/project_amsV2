package ezen.ams.gui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;

/**
 * 
 * @author 조영호
 *
 */
public class AMSFrame extends Frame {
	GridBagLayout grid;
	GridBagConstraints conts;
	Choice choice;
	Label accountType, accountNum, accountOwner, password, borrowMoney, accountList, inputMoney, moneyType;
	Button showAccount, deletAccount, searchAccount, createNewAccount, showAll; 
	TextField accNumTF, accOwnerTF, passTF, inMoneyTF, boMoneyTF;
	TextArea accListTA;
	
	public AMSFrame() {
		this("no-Title");
	}
	
	public AMSFrame(String title){
		super(title);
		grid = new GridBagLayout();
		conts = new GridBagConstraints();
		setLayout(grid);
		
		
		
		conts.weightx = 1.0;
		conts.weighty = 1.0;
		conts.insets = new Insets(5, 10, 5, 10);
		conts.fill = GridBagConstraints.HORIZONTAL;
		
		
		//Label(라벨) 초기화
		accountType = new Label("계좌종류");
		accountNum = new Label("계좌번호");
		accountOwner = new Label("예금주명");
		password = new Label("비밀번호");
		borrowMoney = new Label("대출금액");
		accountList = new Label("계좌목록");
		inputMoney = new Label("입금금액");
		moneyType = new Label("(단위 : 원)");
		
		//choice 항목 add메서드로 추가
		choice = new Choice();
		choice.add("전체");
		choice.add("입출금계좌");
		choice.add("마이너스계좌");
		
		//Button 초기화
		showAccount = new Button("조 회");
		deletAccount = new Button("삭 제");
		searchAccount = new Button("검 색");
		createNewAccount = new Button("신규등록");
		showAll = new Button("전체조회");
		
		//TextField 초기화
		accNumTF = new TextField(); 
		accOwnerTF = new TextField(); 
		passTF = new TextField();
		inMoneyTF = new TextField(); 
		boMoneyTF = new TextField();
		accListTA = new TextArea();
		
		//배치
		//계좌 종류-------------------------------------------------
		gblInsert(accountType, 0, 0, 1, 1);
		gblInsert(choice, 1, 0, 1, 1);
		
		//계좌번호--------------------------------------------------
		gblInsert(accountNum, 0, 1, 1, 1);
		gblInsert(accNumTF, 1, 1, 2, 1);
		//버튼
		gblInsert(showAccount, 3, 1, 1, 1);
		gblInsert(deletAccount, 4, 1, 1, 1);
		
//		//예금주명--------------------------------------------------
		gblInsert(accountOwner, 0, 2, 1, 1);
		gblInsert(accOwnerTF, 1, 2, 1, 1);
		//버튼
		gblInsert(searchAccount, 3, 2, 1, 1);
//		
//		//비밀번호, 입금금액----------------------------------------
		gblInsert(password, 0, 3, 1, 1);
		gblInsert(passTF, 1, 3, 1, 1);
		
		gblInsert(inputMoney, 3, 3, 1, 1);
		gblInsert(inMoneyTF, 4, 3, 7, 1);
		
		//대출금액, 신규등록, 전체조회-----------------------------
		gblInsert(borrowMoney, 0, 4, 1, 1);
		gblInsert(boMoneyTF, 1, 4, 1, 1);
		
		gblInsert(createNewAccount, 3, 4, 1, 1);
		
		gblInsert(showAll, 4, 4, 1, 1);
		
		//계좌목록, 머니타입---------------------------------------
		gblInsert(accountList, 0, 5, 1, 1);
		gblInsert(moneyType, 7, 5, 1, 1);
		
		//계좌목록 텍스트필드
		gblInsert(accListTA, 0, 6, 8, 1);
		
		pack();	
		setVisible(true);
	}
	
	public void gblInsert(Component c, int x, int y, int w, int h) {
		conts.gridx = x;
		conts.gridy = y;
		conts.gridwidth = w;
		conts.gridheight = h;
		grid.setConstraints(c, conts);
		this.add(c);	
	}
	
	public static void main(String[] args) {
		new AMSFrame("EZEN-BANK AMS");
	}	
}


