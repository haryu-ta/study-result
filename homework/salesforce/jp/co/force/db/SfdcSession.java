package jp.co.force.db;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.SessionHeader;
import com.sforce.soap.enterprise.SforceServiceLocator;
import com.sforce.soap.enterprise.SoapBindingStub;

public class SfdcSession {

	/** ユーザーID */
	private String uid = null;

	/** ユーザーパスワード */
	private String pwd = null;

	/** エンドポイント */
	private String endpoint = null;

	/** セールフォースのセッションクラス */
	private static SfdcSession currentSession = null;

	/** SoapBindingStubクラス */
	public SoapBindingStub binding = null;

	/** LoginResultクラス */
	private LoginResult lr = null;

	/** セッションヘッダクラス */
	private SessionHeader sh = null;

	public SfdcSession(String uid, String pwd, String endpoint) {
		this.uid = uid;
		this.pwd = pwd;
		this.endpoint = endpoint;
	}

	public static SfdcSession openSession(String uid, String pwd, String endpoint)
			throws IOException {

		currentSession = new SfdcSession(uid, pwd, endpoint);
		currentSession.close();
		currentSession.connect();
		return currentSession;
	}

	public void connect() throws IOException {
		// SOARBinding生成
		try {
			SforceServiceLocator sfl = new SforceServiceLocator();
			sfl.setSoapEndpointAddress(endpoint.trim());
			binding = (SoapBindingStub) sfl.getSoap();
		} catch (ServiceException e) {
			throw new IOException("SFDCへの接続失敗");
		}

		// ログイン
		lr = binding.login(this.uid, this.pwd);

		// エンドポイントURLセット
		binding._setProperty(SoapBindingStub.ENDPOINT_ADDRESS_PROPERTY, lr.getServerUrl());

		// セッションヘッダ作成
		sh = new SessionHeader();
		sh.setSessionId(lr.getSessionId());

		// セッションヘッダをセット
//		binding.setHeader(new SforceServiceLocator().getServiceName().getNamespaceURI(),SessionHeader, sh);
		binding.setHeader(new SforceServiceLocator().getServiceName().getNamespaceURI(), "SessionHeader", sh);
	}

	/**
	 * ログアウト
	 *
	 */
	public void close() {
		if (binding != null) {
			try {
				binding.logout();
			} catch (RemoteException e) {
				e.printStackTrace();
			} finally {
				binding = null;
			}
		}
	}

	/**
	 * タイムアウト時間設定
	 *
	 * @param timeout タイムアウト時間
	 */
	public void setTimeOut(int timeout) {
		binding.setTimeout(timeout);
	}

}
