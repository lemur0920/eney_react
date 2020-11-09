package eney.api.v1.domain;

public class ApiAuthVo {
	public final static int AUTH_SCOPE_VNO = 1;
	public final static int AUTH_SCOPE_COLORRING = 2;
	public final static int AUTH_SCOPE_RCVMENT = 3;
	public final static int AUTH_SCOPE_SENDMENT = 4;
	public final static int AUTH_SCOPE_CALLBACK = 5;
	
	private int auth_idx;
	private int token_idx;
	private int auth_scope;
	
	public int getAuth_idx() {
		return auth_idx;
	}
	public void setAuth_idx(int auth_idx) {
		this.auth_idx = auth_idx;
	}
	public int getToken_idx() {
		return token_idx;
	}
	public void setToken_idx(int token_idx) {
		this.token_idx = token_idx;
	}
	public int getAuth_scope() {
		return auth_scope;
	}
	public void setAuth_scope(int auth_scope) {
		this.auth_scope = auth_scope;
	}

	@Override
	public String toString() {
		return "ApiAuthVo [auth_idx=" + auth_idx + ", token_idx=" + token_idx
				+ ", auth_scope=" + auth_scope + "]";
	}
}
