package eney.util;
import java.nio.charset.Charset;

import eney.domain.AcsTransmitVO;

public class AcsUtils {
	
	public static byte[] converterByteArrayToAcsTransmit(AcsTransmitVO acsVO) {
		byte[] tcpPacketArray = new byte[94];
		
		for(int i=0; i<tcpPacketArray.length; i++){
			tcpPacketArray[i] = ' ';
		}
		
		Charset charset = Charset.forName("ASCII");
		
		byte[] code 		= acsVO.getCode().getBytes(charset);
		byte[] companeyId 	= acsVO.getCompany_id().getBytes(charset);
		byte[] length 		= acsVO.getLength().getBytes(charset);
		byte[] reqNum 		= acsVO.getReq_num().getBytes(charset);
		byte[] mode 		= acsVO.getMode().getBytes(charset);
		byte[] result 		= acsVO.getResult().getBytes(charset);
		byte[] telNum 		= acsVO.getTel_num().getBytes(charset);
		byte[] callBackNum 	= acsVO.getCall_back_num().getBytes(charset);
		byte[] authNum 		= acsVO.getAuth_num().getBytes(charset);
		byte[] severIp 		= acsVO.getServer_ip().getBytes(charset);
		byte[] serverPort 	= acsVO.getServer_port().getBytes(charset);
		
		// System.arraycopy(복사할배열, 원본의 첫 데이터, 붙여넣을배열, 복사된데이터넣을위치, 길이);
		System.arraycopy(code, 			0, tcpPacketArray, 0, Math.min(code.length, 4));
		System.arraycopy(companeyId, 	0, tcpPacketArray, 4, Math.min(companeyId.length, 10));
		System.arraycopy(length, 		0, tcpPacketArray, 14, Math.min(length.length, 4));
		System.arraycopy(reqNum, 		0, tcpPacketArray, 18, Math.min(reqNum.length, 12));
		System.arraycopy(mode, 			0, tcpPacketArray, 30, Math.min(mode.length, 2));
		System.arraycopy(result, 		0, tcpPacketArray, 32, Math.min(result.length, 2));
		System.arraycopy(telNum, 		0, tcpPacketArray, 34, Math.min(telNum.length, 12));
		System.arraycopy(callBackNum, 	0, tcpPacketArray, 46, Math.min(callBackNum.length, 12));
		System.arraycopy(authNum, 		0, tcpPacketArray, 58, Math.min(authNum.length, 14));
		System.arraycopy(severIp, 		0, tcpPacketArray, 72, Math.min(severIp.length, 16));
		System.arraycopy(serverPort, 	0, tcpPacketArray, 88, Math.min(serverPort.length, 6));
		
//		System.arraycopy(code, 			0, tcpPacketArray, 0 + (4 - code.length),			Math.min(code.length, 4));
//		System.arraycopy(companeyId, 	0, tcpPacketArray, 4 + (10 - companeyId.length), 	Math.min(companeyId.length, 10));
//		System.arraycopy(length, 		0, tcpPacketArray, 14 + (4 - length.length), 		Math.min(length.length, 4));
//		System.arraycopy(reqNum, 		0, tcpPacketArray, 18 + (12 - reqNum.length), 		Math.min(reqNum.length, 12));
//		System.arraycopy(mode, 			0, tcpPacketArray, 30 + (2 - mode.length), 			Math.min(mode.length, 2));
//		System.arraycopy(result, 		0, tcpPacketArray, 32 + (2 - result.length), 		Math.min(result.length, 2));
//		System.arraycopy(telNum, 		0, tcpPacketArray, 34 + (12 - telNum.length), 		Math.min(telNum.length, 12));
//		System.arraycopy(callBackNum, 	0, tcpPacketArray, 46 + (12 - callBackNum.length), 	Math.min(callBackNum.length, 12));
//		System.arraycopy(authNum, 		0, tcpPacketArray, 58 + (14 - authNum.length), 		Math.min(authNum.length, 14));
//		System.arraycopy(severIp, 		0, tcpPacketArray, 72 + (16 - severIp.length), 		Math.min(severIp.length, 16));
//		System.arraycopy(serverPort, 	0, tcpPacketArray, 88 + (6 - serverPort.length), 	Math.min(serverPort.length, 6));

		return tcpPacketArray;
	}

	public static AcsTransmitVO converterAcsResponseToByteArray(byte[] responseData) {
		byte[] code = new byte[4];
		byte[] companeyId = new byte[10];
		byte[] length = new byte[4];
		byte[] reqNum = new byte[12];
		byte[] mode = new byte[2];
		byte[] telNum = new byte[12];
		byte[] result = new byte[2];
		
		System.arraycopy(responseData, 0, code, 0, 4);
		System.arraycopy(responseData, 4, companeyId, 0, 10);
		System.arraycopy(responseData, 14, length, 0, 4);
		System.arraycopy(responseData, 18, reqNum, 0, 12);
		System.arraycopy(responseData, 30, mode, 0, 2);
		System.arraycopy(responseData, 32, telNum, 0, 12);
		System.arraycopy(responseData, 44, result, 0, 2);
		
		AcsTransmitVO responseVO = new AcsTransmitVO();
		
		responseVO.setCode(new String(code)); // 고정 0~3
		responseVO.setCompany_id(new String(companeyId)); // 고정 4~13
		responseVO.setLength(new String(length)); // 고정 14~17
		responseVO.setReq_num(new String(reqNum)); // 18~29
		responseVO.setMode(new String(mode)); // 고정 30~31
		responseVO.setTel_num(new String(telNum)); // 32~43
		responseVO.setResult(new String(result)); // 44~45

		return responseVO;
	}
	
	/**
	 * 오른쪽에 자릿수 채우기
	 * 
	 * @param str 텍스트 내용
	 * @param size 텍스트 길이
	 * @param c 채울 텍스트
	 * @return
	 */
	
	public static String getRPad(String str, int size, char c) {
		for (int i = (str.getBytes()).length; i < size; i++) {
			str += c;
		}
		return str;
	}

	public static String getLPad(String str, int size, char c) {
		for (int i = (str.getBytes()).length; i < size; i++) {
			str = c + str;
		}
		return str;
	}

}
