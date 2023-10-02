package ezen.ams.app;

import ezen.ams.domain.Account;
import ezen.ams.domain.AccountRepository;
import ezen.ams.domain.ListAccountRepository;
import ezen.ams.domain.MinusAccount;
import ezen.ams.exception.NotBalanceException;
import ezen.ams.gui.AMSFrame2;

public class AMS2 {

	public static AccountRepository repository = new ListAccountRepository();

	public static void main(String[] args) {
		AMSFrame2 ams2 = new AMSFrame2("EZEN-BANK AMS");
		ams2.addEventListner();
		ams2.setVisible(true);

		//임시 더미데이터 입력
		repository.addAccount(new Account("김기정", 1234, 1000000));
		repository.addAccount(new Account("조기정", 1234, 1000000));
		repository.addAccount(new Account("조기정", 1234, 1000000));
		repository.addAccount(new Account("저기정", 1234, 1000000));
		repository.addAccount(new Account("브기정", 1234, 1000000));
		repository.addAccount(new MinusAccount("오기정", 1234, 0, 9000000));
		repository.addAccount(new MinusAccount("박기정", 1234, 0, 9000000));
		repository.addAccount(new Account("이기정", 1234, 1000000));
	}
}
