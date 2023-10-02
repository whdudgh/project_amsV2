package ezen.ams.domain;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileAccountRepository implements AccountRepository {

	// 파일 저장 경로
	private static final String FILE_PATH = "account.acc";

	// 계좌수 저장을 위한 사이즈 고정
	private static final int RECORD_COUNT_LENGTH = 4;

	// 계좌번호 길이 고정
	private static final int ACCOUNT_NUM_LENGTH = 12;

	// 이름5글자 가능하게 고정(10바이트)
	private static final int ACCOUNT_OWNER_LENGTH = 10;

	// 계좌 잔액 길이 고정(60바이트)
	private static final int ACCOUNT_RESTMONEY_LENGTH = 60;

	// 계좌 대출액 길이 고정(60바이트)
	private static final int ACCOUNT_BORROW_MONEY = 60;

	// 어카운트 계좌저장시 총 레코드사이즈 = 142바이트
	public static final int ALL_RECORD_LENGTH = RECORD_COUNT_LENGTH + ACCOUNT_NUM_LENGTH + ACCOUNT_OWNER_LENGTH
			+ ACCOUNT_RESTMONEY_LENGTH + ACCOUNT_BORROW_MONEY;

	RandomAccessFile file;

	// 저장된 계좌의 수
	private int accountCount = 0;

	// 생성자에서 파일객체생성필요 계좌정보가 이미 있으면 count+1
	public FileAccountRepository() {
		try {
			file = new RandomAccessFile(FILE_PATH, "rw");

			if (file.length() != 0) {
				accountCount = file.readInt();
			}
		} catch (IOException io) {
			io.getMessage();
		}
	}
	/**
	 * 현재파일에 저장된 계좌번호의 최대 번호 알아내는 메서드
	 * @return 계좌번호 최대 번호
	 * @throws IOException 
	 */

	public int maxAccountNum() {
		int maxAccountNum = 0;
		for (int i = 0; i < accountCount; i++) {
			try {
				Account account = getFileAccount(i);
				int ma = Integer.parseInt(account.getAccountNum());
				if(maxAccountNum < ma) {
					maxAccountNum = ma;					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return maxAccountNum;
	}

	// 오버라이드 메서드들
	/**
	 * 전체계좌 목록 수 반환
	 */
	@Override
	public int getCount() {
		return accountCount;
	}

	/**
	 * 전체계좌 목록
	 */
	@Override
	public List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		for (int i = 0; i < accountCount; i++) {
			try {
				Account account = getFileAccount(i);
				accounts.add(account);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return accounts;
	}

	/**
	 * 신규계좌 등록
	 */
	@Override
	public boolean addAccount(Account account) {
		// 새로운 계좌 추가
		String accName = account.getAccountOwner();
		String accNum = account.getAccountNum();
		int passward = account.getPasswd();
		long restMoney = account.getRestMoney();
		
		account = new MinusAccount();
		long borrowMoney = account.getBorrowMoney();
		
		int nameCount = accName.length();
		int numCount = accNum.length();

		try {
			
			file.seek((accountCount * ALL_RECORD_LENGTH) + RECORD_COUNT_LENGTH);
			//1.오우너
			for (int i = 0; i < ACCOUNT_OWNER_LENGTH / 2; i++) {
				file.writeChar((i < nameCount ? accName.charAt(i) : ' '));
			}
			;
			//2.계좌번호
			for (int i = 0; i < ACCOUNT_NUM_LENGTH / 2; i++) {
				file.writeChar((i < numCount ? accNum.charAt(i) : ' '));
			};
//			System.out.println("생성시디버깅"+accNum);
			
			//3.패스워드
			file.writeInt(passward);
			//4.레스트머니
			file.writeLong(restMoney);
			//5.버로우머니
			if(borrowMoney != 0L) {
				file.writeLong(borrowMoney);				
			}
			
			file.seek(0);
			file.writeInt(++accountCount);
			System.out.println(accountCount + "명 저장됨.");
			System.out.println("저장된 후 계좌번호 = "+accNum);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("이미 등록된 계좌입니다.");
			return false;
		}
	}

	@Override
	public Account searchAccount(String accountNum) {
//		List<Account> accounts = new ArrayList<Account>();
		for (int i = 0; i < accountCount; i++) {
			try {
				Account account = getFileAccount(i);
				if (account.getAccountNum().equals(accountNum)) {
					return account;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Account> searchAccountByOwner(String accountOwner) {
		// 생성된 파일에서 정보 읽어오기 input
		return null;
	}

	@Override
	public boolean removeAccount(String accountNum) {
		// 작성된 계좌삭제 input으로 읽어와서 삭제 후 output
		return false;
	}

//	특정 위치 정보를 읽어와 Account객체로 반환
	private Account getFileAccount(int index) throws IOException {
		Account account = null;

		String accOwner = " ";
		String accNum = " ";
		int accPass = 0;
		long restMoney = 0L;
		long borrowMoney = 0L;

		file.seek((index * ALL_RECORD_LENGTH) + RECORD_COUNT_LENGTH);
		// 1.오우너
		for (int i = 0; i < (ACCOUNT_OWNER_LENGTH / 2); i++) {
			accOwner += file.readChar();
		}
		accOwner = accOwner.trim();
//		System.out.println("디버깅오우너" + accOwner);
		// 2.계좌번호
		for (int i = 0; i < (ACCOUNT_NUM_LENGTH / 2); i++) {
			accNum += file.readChar();
		}
		accNum = accNum.trim();
//		System.out.println("디버깅계좌번호" + accNum);
		// 3.패스워드
		accPass = file.readInt();
//		System.out.println("디버깅패스워드" + accPass);
		// 4.레스트머니
		restMoney = file.readLong();
		
		// 5.버로우머니
		if(file.readLong() != 0L) {
			borrowMoney = file.readLong();			
		}
		
		//마이너스객체인지 그냥 객체인지 분류
		if (!(borrowMoney == 0L)) {
			account = new MinusAccount();
		    account.setAccountOwner(accOwner);
		    account.setAccountNum(accNum);
		    account.setPasswd(accPass);
		    account.setRestMoney(restMoney);
		    account.setBorrowMoney(borrowMoney);
		    return account;
		} else {
			account = new Account();
			account.setAccountOwner(accOwner);
			account.setAccountNum(accNum);
			account.setPasswd(accPass);
			account.setRestMoney(restMoney);
			return account;
		}
	}

	/**
	 * 신규자식 메서드 추가 스트림리소스 반환 메서드
	 */
	public void close() {
		try {
			if (file != null)
				file.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
