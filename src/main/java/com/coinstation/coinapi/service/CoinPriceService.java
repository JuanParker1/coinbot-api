package com.coinstation.coinapi.service;
import com.coinstation.coinapi.CoinApiConstants;
import com.coinstation.coinapi.config.CommonValues;
import com.coinstation.coinapi.vo.CoinNickVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoinPriceService {
	
	private static final Logger logger = LoggerFactory.getLogger(CoinPriceService.class);

	@Autowired
	private CommonValues commonValues;

	@Autowired
	private CommonService commonService;

	@Autowired
	@Qualifier(CoinApiConstants.DB_SOURCE_COIN_STATION)
	private SqlSession SqlSession;

	/**
	 * 코인 닉네임 삽입(벌크용)
	 * @param
	 */
	public boolean insertCoinNickBulk() throws IOException {

		boolean is_error = false;

		try{
			//json String 삽입 (List<jsonString> 형태 여야함)
//			String dataStr = "{\"data\":["  +
//					"     {\n" +
//					"    \"market\": \"KRW-BTC\",\n" +
//					"    \"nick_nm\": \"비트코인\",\n" +
//					"    \"english_name\": \"Bitcoin\"\n" +
//					"  },\n" +
//					"  {\n" +
//					"    \"market\": \"KRW-ETH\",\n" +
//					"    \"nick_nm\": \"이더리움\",\n" +
//					"    \"english_name\": \"Ethereum\"\n" +
//					"  }]}";

			String dataStr = "{\"data\":["  +
					"     {\n" +
					"      \"market\": \"ETH\",\n" +
					"      \"nick_nm\": \"이더리움\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ETC\",\n" +
					"      \"nick_nm\": \"이더리움 클래식\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"XRP\",\n" +
					"      \"nick_nm\": \"리플\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ATOM\",\n" +
					"      \"nick_nm\": \"코스모스아톰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BCH\",\n" +
					"      \"nick_nm\": \"비트코인 캐시\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"EOS\",\n" +
					"      \"nick_nm\": \"이오스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"QTUM\",\n" +
					"      \"nick_nm\": \"퀀텀\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"IOTA\",\n" +
					"      \"nick_nm\": \"아이오타\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LTC\",\n" +
					"      \"nick_nm\": \"라이트코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BTG\",\n" +
					"      \"nick_nm\": \"비트코인 골드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"OMG\",\n" +
					"      \"nick_nm\": \"오미세고 네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DATA\",\n" +
					"      \"nick_nm\": \"스트리머\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ZIL\",\n" +
					"      \"nick_nm\": \"질리카\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KNC\",\n" +
					"      \"nick_nm\": \"카이버\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ZRX\",\n" +
					"      \"nick_nm\": \"제로엑스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"XTZ\",\n" +
					"      \"nick_nm\": \"테조스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"REP\",\n" +
					"      \"nick_nm\": \"어거\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SNM\",\n" +
					"      \"nick_nm\": \"소늠\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ADT\",\n" +
					"      \"nick_nm\": \"애드토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"STORJ\",\n" +
					"      \"nick_nm\": \"스토리지\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LOOM\",\n" +
					"      \"nick_nm\": \"룸 네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"USDT\",\n" +
					"      \"nick_nm\": \"테더\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BAT\",\n" +
					"      \"nick_nm\": \"베이직어텐션토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"THETA\",\n" +
					"      \"nick_nm\": \"쎄타토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"STORM\",\n" +
					"      \"nick_nm\": \"스톰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CREDO\",\n" +
					"      \"nick_nm\": \"크레도\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"GNO\",\n" +
					"      \"nick_nm\": \"노시스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BSV\",\n" +
					"      \"nick_nm\": \"비트코인사토시비전\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ABL\",\n" +
					"      \"nick_nm\": \"에어블록\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"XLM\",\n" +
					"      \"nick_nm\": \"스텔라루멘\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"NEO\",\n" +
					"      \"nick_nm\": \"네오\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TRX\",\n" +
					"      \"nick_nm\": \"트론\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BTT\",\n" +
					"      \"nick_nm\": \"비트토렌트\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MZK\",\n" +
					"      \"nick_nm\": \"뮤지카\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"VNT\",\n" +
					"      \"nick_nm\": \"반타\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ENJ\",\n" +
					"      \"nick_nm\": \"엔진코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"COSM\",\n" +
					"      \"nick_nm\": \"코스모코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TFUEL\",\n" +
					"      \"nick_nm\": \"쎄타퓨엘\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ONT\",\n" +
					"      \"nick_nm\": \"온톨로지\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ONG\",\n" +
					"      \"nick_nm\": \"온톨로지가스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"GAS\",\n" +
					"      \"nick_nm\": \"가스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AMO\",\n" +
					"      \"nick_nm\": \"아모코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CPT\",\n" +
					"      \"nick_nm\": \"콘텐츠 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ORBS\",\n" +
					"      \"nick_nm\": \"오브스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ANKR\",\n" +
					"      \"nick_nm\": \"앵커 네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SPIN\",\n" +
					"      \"nick_nm\": \"스핀프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TEMCO\",\n" +
					"      \"nick_nm\": \"템코\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LUNA\",\n" +
					"      \"nick_nm\": \"루나\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CELR\",\n" +
					"      \"nick_nm\": \"셀러네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PXL\",\n" +
					"      \"nick_nm\": \"픽셀\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"HINT\",\n" +
					"      \"nick_nm\": \"힌트체인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PIB\",\n" +
					"      \"nick_nm\": \"피블\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KSC\",\n" +
					"      \"nick_nm\": \"케이스타라이브\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DAPPT\",\n" +
					"      \"nick_nm\": \"댑 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"EGG\",\n" +
					"      \"nick_nm\": \"네스트리\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MATIC\",\n" +
					"      \"nick_nm\": \"폴리곤\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PROM\",\n" +
					"      \"nick_nm\": \"프로메테우스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LAMB\",\n" +
					"      \"nick_nm\": \"람다\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DAD\",\n" +
					"      \"nick_nm\": \"다드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"HUM\",\n" +
					"      \"nick_nm\": \"휴먼스케이프\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ARPA\",\n" +
					"      \"nick_nm\": \"알파 체인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"VANTA\",\n" +
					"      \"nick_nm\": \"반타 네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"UOS\",\n" +
					"      \"nick_nm\": \"울트라\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CLB\",\n" +
					"      \"nick_nm\": \"클라우드브릭\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BAAS\",\n" +
					"      \"nick_nm\": \"바스아이디\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FLETA\",\n" +
					"      \"nick_nm\": \"플레타\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ISR\",\n" +
					"      \"nick_nm\": \"인슈어리움\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"REDI\",\n" +
					"      \"nick_nm\": \"레디\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FTM\",\n" +
					"      \"nick_nm\": \"팬텀\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CHZ\",\n" +
					"      \"nick_nm\": \"칠리즈\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PCI\",\n" +
					"      \"nick_nm\": \"페이코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"STPT\",\n" +
					"      \"nick_nm\": \"에스티피\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AXL\",\n" +
					"      \"nick_nm\": \"엑시얼\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MBL\",\n" +
					"      \"nick_nm\": \"무비블록\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TROY\",\n" +
					"      \"nick_nm\": \"트로이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DRM\",\n" +
					"      \"nick_nm\": \"두드림체인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"IOTX\",\n" +
					"      \"nick_nm\": \"아이오텍스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PSC\",\n" +
					"      \"nick_nm\": \"폴스타코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SOC\",\n" +
					"      \"nick_nm\": \"소다코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KVI\",\n" +
					"      \"nick_nm\": \"케이브이아이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BNA\",\n" +
					"      \"nick_nm\": \"바나나톡\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"HIBS\",\n" +
					"      \"nick_nm\": \"힙스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"XPN\",\n" +
					"      \"nick_nm\": \"판테온X\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"OGN\",\n" +
					"      \"nick_nm\": \"오리진 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"WIKEN\",\n" +
					"      \"nick_nm\": \"위드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LBXC\",\n" +
					"      \"nick_nm\": \"럭스 바이오\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"COS\",\n" +
					"      \"nick_nm\": \"콘텐토스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DAI\",\n" +
					"      \"nick_nm\": \"다이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SNX\",\n" +
					"      \"nick_nm\": \"신세틱스 네트워크 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MCH\",\n" +
					"      \"nick_nm\": \"미콘캐시\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"OBSR\",\n" +
					"      \"nick_nm\": \"옵저버\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FET\",\n" +
					"      \"nick_nm\": \"페치\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"GOM2\",\n" +
					"      \"nick_nm\": \"고머니2\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MNR\",\n" +
					"      \"nick_nm\": \"미네랄\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KAVA\",\n" +
					"      \"nick_nm\": \"카바\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"QTCON\",\n" +
					"      \"nick_nm\": \"퀴즈톡\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BORA\",\n" +
					"      \"nick_nm\": \"보라\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"RNX\",\n" +
					"      \"nick_nm\": \"루넥스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KDAG\",\n" +
					"      \"nick_nm\": \"킹디에이쥐\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ASM\",\n" +
					"      \"nick_nm\": \"어셈블 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"JST\",\n" +
					"      \"nick_nm\": \"저스트\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DVX\",\n" +
					"      \"nick_nm\": \"데리벡스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TMTG\",\n" +
					"      \"nick_nm\": \"더마이다스터치골드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LMCH\",\n" +
					"      \"nick_nm\": \"라탐캐시\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"EXE\",\n" +
					"      \"nick_nm\": \"8X8 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KLAY\",\n" +
					"      \"nick_nm\": \"클레이튼\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SHOW\",\n" +
					"      \"nick_nm\": \"쇼고\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TRCL\",\n" +
					"      \"nick_nm\": \"트리클\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"COMP\",\n" +
					"      \"nick_nm\": \"컴파운드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KRT\",\n" +
					"      \"nick_nm\": \"테라 KRT\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"GET\",\n" +
					"      \"nick_nm\": \"겟 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"IBP\",\n" +
					"      \"nick_nm\": \"아이비피 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"NFUP\",\n" +
					"      \"nick_nm\": \"엔에프유피\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SIX\",\n" +
					"      \"nick_nm\": \"식스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MOV\",\n" +
					"      \"nick_nm\": \"모티브\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"STPL\",\n" +
					"      \"nick_nm\": \"스트림 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BZRX\",\n" +
					"      \"nick_nm\": \"비지엑스 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KAI\",\n" +
					"      \"nick_nm\": \"카르디아체인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MTA\",\n" +
					"      \"nick_nm\": \"메타\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LINK\",\n" +
					"      \"nick_nm\": \"체인링크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ELLA\",\n" +
					"      \"nick_nm\": \"엘라파이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BAND\",\n" +
					"      \"nick_nm\": \"밴드 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ISDT\",\n" +
					"      \"nick_nm\": \"아이스타더스트\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FTT\",\n" +
					"      \"nick_nm\": \"에프티엑스 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AOA\",\n" +
					"      \"nick_nm\": \"오로라\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"VSYS\",\n" +
					"      \"nick_nm\": \"브이시스템즈\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"IPX\",\n" +
					"      \"nick_nm\": \"타키온 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KSM\",\n" +
					"      \"nick_nm\": \"쿠사마\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SXP\",\n" +
					"      \"nick_nm\": \"스와이프\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SRM\",\n" +
					"      \"nick_nm\": \"세럼\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CTSI\",\n" +
					"      \"nick_nm\": \"카르테시\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DOT\",\n" +
					"      \"nick_nm\": \"폴카닷\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BAL\",\n" +
					"      \"nick_nm\": \"발란서\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"NEST\",\n" +
					"      \"nick_nm\": \"네스트 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DIA\",\n" +
					"      \"nick_nm\": \"디아\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"UMA\",\n" +
					"      \"nick_nm\": \"우마\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BOT\",\n" +
					"      \"nick_nm\": \"바운스 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"REN\",\n" +
					"      \"nick_nm\": \"렌\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ADA\",\n" +
					"      \"nick_nm\": \"에이다\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DMG\",\n" +
					"      \"nick_nm\": \"디엠엠 거버넌스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"USDC\",\n" +
					"      \"nick_nm\": \"유에스디코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LEND\",\n" +
					"      \"nick_nm\": \"에이브\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MSB\",\n" +
					"      \"nick_nm\": \"미스블록\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TMC\",\n" +
					"      \"nick_nm\": \"티엠씨\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SUNOLD\",\n" +
					"      \"nick_nm\": \"썬\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TOMOE\",\n" +
					"      \"nick_nm\": \"토모체인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LUA\",\n" +
					"      \"nick_nm\": \"루아 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AAVE\",\n" +
					"      \"nick_nm\": \"에이브\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ALPHA\",\n" +
					"      \"nick_nm\": \"알파 파이낸스 랩\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BFC\",\n" +
					"      \"nick_nm\": \"비에프코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CKB\",\n" +
					"      \"nick_nm\": \"너보스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ASTA\",\n" +
					"      \"nick_nm\": \"아스타\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"WBTC\",\n" +
					"      \"nick_nm\": \"랩트 비트코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"WETH\",\n" +
					"      \"nick_nm\": \"랩트 이더리움\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BEL\",\n" +
					"      \"nick_nm\": \"벨라 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MVC\",\n" +
					"      \"nick_nm\": \"마일벌스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"STAKE\",\n" +
					"      \"nick_nm\": \"스테이크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PICA\",\n" +
					"      \"nick_nm\": \"피카\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DVI\",\n" +
					"      \"nick_nm\": \"디비전\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ORC\",\n" +
					"      \"nick_nm\": \"오르빗체인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BNT\",\n" +
					"      \"nick_nm\": \"방코르\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CAMP\",\n" +
					"      \"nick_nm\": \"캠프코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DODO\",\n" +
					"      \"nick_nm\": \"도도\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PURE\",\n" +
					"      \"nick_nm\": \"퓨리에버\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SKLAY\",\n" +
					"      \"nick_nm\": \"에스클레이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BCHA\",\n" +
					"      \"nick_nm\": \"비트코인 캐시 에이비씨\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FRONT\",\n" +
					"      \"nick_nm\": \"프론티어\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BMP\",\n" +
					"      \"nick_nm\": \"비엠피\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AXS\",\n" +
					"      \"nick_nm\": \"엑시 인피니티\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"HANDY\",\n" +
					"      \"nick_nm\": \"핸디\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"INJ\",\n" +
					"      \"nick_nm\": \"인젝티브 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MTS\",\n" +
					"      \"nick_nm\": \"메티스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DUCATO\",\n" +
					"      \"nick_nm\": \"두카토\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"HARD\",\n" +
					"      \"nick_nm\": \"하드 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DON\",\n" +
					"      \"nick_nm\": \"도니 파이낸스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MIR\",\n" +
					"      \"nick_nm\": \"미러 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TOM\",\n" +
					"      \"nick_nm\": \"톰 파이낸스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BASIC\",\n" +
					"      \"nick_nm\": \"베이직\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TIP\",\n" +
					"      \"nick_nm\": \"팁\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LINA\",\n" +
					"      \"nick_nm\": \"리니어 파이낸스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CBANK\",\n" +
					"      \"nick_nm\": \"크립토뱅크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CBK\",\n" +
					"      \"nick_nm\": \"코박 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DRC\",\n" +
					"      \"nick_nm\": \"디알씨 모빌리티\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ONX\",\n" +
					"      \"nick_nm\": \"오니엑스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FCR\",\n" +
					"      \"nick_nm\": \"프롬카\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"QTBK\",\n" +
					"      \"nick_nm\": \"퀀트북\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AVAX\",\n" +
					"      \"nick_nm\": \"아발란체\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"1INCH\",\n" +
					"      \"nick_nm\": \"1인치\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AIP\",\n" +
					"      \"nick_nm\": \"에이아이피\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"RUSH\",\n" +
					"      \"nick_nm\": \"러쉬 코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SUSHI\",\n" +
					"      \"nick_nm\": \"스시\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CRV\",\n" +
					"      \"nick_nm\": \"커브\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TOROCUS\",\n" +
					"      \"nick_nm\": \"토로커스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"KSP\",\n" +
					"      \"nick_nm\": \"클레이스왑\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"GRT\",\n" +
					"      \"nick_nm\": \"더그래프\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TRIX\",\n" +
					"      \"nick_nm\": \"트라이엄프엑스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"VIVI\",\n" +
					"      \"nick_nm\": \"비비\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TEN\",\n" +
					"      \"nick_nm\": \"테넷\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CAD\",\n" +
					"      \"nick_nm\": \"캔디 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MAP\",\n" +
					"      \"nick_nm\": \"맵 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"POD\",\n" +
					"      \"nick_nm\": \"포도\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"QI\",\n" +
					"      \"nick_nm\": \"키 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CELEB\",\n" +
					"      \"nick_nm\": \"셀럽\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MLK\",\n" +
					"      \"nick_nm\": \"밀크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LZM\",\n" +
					"      \"nick_nm\": \"라운지엠\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ATT\",\n" +
					"      \"nick_nm\": \"아튜브\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BIOT\",\n" +
					"      \"nick_nm\": \"바이오패스포트\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"IDV\",\n" +
					"      \"nick_nm\": \"이다볼 네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FIL\",\n" +
					"      \"nick_nm\": \"파일코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"UNI\",\n" +
					"      \"nick_nm\": \"유니스왑\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ICX\",\n" +
					"      \"nick_nm\": \"아이콘\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"PHA\",\n" +
					"      \"nick_nm\": \"팔라 네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"TVK\",\n" +
					"      \"nick_nm\": \"테라 버추어\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SOBA\",\n" +
					"      \"nick_nm\": \"소바\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SAND\",\n" +
					"      \"nick_nm\": \"샌드박스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MEGA\",\n" +
					"      \"nick_nm\": \"메가 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"LIT\",\n" +
					"      \"nick_nm\": \"릿엔트리\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CRO\",\n" +
					"      \"nick_nm\": \"크립토닷컴체인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"HOT\",\n" +
					"      \"nick_nm\": \"홀로\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ANC\",\n" +
					"      \"nick_nm\": \"앵커 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FIS\",\n" +
					"      \"nick_nm\": \"스타파이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"DOGE\",\n" +
					"      \"nick_nm\": \"도지코인\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"STND\",\n" +
					"      \"nick_nm\": \"스탠다드 프로토콜\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SUN\",\n" +
					"      \"nick_nm\": \"썬\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"AUCTION\",\n" +
					"      \"nick_nm\": \"바운스 토큰\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CLV\",\n" +
					"      \"nick_nm\": \"클로버 파이낸스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"XEC\",\n" +
					"      \"nick_nm\": \"이캐시\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"FLOW\",\n" +
					"      \"nick_nm\": \"플로우\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ACH\",\n" +
					"      \"nick_nm\": \"알케미 페이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SOL\",\n" +
					"      \"nick_nm\": \"솔라나\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"WNCG\",\n" +
					"      \"nick_nm\": \"랩트 나인 크로니클 골드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SKU\",\n" +
					"      \"nick_nm\": \"사쿠라\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CRU\",\n" +
					"      \"nick_nm\": \"크러스트 네트워크\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"SGB\",\n" +
					"      \"nick_nm\": \"송버드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"MANA\",\n" +
					"      \"nick_nm\": \"디센트럴랜드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"HORUS\",\n" +
					"      \"nick_nm\": \"호루스페이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ATD\",\n" +
					"      \"nick_nm\": \"아티디움\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"ADD\",\n" +
					"      \"nick_nm\": \"애드\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BLACK\",\n" +
					"      \"nick_nm\": \"이오스블랙\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"EDNA\",\n" +
					"      \"nick_nm\": \"이디엔에이\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"CHL\",\n" +
					"      \"nick_nm\": \"챌린지닥\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"EOX\",\n" +
					"      \"nick_nm\": \"이오엑스\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"WECASH\",\n" +
					"      \"nick_nm\": \"위캐시\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"NFT\",\n" +
					"      \"nick_nm\": \"에이피이엔에프티\"\n" +
					"    },\n" +
					"    {\n" +
					"      \"market\": \"BOBA\",\n" +
					"      \"nick_nm\": \"보바토큰\"\n" +
					"    }]}";

			Map<String,Object> map = commonService.jsonToMap(dataStr);
			List<Map<String,Object>> cMapList = (List<Map<String,Object>>)map.get("data");

			CoinNickVo nickVo;
			for(Map<String,Object> c : cMapList){
				// KRW면 닉네임과 심볼 삽입
				String cJson[] = (c.get("market").toString()).split("-");
				if( cJson[0].equals("KRW") ){
					nickVo = new CoinNickVo();
					nickVo.setNick_nm(c.get("nick_nm").toString());
					nickVo.setRef_room_id("23J1-G3T2-KKP1");
					nickVo.setCoin_symbol( cJson[1] );
					SqlSession.insert("coin_nick.insertCoinNick", nickVo);
				}

				if( cJson.length < 2 ){
					nickVo = new CoinNickVo();
					nickVo.setNick_nm( (c.get("nick_nm").toString()).replace(" ","") );
					nickVo.setRef_room_id("23J1-G3T2-KKP1");
					nickVo.setCoin_symbol( cJson[0] );
					SqlSession.insert("coin_nick.insertCoinNick", nickVo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			is_error = true;
		}

		return is_error;
	}

	/**
	 * 코인 닉네임 삽입(벌크용)
	 * @param
	 */
	public String insertCoinNick(CoinNickVo vo) throws IOException {
		HttpGet http = new HttpGet(CoinApiConstants.API_COIN_MARKET_CAP_LIST);

		String result = "SUCCESS";
		String dataStr = "";
		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();

		List<Map<String,Object>> map = (List<Map<String,Object>>)commonService.jsonToMap(dataStr);

		SqlSession.insert("coin_nick.insertCoinNick");

		return result;
	}

	/**
	 * 코인 가격 조회
	 * @param
	 */
//	public String insertCoinNick(){
//		HttpGet http = new HttpGet(CoinApiConstants.API_COIN_MARKET_CAP_LIST);
//
//		String result = "SUCCESS";
//
//		List<Map<String,Object>> cMapList = new ArrayList<Map<String,Object>>();
//		try (CloseableHttpClient httpclient = HttpClients.custom().build(); CloseableHttpResponse response = httpclient.execute(http)) {
//			response.getStatusLine().getStatusCode();
//			String dataStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//
//			Map<String,Object> map = commonService.jsonToMap(dataStr);
//			cMapList = (List<Map<String,Object>>)map.get("data");
//		} catch(Exception e) {
//			logger.error("error", e);
//			result = "fail";
//		}
//
//		return result;
//	}

}
